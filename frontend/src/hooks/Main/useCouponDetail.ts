import { useQuery } from 'react-query';
import { client } from '../../apis/axios';
import { API_PATH } from '../../constants/api';

const formatingRawDate = date => [date.split('T')[0], date.split('T')[1].split('.')[0].slice(0, 5)];

export const useReservationDetail = couponId => {
  const { data, isLoading, isError } = useQuery(
    ['reservationDetail', couponId],
    async () => {
      const { data } = await client({
        method: 'get',
        url: `${API_PATH.GET_COUPON_DETAIL(couponId)}`,
      });

      return data;
    },
    {
      select: data => {
        const [formattedDate, formattedTime] = formatingRawDate(data.time.meetingTime);

        const coupon = data.coupon;
        const time = { ...data.time, meetingTime: { date: formattedDate, time: formattedTime } };

        return { coupon, time };
      },
    }
  );

  return { coupon: data?.coupon, time: data?.time, isLoading, isError };
};

export const useNotUsedCouponDetail = couponId => {
  const { data, isLoading, isError } = useQuery(['notUsedDetail', couponId], async () => {
    const { data } = await client({
      method: 'get',
      url: `${API_PATH.GET_COUPON_DETAIL(couponId)}`,
    });
    return data;
  });

  return { coupon: data?.coupon, isError, isLoading };
};
