import { useMutation, useQueryClient } from 'react-query';
import { client } from '../../../apis/axios';
import { API_PATH } from '../../../constants/api';
import useToast from '../../useToast';

export const usePutReservationStatus = (
  reservationId,
  { onSuccess: handleSuccess } = { onSuccess: () => {} }
) => {
  const queryClient = useQueryClient();
  const { insertToastItem } = useToast();

  return useMutation(
    async (status: string) => {
      await client({
        method: 'put',
        url: `${API_PATH.RESERVATIONS}/${reservationId}`,
        data: { status },
      });
    },
    {
      onSuccess: () => {
        handleSuccess();
        queryClient.invalidateQueries('reservations');
      },
      onError: () => {
        insertToastItem('요청에 실패했습니다.');
      },
    }
  );
};
