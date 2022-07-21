import axios from 'axios';
import { useEffect, useState } from 'react';
import { useQuery } from 'react-query';
import { useNavigate } from 'react-router-dom';
import { useRecoilValue } from 'recoil';
import { API_PATH } from '../../constants/api';
import { ROUTE_PATH } from '../../constants/routes';
import { checkedUsersAtom } from '../../recoil/atom';
import { Coupon, CouponType, initialCouponState, UserProfile } from '../../types';

const useEnterCouponContent = () => {
  const accessToken = localStorage.getItem('token');
  const { data: userProfile } = useQuery<UserProfile>('profile', async () => {
    const res = await axios({
      method: 'GET',
      url: `${API_PATH.PROFILE}`,
      headers: { Authorization: `Bearer ${accessToken}` },
    });
    return res.data;
  });

  const checkedUsers = useRecoilValue(checkedUsersAtom);

  const [couponType, setCouponType] = useState<CouponType>('coffee');
  const [title, setTitle] = useState('');
  const [message, setMessage] = useState('');

  const isFilled = !!title && !!message;

  const navigate = useNavigate();

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
  });

  useEffect(() => {
    if (userProfile) {
      const newCouponSender = { ...currentCoupon.sender, id: userProfile.id };
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

  const sendCoupon = async () => {
    const { status, statusText } = await axios({
      method: 'POST',
      url: `${API_PATH.SEND_COUPON}`,
      headers: { Authorization: `Bearer ${accessToken}` },
      data: {
        receiverIds: checkedUsers.map(user => user.id),
        content: {
          ...currentCoupon.content,
        },
      },
    });

    navigate(`${ROUTE_PATH.EXACT_MAIN}`);
  };

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
