import { useNavigate } from 'react-router-dom';
import { useRecoilState, useRecoilValue } from 'recoil';
import { queryClient } from '../../../apis/queryClient';
import { COUPON_STATUS_BUTTON_TEXT } from '../../../constants/coupon';
import { ROUTE_PATH } from '../../../constants/routes';
import { sentOrReceivedAtom, targetCouponAtom } from '../../../recoil/atom';
import useModal from '../../useModal';
import {
  useGetCouponDetail,
  usePutCancelReseravation,
  usePutCompleteMeeting,
} from '../queries/couponDetail';

export const useCouponDetail = (couponId: number) => {
  const [targetCouponId, setTargetCouponId] = useRecoilState(targetCouponAtom);
  const { close } = useModal();

  const { data: couponDetail, isError, isLoading } = useGetCouponDetail(couponId);
  const sentOrReceived = useRecoilValue(sentOrReceivedAtom);
  const navigate = useNavigate();
  const reservationId = couponDetail?.reservation?.reservationId;
  const meetingId = couponDetail?.meeting?.meetingId;
  const { mutate: cancelReservation } = usePutCancelReseravation(reservationId);
  const { mutate: completeMeeting } = usePutCompleteMeeting(meetingId);

  const handleClickOnCouponStatus = {
    not_used: () => {
      setTargetCouponId(couponId);
      close();
      navigate(ROUTE_PATH.CREATE_RESERVATION);
    },
    reserved: () => {
      if (confirm('만남은 즐거우셨나요? \n해당 쿠폰을 사용 완료하겠습니다')) {
        completeMeeting();
      }
      close();
    },
    reserving: () => {
      if (confirm('예약을 취소하시겠습니까?')) {
        cancelReservation();
      }
      close();
    },
  };

  const buttonText = COUPON_STATUS_BUTTON_TEXT[couponDetail?.coupon.status as string];

  return {
    handleClickOnCouponStatus: handleClickOnCouponStatus[couponDetail?.coupon.status as string],
    sentOrReceived,
    couponDetail,
    isLoading,
    isError,
    buttonText,
    close,
  };
};
