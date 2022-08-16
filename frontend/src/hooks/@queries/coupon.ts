import { useMutation } from 'react-query';
import { client } from '../../apis/axios';
import { API_PATH } from '../../constants/api';

export const useCreateCouponMutation = ({ receiverIds, content }, { onSuccess = () => {} } = {}) =>
  useMutation(() => createCouponRequest({ receiverIds, content }), {
    onSuccess: () => {
      onSuccess();
    },
  });

/** FETCHER */

const createCouponRequest = async ({ receiverIds, content }) => {
  const { data } = await client({
    method: 'post',
    url: API_PATH.SEND_COUPON,
    data: { receiverIds, content },
  });

  return data;
};
