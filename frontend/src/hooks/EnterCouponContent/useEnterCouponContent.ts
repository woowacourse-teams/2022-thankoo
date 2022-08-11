import { useState } from 'react';
import { useRecoilValue, useResetRecoilState } from 'recoil';
import { ROUTE_PATH } from '../../constants/routes';
import { checkedUsersAtom } from '../../recoil/atom';
import { CouponType, UserProfile } from '../../types';
import { useCreateCouponMutation } from '../queries/coupon';
import { useGetUserProfile } from '../queries/profile';
import useOnSuccess from './../useOnSuccess';

const useEnterCouponContent = () => {
  const { successNavigate } = useOnSuccess();

  const [title, setTitle] = useState('');
  const [message, setMessage] = useState('');
  const [couponType, setCouponType] = useState<CouponType>('coffee');
  const checkedUsers = useRecoilValue<UserProfile[]>(checkedUsersAtom);
  const resetCheckedUsers = useResetRecoilState(checkedUsersAtom);

  const { data: userProfile } = useGetUserProfile();

  const isFilled = !!title && !!message;

  const { mutate: sendCoupon } = useCreateCouponMutation(
    {
      receiverIds: checkedUsers.map(user => user.id),
      content: { couponType, title, message },
    },
    {
      onSuccess: () => {
        resetCheckedUsers();
        successNavigate(ROUTE_PATH.EXACT_MAIN);
      },
    }
  );

  return {
    couponType,
    setCouponType,
    isFilled,
    sendCoupon,
    title,
    message,
    setTitle,
    setMessage,
    checkedUsers,
    name: userProfile?.name,
    id: userProfile?.id,
  };
};

export default useEnterCouponContent;
