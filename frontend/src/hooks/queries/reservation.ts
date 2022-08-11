import { useMutation, useQueryClient } from 'react-query';
import { useRecoilState } from 'recoil';
import { createReservationRequest } from '../../apis/reservation';
import useToast from '../useToast';

export const useCreateReservationMutation = (
  { couponId, date, time },
  { onSuccess = () => {}, onError = () => {} } = {}
) => {
  const queryClient = useQueryClient();
  const { show } = useToast();

  return useMutation(() => createReservationRequest({ couponId, date, time }), {
    onSuccess: () => {
      queryClient.invalidateQueries('coupon');
      onSuccess();
    },
    retry: false,
    onError: () => {
      onError();
      show('예약이 불가능한 날짜입니다.');
    },
  });
};
