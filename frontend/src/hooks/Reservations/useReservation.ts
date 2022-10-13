import { useQueryClient } from 'react-query';
import { MeetingTime } from '../../types/meeting';
import {
  RESERVATION_QUERY_KEYS,
  usePutCancelReseravation,
  usePutReservationStatus,
} from '../@queries/reservation';
import { 예약요청응답별코멘트 } from '../Main/useCouponDetail';
import useToast from '../useToast';

type useReservationParam = {
  reservationId: number;
  time: MeetingTime;
};

const useReservation = ({ reservationId, time }: useReservationParam) => {
  const queryClient = useQueryClient();
  const { insertToastItem } = useToast();

  const { mutate: handleReservation } = usePutReservationStatus(reservationId, {
    onSuccess: status => {
      insertToastItem(예약요청응답별코멘트[status]);
      queryClient.invalidateQueries(RESERVATION_QUERY_KEYS.reservations);
    },
    onError: error => {
      insertToastItem(error.response?.data.message);
    },
  });

  const { mutate: cancelReservation } = usePutCancelReseravation(reservationId, {
    onSuccess: () => {
      insertToastItem('예약을 취소했습니다');
      queryClient.invalidateQueries([RESERVATION_QUERY_KEYS.reservations]);
    },
    onError: error => {
      insertToastItem(error.response?.data.message);
    },
  });

  const handleClickOption = {
    received: [
      () => {
        if (confirm('예약을 거절하시겠습니까?')) {
          handleReservation('deny');
        }
      },
      () => {
        if (confirm(`예약을 수락하시겠습니까? \n ${time?.meetingTime}`)) {
          handleReservation('accept');
        }
      },
    ],
    sent: [
      () => {
        if (confirm('예약을 취소하시겠습니까?')) {
          cancelReservation();
        }
      },
    ],
  };

  return { handleClickOption };
};
export default useReservation;
