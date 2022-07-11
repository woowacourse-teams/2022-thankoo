import { useQuery } from 'react-query';
import axios from 'axios';
import { UserProfile } from '../../types';
import { useRecoilState } from 'recoil';
import { authAtom, checkedUsersAtom } from '../../recoil/atom';
import { useRecoilValue } from 'recoil';
import { BASE_URL } from '../../constants';

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

  return { users, isLoading, error, checkedUsers, toggleUser, uncheckUser, isCheckedUser };
};

export default useSelectReceiver;
