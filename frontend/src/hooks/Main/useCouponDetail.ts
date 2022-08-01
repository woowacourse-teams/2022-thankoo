import { useQuery } from 'react-query';
import { requestInstance } from '../../apis/axios';
import { API_PATH } from '../../constants/api';

const formatingRawDate = date => date.split('T')[0] + ' ' + date.split('T')[1].split('.')[0];

export const useReservationDetail = couponId => {
  const { data, isLoading, isError } = useQuery(
    ['reservationDetail', couponId],
    async () => {
      const { data } = await requestInstance({
        method: 'get',
        url: `${API_PATH.GET_COUPON_DETAIL(couponId)}`,
      });

      return data;
    },
    {
      select: data => {
        const coupon = data.coupon;
        const time = { ...data.time, meetingTime: formatingRawDate(data.time.meetingTime) };

        return { coupon, time };
      },
    }
  );

  return { coupon: data?.coupon, time: data?.time, isLoading, isError };
};

export const useNotUsedCouponDetail = couponId => {
  const { data, isLoading, isError } = useQuery(['notUsedDetail', couponId], async () => {
    const { data } = await requestInstance({
      method: 'get',
      url: `${API_PATH.GET_COUPON_DETAIL(couponId)}`,
    });
    return data;
  });

  return { coupon: data?.coupon, isError, isLoading };
};
