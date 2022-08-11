import { useMutation, useQueryClient } from 'react-query';
import { createReservationRequest } from '../../apis/reservation';

export const useCreateReservationMutation = (
  { couponId, date, time },
  { onSuccess = () => {}, onError = () => {} } = {}
) => {
  const queryClient = useQueryClient();

  return useMutation(() => createReservationRequest({ couponId, date, time }), {
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
