import { useMutation, useQuery, useQueryClient } from 'react-query';
import { client } from '../../../apis/axios';
import { API_PATH } from '../../../constants/api';
import { CouponDetail } from '../../../types';

export const useGetCouponDetail = couponId =>
  useQuery<CouponDetail>(['couponDetail', couponId], async () => {
    const { data } = await client({
      method: 'get',
      url: `${API_PATH.GET_COUPON_DETAIL(couponId)}`,
    });

    return data;
  });

export const usePutCancelReseravation = (
  reservationId,
  { onSuccess: handleSuccess } = { onSuccess: () => {} }
) =>
  useMutation(() => client({ method: 'put', url: API_PATH.CANCEL_RESERVATION(reservationId) }), {
    onSuccess: () => {
      handleSuccess();
      alert('예약이 취소됐습니다.');
    },
  });

export const usePutCompleteMeeting = (
  meetingId,
  { onSuccess: handleSuccess } = { onSuccess: () => {} }
) =>
  useMutation(() => client({ method: 'put', url: API_PATH.COMPLETE_MEETING(meetingId) }), {
    onSuccess: () => {
      handleSuccess();
      alert('사용 완료됐습니다');
    },
  });
