import { useState } from 'react';
import { useRecoilState } from 'recoil';
import { checkedUsersAtom } from '../../recoil/atom';
import { UserProfile } from '../../types/user';

const useSelectReceiver = () => {
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

  return {
    checkedUsers,
    toggleUser,
    uncheckUser,
    isCheckedUser,
    keyword,
    setKeyword,
  };
};

export default useSelectReceiver;
