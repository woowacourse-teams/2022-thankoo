import { useEffect, useRef, useState } from 'react';
import { useQuery } from 'react-query';
import { useRecoilState } from 'recoil';
import { client } from '../../apis/axios';
import { API_PATH } from '../../constants/api';
import { Coupon } from '../../types';
import { targetCouponAtom } from './../../recoil/atom';

const formatingRawDate = date => [date.split('T')[0], date.split('T')[1].split('.')[0].slice(0, 5)];

export const useCouponDetail = (coupon: Coupon) => {
  const { couponId, status } = coupon;
  const [targetCouponId, setTargetCouponId] = useRecoilState(targetCouponAtom);
  const [page, setPage] = useState(true);

  const pageRefs = useRef<Array<HTMLDivElement | null>>([]);

  const setPageRef = (page: number) => (el: HTMLDivElement | null) => {
    pageRefs.current[page] = el;

    return pageRefs.current[page];
  };

  useEffect(() => {
    if (page) {
      pageRefs.current[0]?.scrollIntoView({
        behavior: 'smooth',
      });
    }
    if (!page) {
      pageRefs.current[1]?.scrollIntoView({
        behavior: 'smooth',
      });
    }
  }, [page]);

  const syncPageWithScroll = e => {
    if (e.currentTarget.firstElementChild?.getBoundingClientRect().x > 59) {
      setPage(true);
    }
    if (e.currentTarget.firstElementChild?.getBoundingClientRect().x < -195) {
      setPage(false);
    }
  };

  return { syncPageWithScroll, couponId, setPageRef, page, setPage, setTargetCouponId };
};

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
