import { ChangeEvent, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { useRecoilValue } from 'recoil';
import { ROUTE_PATH } from '../../constants/routes';
import { checkedUsersAtom } from '../../recoil/atom';
import { CouponType } from '../../types/coupon';
import { UserProfile } from '../../types/user';
import { usePostCouponMutation } from '../@queries/coupon';
import { useGetUserProfile } from '../@queries/profile';
import useModal from '../useModal';
import { COUPON_MESSEGE_MAX_LENGTH, COUPON_TITLE_MAX_LENGTH } from './../../constants/coupon';
import useOnSuccess from './../useOnSuccess';

const useEnterCouponContent = () => {
  const { successNavigate } = useOnSuccess();
  const navigate = useNavigate();

  const [title, setTitle] = useState('');
  const [message, setMessage] = useState('');
  const [couponType, setCouponType] = useState<CouponType>('coffee');
  const checkedUsers = useRecoilValue<UserProfile[]>(checkedUsersAtom);

  const { data: userProfile } = useGetUserProfile();

  const isFilled = !!title.trim() && !!message.trim();
  const { close } = useModal();
  const { mutate: sendCoupon } = usePostCouponMutation(
    {
      receiverIds: checkedUsers.map(user => user.id),
      content: { couponType, title, message },
    },
    {
      onSuccess: () => {
        successNavigate({
          page: ROUTE_PATH.ENTER_COUPON_CONTENT,
          props: {
            couponType,
            message,
            receivers: checkedUsers,
            title,
          },
        });
        close();
      },
    }
  );

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
    sendCoupon,
    title,
    message,
    setTitle,
    setMessage,
    checkedUsers,
    currentUserName: userProfile?.name,
    currentUserId: userProfile?.id,
    handleOnchangeTitle,
    handleOnchangeMessage,
  };
};

export default useEnterCouponContent;
