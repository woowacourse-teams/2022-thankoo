import axios from 'axios';
import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { useRecoilValue } from 'recoil';
import { authAtom, checkedUsersAtom } from '../../recoil/atom';
import { Coupon, CouponType, initialCouponState } from '../../types';
import { API_PATH } from '../../constants/api';
import { ROUTE_PATH } from '../../constants/routes';

const useEnterCouponContent = () => {
  const auth = useRecoilValue(authAtom);
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
      id: auth.memberId,
    },
    content: {
      ...initialCouponState.content,
      couponType,
    },
  });

  useEffect(() => {
    setCurrentCoupon(prev => ({
      ...prev,
      content: {
        couponType,
        title,
        message,
      },
    }));
  }, [couponType, title, message]);

  const sendCoupon = async () => {
    const { status, statusText } = await axios({
      method: 'POST',
      url: `${API_PATH.SEND_COUPON}`,
      headers: { Authorization: `Bearer ${auth.accessToken}` },
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
