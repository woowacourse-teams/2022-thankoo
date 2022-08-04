import { useState } from 'react';
import { useRecoilState } from 'recoil';
import { sentOrReceivedAtom } from '../../../recoil/atom';
import { CouponType } from '../../../types';
import { useGetCoupons } from '../queries/coupons';

const COUPON_STATUS_PRIORITY = {
  not_used: 2,
  reserving: 0,
  reserved: 1,
  used: 10,
};

const useMain = () => {
  const [sentOrReceived, setSentOrReceived] = useRecoilState(sentOrReceivedAtom);
  const [currentType, setCurrentType] = useState<CouponType>('entire');

  const { data, isLoading, error } = useGetCoupons(sentOrReceived);

  const edittedCouponsBySentOrReceived =
    sentOrReceived === '보낸'
      ? data?.map(coupon => {
          const tempSender = coupon.sender;
          const tempReceiver = coupon.receiver;
          return { ...coupon, receiver: tempSender, sender: tempReceiver };
        })
      : data;

  const couponsByType = edittedCouponsBySentOrReceived?.filter(
    coupon => coupon.content.couponType === currentType || currentType === 'entire'
  );

  const orderedCoupons = couponsByType?.sort(
    (coupon1, coupon2) =>
      COUPON_STATUS_PRIORITY[coupon1.status] - COUPON_STATUS_PRIORITY[coupon2.status]
  );

  const toggleSentOrReceived = () => {
    setSentOrReceived(prev => (prev === '받은' ? '보낸' : '받은'));
  };

  return {
    setCurrentType,
    orderedCoupons,
    isLoading,
    error,
    currentType,
    sentOrReceived,
    toggleSentOrReceived,
  };
};

export default useMain;
