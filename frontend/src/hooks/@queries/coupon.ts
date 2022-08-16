import { useMutation } from 'react-query';
import { client } from '../../apis/axios';
import { API_PATH } from '../../constants/api';

export const usePostCouponMutation = ({ receiverIds, content }, { onSuccess = () => {} } = {}) =>
  useMutation(() => postCouponRequest({ receiverIds, content }), {
    onSuccess: () => {
      onSuccess();
    },
  });

/** FETCHER */

const postCouponRequest = async ({ receiverIds, content }) => {
  const { data } = await client({
    method: 'post',
    url: API_PATH.SEND_COUPON,
    data: { receiverIds, content },
  });

  return data;
};
