import { API_PATH } from '../../constants/api';
import { client } from '../config/axios';

export const createCouponRequest = async ({ receiverIds, content }) => {
  const { data } = await client({
    method: 'post',
    url: API_PATH.SEND_COUPON,
    data: { receiverIds, content },
  });

  return data;
};
