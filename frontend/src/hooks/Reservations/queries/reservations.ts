import { useMutation, useQueryClient } from 'react-query';
import { client } from '../../../apis/axios';
import { API_PATH } from '../../../constants/api';

export const usePutReservationStatus = reservationId => {
  const queryClient = useQueryClient();

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
        queryClient.invalidateQueries('reservations');
      },
    }
  );
};
