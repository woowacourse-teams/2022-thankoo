import { useEffect, useRef, useState } from 'react';
import { useRecoilState } from 'recoil';
import { targetCouponAtom } from '../../../recoil/atom';

export const useCouponDetail = (couponId: number) => {
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
    if (e.currentTarget.firstElementChild?.getBoundingClientRect().x > -50) {
      setPage(true);
      return;
    }
    setPage(false);
  };

  return { syncPageWithScroll, couponId, setPageRef, page, setPage, setTargetCouponId };
};
