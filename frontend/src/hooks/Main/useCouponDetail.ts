import { useQueryClient } from 'react-query';
import { useNavigate } from 'react-router-dom';
import { useRecoilState, useRecoilValue } from 'recoil';
import { ROUTE_PATH } from '../../constants/routes';
import { sentOrReceivedAtom, targetCouponAtom } from '../../recoil/atom';
import { CouponDetailButton, CouponDetailButtonProps } from '../../types/coupon';
import { COUPON_QUERY_KEY, useGetCouponDetail, usePutCompleteCoupon } from '../@queries/coupon';
import { usePutCompleteMeeting } from '../@queries/meeting';
import { usePutCancelReseravation, usePutReservationStatus } from '../@queries/reservation';
import useModal from '../useModal';
import useToast from '../useToast';

export const 예약요청응답별코멘트 = {
  accept: '예약을 승인 했습니다.',
  deny: '예약을 거절 했습니다.',
};

export const useCouponDetail = (couponId: number) => {
  const queryClient = useQueryClient();
  const [_, setTargetCouponId] = useRecoilState(targetCouponAtom);
  const { close } = useModal();
  const { insertToastItem } = useToast();

  const { data: couponDetail, isError, isLoading } = useGetCouponDetail(couponId);
  const sentOrReceived = useRecoilValue(sentOrReceivedAtom);
  const navigate = useNavigate();
  const reservationId = couponDetail?.reservation?.reservationId;
  const meetingId = couponDetail?.meeting?.meetingId;

  const { mutate: cancelReservation } = usePutCancelReseravation(reservationId, {
    onSuccess: () => {
      queryClient.invalidateQueries([COUPON_QUERY_KEY.coupon]);
      insertToastItem('예약을 취소했습니다.');
      close();
    },
    onError: error => {
      insertToastItem(error.response?.data.message);
    },
  });
  const { mutate: completeMeeting } = usePutCompleteMeeting(meetingId, {
    onSuccess: () => {
      queryClient.invalidateQueries([COUPON_QUERY_KEY.coupon]);
      insertToastItem('✅ 쿠폰을 사용했습니다');
      close();
    },
    onError: error => {
      insertToastItem(error.response?.data.message);
    },
  });
  const { mutate: handleReservation } = usePutReservationStatus(reservationId, {
    onSuccess: status => {
      queryClient.invalidateQueries([COUPON_QUERY_KEY.coupon]);
      insertToastItem(예약요청응답별코멘트[status]);
      close();
    },
    onError: error => {
      insertToastItem(error.response?.data.message);
    },
  });

  const { mutate: completeCoupon } = usePutCompleteCoupon(couponId, {
    onSuccess: () => {
      queryClient.invalidateQueries([COUPON_QUERY_KEY.coupon]);
      insertToastItem('쿠폰 사용 완료했습니다.');
      close();
    },
    onError: error => {
      insertToastItem(error.response?.data.message);
    },
  });

  const immediateUseButton: CouponDetailButtonProps = {
    text: '즉시 사용하기',
    color: 'primary',
    onClick: () => {
      if (confirm('만남은 즐거우셨나요? \n쿠폰을 사용 완료 하겠습니다.')) {
        completeCoupon();
      }
    },
  };

  const COUPON_STATUS_BUTTON: CouponDetailButton = {
    received: {
      not_used: [
        {
          text: '예약 하기',
          color: 'secondaryLight',
          onClick: () => {
            setTargetCouponId(couponId);
            close();
            navigate(ROUTE_PATH.CREATE_RESERVATION);
          },
        },
        immediateUseButton,
      ],
      reserving: [
        {
          text: '예약 취소',
          color: 'secondaryLight',
          onClick: () => {
            if (confirm('예약을 취소하시겠습니까?')) {
              cancelReservation();
            }
          },
        },
        immediateUseButton,
      ],
      reserved: [
        {
          text: '사용 완료',
          color: 'primary',
          onClick: () => {
            if (confirm('만남은 즐거우셨나요? \n쿠폰을 사용 완료 하겠습니다')) {
              completeMeeting();
            }
          },
        },
      ],
      used: [{ text: '이미 사용된 쿠폰입니다', isDisabled: true, color: 'secondary' }],
      immediately_used: [{ text: '이미 사용된 쿠폰입니다', isDisabled: true, color: 'secondary' }],
      expired: [{ text: '만료된 쿠폰입니다', isDisabled: true, color: 'secondary' }],
    },
    sent: {
      not_used: [immediateUseButton],
      reserving: [
        {
          text: '거절',
          color: 'secondaryLight',
          onClick: () => {
            if (confirm('예약을 거절하시겠습니까?')) {
              handleReservation('deny');
            }
          },
        },
        {
          text: '승인',
          color: 'primary',
          onClick: () => {
            if (confirm('예약을 승인하시겠습니까?')) {
              handleReservation('accept');
            }
          },
        },
      ],
      reserved: [
        {
          text: '사용 완료',
          color: 'primary',
          onClick: () => {
            if (confirm('만남은 즐거우셨나요? \n쿠폰을 사용 완료하겠습니다')) {
              completeMeeting();
            }
          },
        },
      ],
      used: [{ text: '이미 사용된 쿠폰입니다', isDisabled: true, color: 'secondary' }],
      immediately_used: [{ text: '이미 사용된 쿠폰입니다', isDisabled: true, color: 'secondary' }],
      expired: [{ text: '만료된 쿠폰입니다', isDisabled: true, color: 'secondary' }],
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
