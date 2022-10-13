import { AxiosError } from 'axios';
import { useMutation, useQuery, useQueryClient } from 'react-query';
import { client } from '../../apis/axios';
import { API_PATH } from '../../constants/api';
import { ErrorType } from '../../types/api';
import { COUPON_QUERY_KEY } from './coupon';

type OrderByType = 'received' | 'sent';
type 예약요청응답Type = 'accept' | 'deny';

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
export const useGetReservations = (orderBy: OrderByType) =>
  useQuery([RESERVATION_QUERY_KEYS.reservations, orderBy], () => getReservationsRequest(orderBy), {
    refetchOnWindowFocus: false,
    retry: false,
  });

export const usePutCancelReseravation = (
  reservationId,
  { onSuccess: handleSuccess, onError } = {
    onSuccess: () => {},
    onError: (error: AxiosError<ErrorType>) => {},
  }
) =>
  useMutation(() => client({ method: 'put', url: API_PATH.CANCEL_RESERVATION(reservationId) }), {
    onSuccess: () => {
      handleSuccess();
    },
    onError: (error: AxiosError<ErrorType>) => {
      onError?.(error);
    },
    retry: false,
  });

export const usePutReservationStatus = (
  reservationId,
  { onSuccess: handleSuccess, onError } = {
    onSuccess: (status: 예약요청응답Type) => {},
    onError: (error: AxiosError<ErrorType>) => {},
  }
) =>
  useMutation((status: 예약요청응답Type) => putReservationStatusRequest(status, reservationId), {
    onSuccess: (_, variables: 예약요청응답Type) => {
      handleSuccess(variables);
    },
    onError: (error: AxiosError<ErrorType>) => {
      onError?.(error);
    },
    retry: false,
  });

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
