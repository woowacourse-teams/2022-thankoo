import { useEffect, useState } from 'react';
import { useMutation, useQuery } from 'react-query';
import { useRecoilValue, useResetRecoilState } from 'recoil';
import { client } from '../../apis/axios';
import { API_PATH } from '../../constants/api';
import { ROUTE_PATH } from '../../constants/routes';
import { checkedUsersAtom } from '../../recoil/atom';
import { Coupon, CouponType, initialCouponState, UserProfile } from '../../types';
import { COUPON_MESSEGE_MAX_LENGTH, COUPON_TITLE_MAX_LENGTH } from './../../constants/coupon';
import useOnSuccess from './../useOnSuccess';

const useEnterCouponContent = () => {
  const { successNavigate } = useOnSuccess();
  const resetCheckedUsers = useResetRecoilState(checkedUsersAtom);

  const { data: userProfile } = useQuery<UserProfile>('profile', async () => {
    const res = await client({
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

  const handleOnchangeTitle = value => {
    if (value.length <= COUPON_TITLE_MAX_LENGTH) {
      setTitle(value);
    }
  };
  const handleOnchangeMessage = value => {
    if (value.length <= COUPON_MESSEGE_MAX_LENGTH) {
      setMessage(value);
    }
  };

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
      await client({
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
    handleOnchangeTitle,
    handleOnchangeMessage,
  };
};

export default useEnterCouponContent;
