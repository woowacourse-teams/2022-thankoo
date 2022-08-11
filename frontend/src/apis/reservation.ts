import { API_PATH } from '../constants/api';
import { client } from './axios';

export const createReservationRequest = ({ couponId, date, time }) =>
  client({
    method: 'post',
    url: `${API_PATH.RESERVATIONS}`,
    data: {
      couponId,
      startAt: `${date} ${time}:00`,
    },
  });
