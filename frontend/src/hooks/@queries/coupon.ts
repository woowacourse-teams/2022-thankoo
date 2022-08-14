import { useMutation } from 'react-query';
import { createCouponRequest } from '../../api/fetcher/coupon';

export const useCreateCouponMutation = ({ receiverIds, content }, { onSuccess = () => {} } = {}) =>
  useMutation(() => createCouponRequest({ receiverIds, content }), {
    onSuccess: () => {
      onSuccess();
    },
  });
