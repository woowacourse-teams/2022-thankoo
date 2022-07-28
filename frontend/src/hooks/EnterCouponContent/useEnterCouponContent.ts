import { useEffect, useState } from 'react';
import { useMutation, useQuery } from 'react-query';
import { useRecoilValue, useResetRecoilState } from 'recoil';
import { requestInstance } from '../../apis/axios';
import { API_PATH } from '../../constants/api';
import { ROUTE_PATH } from '../../constants/routes';
import { checkedUsersAtom } from '../../recoil/atom';
import { Coupon, CouponType, initialCouponState, UserProfile } from '../../types';
import useOnSuccess from './../useOnSuccess';

const useEnterCouponContent = () => {
  const { successNavigate } = useOnSuccess();
  const resetCheckedUsers = useResetRecoilState(checkedUsersAtom);

  const { data: userProfile } = useQuery<UserProfile>('profile', async () => {
    const res = await requestInstance({
      method: 'GET',
      url: `${API_PATH.PROFILE}`,
    });
    return res.data;
  });

  const checkedUsers = useRecoilValue(checkedUsersAtom);

  const [couponType, setCouponType] = useState<CouponType>('coffee');
  const [title, setTitle] = useState('');
  const [message, setMessage] = useState('');

  const isFilled = !!title && !!message;

  const [currentCoupon, setCurrentCoupon] = useState<Coupon>({
    ...initialCouponState,
    sender: {
      ...initialCouponState.sender,
      id: userProfile?.id ?? 0,
    },
    content: {
      ...initialCouponState.content,
      couponType,
    },
    status: 'not_used',
  });

  useEffect(() => {
    if (userProfile) {
      const newCouponSender = {
        ...currentCoupon.sender,
        id: userProfile.id,
        name: userProfile.name,
      };
      setCurrentCoupon(prev => ({
        ...prev,
        sender: newCouponSender,
      }));
    }
    setCurrentCoupon(prev => ({
      ...prev,
      content: {
        couponType,
        title,
        message,
      },
    }));
  }, [userProfile, couponType, title, message]);
  const sendCoupon = useMutation(
    async () =>
      await requestInstance({
        method: 'POST',
        url: `${API_PATH.SEND_COUPON}`,
        data: {
          receiverIds: checkedUsers.map(user => user.id),
          content: {
            ...currentCoupon.content,
          },
        },
      }),
    {
      onSuccess: () => {
        resetCheckedUsers();
        successNavigate(`${ROUTE_PATH.EXACT_MAIN}`);
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
    currentCoupon,
  };
};

export default useEnterCouponContent;
