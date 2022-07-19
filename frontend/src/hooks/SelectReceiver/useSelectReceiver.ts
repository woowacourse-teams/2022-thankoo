import axios from 'axios';
import { useState } from 'react';
import { useQuery } from 'react-query';
import { useRecoilState, useRecoilValue } from 'recoil';
import { BASE_URL } from '../../constants';
import { authAtom, checkedUsersAtom } from '../../recoil/atom';
import { UserProfile } from '../../types';
import useFilterMatchedUser from '../useFilterMatchedUser';
import { API_PATH } from '../../constants/api';

const useSelectReceiver = () => {
  const { accessToken, memberId } = useRecoilValue(authAtom);
  const {
    data: users,
    isLoading,
    error,
  } = useQuery<UserProfile[]>('users', async () => {
    const { data } = await axios({
      method: 'get',
      url: `${API_PATH.MEMBERS}`,
      headers: { Authorization: `Bearer ${accessToken}` },
    });
    return data;
  });

  const [keyword, setKeyword] = useState('');
  const [checkedUsers, setCheckedUsers] = useRecoilState<UserProfile[]>(checkedUsersAtom);

  const checkUser = (user: UserProfile) => {
    setCheckedUsers(prev => [...prev, user]);
  };
  const uncheckUser = (user: UserProfile) => {
    setCheckedUsers(prev => prev.filter(checkedUser => checkedUser.id !== user.id));
  };
  const toggleUser = (user: UserProfile) => {
    if (isCheckedUser(user)) {
      uncheckUser(user);
      return;
    }
    checkUser(user);
  };

  const isCheckedUser = (user: UserProfile) =>
    checkedUsers?.some(checkUser => checkUser.id === user.id);

  const matchedUsers = useFilterMatchedUser(keyword, users);

  return {
    users,
    isLoading,
    error,
    checkedUsers,
    toggleUser,
    uncheckUser,
    isCheckedUser,
    keyword,
    setKeyword,
    matchedUsers,
  };
};

export default useSelectReceiver;
