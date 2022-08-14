import { API_PATH } from '../../constants/api';
import { client } from '../config/axios';

export const getUserProfileRequest = async () => {
  const { data } = await client({
    method: 'GET',
    url: API_PATH.PROFILE,
  });

  return data;
};

export const getCouponExchangeCount = async () => {
  const { data } = await client({
    method: 'get',
    url: `${API_PATH.GET_COUPONS_EXCHANGE_COUNT}`,
  });
  return data;
};
