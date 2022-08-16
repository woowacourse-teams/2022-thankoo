import { useMutation, useQueryClient } from 'react-query';
import { client } from '../../apis/axios';
import { API_PATH } from '../../constants/api';

export const usePostReservationMutation = (
  { couponId, date, time },
  { onSuccess = () => {}, onError = () => {} } = {}
) => {
  const queryClient = useQueryClient();

  return useMutation(() => postReservationRequest({ couponId, date, time }), {
    onSuccess: () => {
      queryClient.invalidateQueries('coupon');
      onSuccess();
    },
    retry: false,
    onError: () => {
      onError();
    },
  });
};

/** FETCHER */

const postReservationRequest = ({ couponId, date, time }) =>
  client({
    method: 'post',
    url: `${API_PATH.RESERVATIONS}`,
    data: {
      couponId,
      startAt: `${date} ${time}:00`,
    },
  });
