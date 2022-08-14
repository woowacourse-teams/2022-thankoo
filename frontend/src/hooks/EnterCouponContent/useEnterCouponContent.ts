import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { useRecoilValue } from 'recoil';
import { ROUTE_PATH } from '../../constants/routes';
import { checkedUsersAtom } from '../../recoil/atom';
import { CouponType, UserProfile } from '../../types';
import { useCreateCouponMutation } from '../@queries/coupon';
import { useGetUserProfile } from '../@queries/profile';
import useModal from '../useModal';
import useOnSuccess from './../useOnSuccess';

const useEnterCouponContent = () => {
  const { successNavigate } = useOnSuccess();
  const navigate = useNavigate();

  const [title, setTitle] = useState('');
  const [message, setMessage] = useState('');
  const [couponType, setCouponType] = useState<CouponType>('coffee');
  const checkedUsers = useRecoilValue<UserProfile[]>(checkedUsersAtom);

  const { data: userProfile } = useGetUserProfile();

  const isFilled = !!title && !!message;
  const { close } = useModal();
  const { mutate: sendCoupon } = useCreateCouponMutation(
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
  };
};

export default useEnterCouponContent;
