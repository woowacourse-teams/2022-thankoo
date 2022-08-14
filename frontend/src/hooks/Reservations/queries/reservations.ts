import { useMutation, useQueryClient } from 'react-query';
import { client } from '../../../api/config/axios';
import { API_PATH } from '../../../constants/api';

export const usePutReservationStatus = (
  reservationId,
  { onSuccess: handleSuccess } = { onSuccess: () => {} }
) => {
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
        handleSuccess();
        queryClient.invalidateQueries('reservations');
      },
    }
  );
};
