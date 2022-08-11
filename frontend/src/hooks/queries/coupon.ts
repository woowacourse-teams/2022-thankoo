import { useMutation } from 'react-query';
import { createCouponRequest } from '../../apis/coupon';

export const useCreateCouponMutation = ({ receiverIds, content }, { onSuccess = () => {} } = {}) =>
  useMutation(() => createCouponRequest({ receiverIds, content }), {
    onSuccess: () => {
      onSuccess();
    },
  });
