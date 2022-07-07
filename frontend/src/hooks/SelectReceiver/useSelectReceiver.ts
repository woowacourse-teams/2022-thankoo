import { useQuery } from 'react-query';
import axios from 'axios';
import { UserProfile } from '../../types';
import { useState } from 'react';

const useSelectReceiver = () => {
  const {
    data: users,
    isLoading,
    error,
  } = useQuery<UserProfile[]>('users', async () => {
    const { data } = await axios.get('http://localhost:3000/api/members');
    return data;
  });

  const [checkedUsers, setCheckedUsers] = useState<UserProfile[]>([]);
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
    checkedUsers.some(checkUser => checkUser.id === user.id);

  return { users, isLoading, error, checkedUsers, toggleUser, uncheckUser, isCheckedUser };
};

export default useSelectReceiver;
