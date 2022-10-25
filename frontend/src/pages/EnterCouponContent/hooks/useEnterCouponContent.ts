import { ChangeEvent, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { useRecoilValue } from 'recoil';
import { ROUTE_PATH } from '../../../constants/routes';
import { checkedUsersAtom } from '../../../recoil/atom';
import { CouponTransmitableType } from '../../../types/coupon';
import { UserProfile } from '../../../types/user';
import { useGetUserProfile } from '../../../hooks/@queries/profile';
import useModal from '../../../hooks/useModal';
import { COUPON_MESSEGE_MAX_LENGTH, COUPON_TITLE_MAX_LENGTH } from '../../../constants/coupon';

const useEnterCouponContent = () => {
  const navigate = useNavigate();

  const [title, setTitle] = useState('');
  const [message, setMessage] = useState('');
  const [couponType, setCouponType] = useState<CouponTransmitableType>('coffee');
  const checkedUsers = useRecoilValue<UserProfile[]>(checkedUsersAtom);

  const { data: userProfile, isLoading } = useGetUserProfile();

  const isFilled = !!title.trim() && !!message.trim();
  const { close } = useModal();

  const handleOnchangeTitle = (e: ChangeEvent<HTMLInputElement>) => {
    const targetValue = e.target.value;
    if (targetValue.length <= COUPON_TITLE_MAX_LENGTH) {
      setTitle(targetValue);
    }
  };
  const handleOnchangeMessage = (e: ChangeEvent<HTMLTextAreaElement>) => {
    const targetValue = e.target.value;
    if (targetValue.length <= COUPON_MESSEGE_MAX_LENGTH) {
      setMessage(targetValue);
    }
  };

  if (!checkedUsers.length) {
    navigate(ROUTE_PATH.SELECT_RECEIVER);
  }

  return {
    couponType,
    setCouponType,
    isFilled,
    title,
    message,
    setTitle,
    setMessage,
    checkedUsers,
    currentUserName: userProfile?.name || '',
    currentUserId: userProfile?.id || 0,
    handleOnchangeTitle,
    handleOnchangeMessage,
  };
};

export default useEnterCouponContent;
