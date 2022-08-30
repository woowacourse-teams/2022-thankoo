import { useQueryClient } from 'react-query';
import { useNavigate } from 'react-router-dom';
import { useRecoilState, useRecoilValue } from 'recoil';
import { ROUTE_PATH } from '../../../constants/routes';
import { sentOrReceivedAtom, targetCouponAtom } from '../../../recoil/atom';
import { usePutReservationStatus } from '../../Reservations/queries/reservations';
import useModal from '../../useModal';
import useToast from '../../useToast';
import {
  useGetCouponDetail,
  usePutCancelReseravation,
  usePutCompleteMeeting,
} from '../queries/couponDetail';

export const useCouponDetail = (couponId: number) => {
  const queryClient = useQueryClient();
  const [targetCouponId, setTargetCouponId] = useRecoilState(targetCouponAtom);
  const { close } = useModal();
  const { insertToastItem } = useToast();

  const { data: couponDetail, isError, isLoading } = useGetCouponDetail(couponId);
  const sentOrReceived = useRecoilValue(sentOrReceivedAtom);
  const navigate = useNavigate();
  const reservationId = couponDetail?.reservation?.reservationId;
  const meetingId = couponDetail?.meeting?.meetingId;

  const { mutate: cancelReservation } = usePutCancelReseravation(reservationId, {
    onSuccess: () => {
      queryClient.invalidateQueries(['coupon']);
    },
  });
  const { mutate: completeMeeting } = usePutCompleteMeeting(meetingId, {
    onSuccess: () => {
      queryClient.invalidateQueries(['coupon']);
    },
  });
  const { mutate: handleReservation } = usePutReservationStatus(reservationId, {
    onSuccess: () => {
      queryClient.invalidateQueries(['coupon']);
    },
  });

  const COUPON_STATUS_BUTTON = {
    받은: {
      not_used: [
        {
          text: '예약 하기',
          bg: 'tomato',
          disabled: false,
          onClick: () => {
            setTargetCouponId(couponId);
            close();
            navigate(ROUTE_PATH.CREATE_RESERVATION);
          },
        },
      ],
      reserving: [
        {
          text: '예약 취소',
          bg: 'tomato',
          disabled: false,
          onClick: () => {
            if (confirm('예약을 취소하시겠습니까?')) {
              cancelReservation();
              insertToastItem('예약을 취소했습니다.');
              close();
            }
          },
        },
      ],
      reserved: [
        {
          text: '사용 완료',
          bg: 'tomato',
          disabled: false,
          onClick: () => {
            if (confirm('만남은 즐거우셨나요? \n쿠폰을 사용 완료 하겠습니다')) {
              insertToastItem('✅ 쿠폰을 사용했습니다');
              completeMeeting();
              close();
            }
          },
        },
      ],
      used: [{ text: '이미 사용된 쿠폰입니다', disable: true, bg: '#838383' }],
      expired: [{ text: '만료된 쿠폰입니다', disable: true, bg: '#838383' }],
    },
    보낸: {
      not_used: [
        {
          text: '상대가 아직 예약하지 않았습니다.',
          disabled: true,
          bg: '#838383',
        },
      ],
      reserving: [
        {
          text: '승인',
          disabled: false,
          bg: 'tomato',
          onClick: () => {
            if (confirm('예약을 승인하시겠습니까?')) {
              handleReservation('accept');
              insertToastItem('✅ 예약을 승인했습니다');
              close();
            }
          },
        },
        {
          text: '거절',
          disabled: false,
          bg: '#838383',
          onClick: () => {
            if (confirm('예약을 거절하시겠습니까?')) {
              handleReservation('deny');
              insertToastItem('예약을 거절했습니다');
              close();
            }
          },
        },
      ],
      reserved: [
        {
          text: '사용 완료',
          disabled: false,
          bg: 'tomato',
          onClick: () => {
            if (confirm('만남은 즐거우셨나요? \n쿠폰을 사용 완료하겠습니다')) {
              insertToastItem('✅ 쿠폰을 사용했습니다');
              completeMeeting();
              close();
            }
          },
        },
      ],
      used: [{ text: '이미 사용된 쿠폰입니다', disabled: true, bg: '#838383' }],
      expired: [{ text: '만료된 쿠폰입니다', disabled: true, bg: '#838383' }],
    },
  };

  const buttonOptions = COUPON_STATUS_BUTTON[sentOrReceived][couponDetail?.coupon.status as string];

  return {
    sentOrReceived,
    couponDetail,
    isLoading,
    isError,
    buttonOptions,
    close,
  };
};
