import { useMutation, useQuery, useQueryClient } from 'react-query';
import { client } from '../../apis/axios';
import { API_PATH } from '../../constants/api';
import useToast from '../useToast';
import { COUPON_QUERY_KEY } from './coupon';

export const RESERVATION_QUERY_KEYS = {
  reservations: 'reservations',
};

export const usePostReservationMutation = (
  { couponId, date, time },
  { onSuccess = () => {}, onError = () => {} } = {}
) => {
  const queryClient = useQueryClient();

  return useMutation(() => postReservationRequest({ couponId, date, time }), {
    onSuccess: () => {
      queryClient.invalidateQueries(COUPON_QUERY_KEY.coupon);
      onSuccess();
    },
    retry: false,
    onError: () => {
      onError();
    },
  });
};
export const useGetReservations = orderBy =>
  useQuery([RESERVATION_QUERY_KEYS.reservations, orderBy], () => getReservationsRequest(orderBy), {
    refetchOnWindowFocus: false,
    retry: false,
  });

export const usePutCancelReseravation = (
  reservationId,
  { onSuccess: handleSuccess } = { onSuccess: () => {} }
) =>
  useMutation(() => client({ method: 'put', url: API_PATH.CANCEL_RESERVATION(reservationId) }), {
    onSuccess: () => {
      handleSuccess();
    },
  });

export const usePutReservationStatus = (
  reservationId,
  { onSuccess: handleSuccess } = { onSuccess: () => {} }
) => {
  const queryClient = useQueryClient();
  const { insertToastItem } = useToast();

  return useMutation((status: string) => putReservationStatusRequest(status, reservationId), {
    onSuccess: () => {
      handleSuccess();
      queryClient.invalidateQueries(RESERVATION_QUERY_KEYS.reservations);
    },
    onError: () => {
      insertToastItem('요청에 실패했습니다.');
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

const getReservationsRequest = async orderBy => {
  const { data } = await client({
    method: 'get',
    url: orderBy === 'received' ? API_PATH.RESERVATIONS_RECEIVED : API_PATH.RESERVATIONS_SENT,
  });

  return data;
};

const putReservationStatusRequest = async (status: string, reservationId) => {
  await client({
    method: 'put',
    url: `${API_PATH.RESERVATIONS}/${reservationId}`,
    data: { status },
  });
};
