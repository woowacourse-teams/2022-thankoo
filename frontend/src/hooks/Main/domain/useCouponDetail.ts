import { useEffect, useRef, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { useRecoilState, useRecoilValue } from 'recoil';
import CouponDetail from '../../../components/Main/CouponDetail';
import { COUPON_STATUS_BUTTON_TEXT } from '../../../constants/coupon';
import { ROUTE_PATH } from '../../../constants/routes';
import { sentOrReceivedAtom, targetCouponAtom } from '../../../recoil/atom';
import { useGetCouponDetail } from '../queries/couponDetail';

export const useCouponDetail = (couponId: number) => {
  const [targetCouponId, setTargetCouponId] = useRecoilState(targetCouponAtom);
  const [page, setPage] = useState(true);

  const { data: couponDetail, isError, isLoading } = useGetCouponDetail(couponId);
  const sentOrReceived = useRecoilValue(sentOrReceivedAtom);
  const navigate = useNavigate();

  const handleClickOnCouponStatus = {
    not_used: () => {
      setTargetCouponId(couponId);
      close();
      navigate(ROUTE_PATH.CREATE_RESERVATION);
    },
    reserved: () => {
      alert('사용 완료 기능은 구현중입니다.');
    },
    reserving: () => {
      alert('예약 취소 기능은 구현중입니다.');
    },
  };

  const buttonText = COUPON_STATUS_BUTTON_TEXT[couponDetail?.coupon.status as string];

  return {
    page,
    setPage,
    handleClickOnCouponStatus: handleClickOnCouponStatus[couponDetail?.coupon.status as string],
    sentOrReceived,
    couponDetail,
    isLoading,
    isError,
    buttonText,
  };
};
