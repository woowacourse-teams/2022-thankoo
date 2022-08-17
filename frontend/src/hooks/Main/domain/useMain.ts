import { useState } from 'react';
import { useRecoilState } from 'recoil';
import { sentOrReceivedAtom } from '../../../recoil/atom';
import { CouponType } from '../../../types';
import { sorted } from '../../../utils';
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
  const [showUsedCouponsWith, setShowUsedCouponsWith] = useState(false);

  const { data, isLoading, error } = useGetCoupons(sentOrReceived);

  const edittedCouponsBySentOrReceived =
    sentOrReceived === '보낸'
      ? data?.map(coupon => {
          const tempSender = coupon.sender;
          const tempReceiver = coupon.receiver;
          return { ...coupon, receiver: tempSender, sender: tempReceiver };
        })
      : data;

  const filteredCoupons = edittedCouponsBySentOrReceived
    ?.filter(coupon => coupon.content.couponType === currentType || currentType === 'entire')
    .filter(coupon => (!showUsedCouponsWith ? coupon.status !== 'used' : true));

  const sortedCoupons = sorted(
    filteredCoupons,
    (coupon1, coupon2) =>
      COUPON_STATUS_PRIORITY[coupon1.status] - COUPON_STATUS_PRIORITY[coupon2.status]
  );

  return {
    setCurrentType,
    coupons: showUsedCouponsWith ? filteredCoupons : sortedCoupons,
    isLoading,
    error,
    currentType,
    sentOrReceived,
    setSentOrReceived,
    setShowUsedCouponsWith,
    showUsedCouponsWith,
  };
};

export default useMain;
