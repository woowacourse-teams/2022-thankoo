import { API_PATH } from '../../constants/api';
import { client } from './../axios';

export const getCouponExchangeCount = async () => {
  const { data } = await client({
    method: 'get',
    url: `${API_PATH.GET_COUPONS_EXCHANGE_COUNT}`,
  });
  return data;
};
