import { useState } from 'react';
import { useRecoilState } from 'recoil';
import { sentOrReceivedAtom } from '../../recoil/atom';
import { CouponStatusPriority, CouponType, UserCanSeeCoupons } from '../../types/coupon';
import { sorted } from '../../utils';
import { useGetCoupons } from '../@queries/coupon';

//낮을 수록 우선순위가 높다
const COUPON_STATUS_PRIORITY: CouponStatusPriority = {
  reserved: 0,
  reserving: 1,
  not_used: 2,
};

//TODO 해당하는 status가 없더라도 에러가 나지 않고 있다.
const userCanSeeCouponsStatus: UserCanSeeCoupons[] = ['not_used', 'reserved', 'reserving'];

const useMain = () => {
  const [sentOrReceived, setSentOrReceived] = useRecoilState(sentOrReceivedAtom);
  const [currentType, setCurrentType] = useState<CouponType>('entire');
  const [showUsedCouponsWith, setShowUsedCouponsWith] = useState(false);

  const { data: coupons, isLoading, error } = useGetCoupons(sentOrReceived);

  const couponsEditedByTransmitStatus =
    sentOrReceived === 'sent'
      ? coupons?.map(coupon => {
          const [receiver, sender] = [coupon.sender, coupon.receiver];

          return { ...coupon, receiver, sender };
        })
      : coupons;

  const userCanSeeCoupons = couponsEditedByTransmitStatus
    ?.filter(coupon => coupon.content.couponType === currentType || currentType === 'entire')
    .filter(coupon =>
      !showUsedCouponsWith ? userCanSeeCouponsStatus.some(status => status === coupon.status) : true
    );

  const sortedUserCanSeeCoupons = sorted(
    userCanSeeCoupons,
    (coupon1, coupon2) =>
      COUPON_STATUS_PRIORITY[coupon1.status] - COUPON_STATUS_PRIORITY[coupon2.status]
  );

  return {
    setCurrentType,
    coupons: showUsedCouponsWith ? userCanSeeCoupons : sortedUserCanSeeCoupons,
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
