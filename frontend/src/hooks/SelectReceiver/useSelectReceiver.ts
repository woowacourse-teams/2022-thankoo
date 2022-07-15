import axios from 'axios';
import { assemble, disassemble } from 'hangul-js';
import { useMemo, useState } from 'react';
import { useQuery } from 'react-query';
import { useRecoilState, useRecoilValue } from 'recoil';
import { BASE_URL } from '../../constants';
import { authAtom, checkedUsersAtom } from '../../recoil/atom';
import { UserProfile } from '../../types';

const findMatches = (keyword, users) =>
  users
    ?.filter(word => {
      const regex = new RegExp(keyword, 'gi');
      return word.name.match(regex);
    })
    .map(user => ({ ...user, name: assemble(user.name) }));

const useSelectReceiver = () => {
  const { accessToken, memberId } = useRecoilValue(authAtom);
  const {
    data: users,
    isLoading,
    error,
  } = useQuery<UserProfile[]>('users', async () => {
    const { data } = await axios({
      method: 'get',
      url: `${BASE_URL}/api/members`,
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

  const parsedNameUsers = useMemo(
    () =>
      users?.map(user => ({
        ...user,
        name: disassemble(user.name as string).join(''),
      })),
    [users?.length]
  );
  const parsedKeyword = disassemble(keyword).join('');
  const matchedUsers = findMatches(parsedKeyword, parsedNameUsers);

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
