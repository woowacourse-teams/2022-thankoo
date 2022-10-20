import { useState } from 'react';
import { useRecoilState } from 'recoil';
import { sentOrReceivedAtom } from '../../recoil/atom';
import { CouponType } from '../../types/coupon';

const useMain = () => {
  const [sentOrReceived, setSentOrReceived] = useRecoilState(sentOrReceivedAtom);
  const [currentType, setCurrentType] = useState<CouponType>('entire');
  const [showUsedCouponsWith, setShowUsedCouponsWith] = useState(false);

  return {
    currentType,
    sentOrReceived,
    showUsedCouponsWith,
    setCurrentType,
    setSentOrReceived,
    setShowUsedCouponsWith,
  };
};

export default useMain;
