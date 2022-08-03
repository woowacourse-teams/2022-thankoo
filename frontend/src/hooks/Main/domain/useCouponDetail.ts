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
    const parentWidthX = e.currentTarget.getBoundingClientRect().x;
    const firstPageX = e.currentTarget.children[0]?.getBoundingClientRect().x;
    const secondPageX = e.currentTarget.children[1]?.getBoundingClientRect().x;

    if (firstPageX === parentWidthX) {
      setPage(true);
    }
    if (secondPageX === parentWidthX) {
      setPage(false);
    }
  };

  return { syncPageWithScroll, couponId, setPageRef, page, setPage, setTargetCouponId };
};
