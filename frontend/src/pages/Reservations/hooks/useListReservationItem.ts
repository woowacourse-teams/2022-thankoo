import { useQueryClient } from 'react-query';
import {
  RESERVATION_QUERY_KEYS,
  usePutCancelReseravation,
  usePutReservationStatus,
} from '../../../hooks/@queries/reservation';
import { 예약요청응답별코멘트 } from '../../Main/hooks/useCouponDetail';
import useToast from '../../../hooks/useToast';

const useListReservationItem = (reservationId: number) => {
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

  return {
    denyRequest: () => {
      if (confirm('예약을 거절하시겠습니까?')) {
        handleReservation('deny');
      }
    },
    acceptRequest: () => {
      if (confirm(`예약을 수락하시겠습니까?`)) {
        handleReservation('accept');
      }
    },
    cancelRequest: () => {
      if (confirm('예약을 취소하시겠습니까?')) {
        cancelReservation();
      }
    },
  };
};
export default useListReservationItem;
