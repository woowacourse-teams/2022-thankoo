import { useState } from 'react';
import { useQuery } from 'react-query';
import { useRecoilState } from 'recoil';
import { client } from '../../../apis/axios';
import { API_PATH } from '../../../constants/api';
import { sentOrReceivedAtom } from '../../../recoil/atom';
import { Coupon, CouponType } from '../../../types';

const COUPON_STATUS_PRIORITY = {
  not_used: 2,
  reserving: 0,
  reserved: 1,
};
const SENT_OR_RECEIVED_API_PATH = {
  받은: API_PATH.RECEIVED_COUPONS_NOT_USED,
  보낸: API_PATH.SENT_COUPONS,
};

const useMain = () => {
  const [sentOrReceived, setSentOrReceived] = useRecoilState(sentOrReceivedAtom);
  const [currentType, setCurrentType] = useState<CouponType>('entire');

  const { data, isLoading, error } = useQuery<Coupon[]>(['coupon', sentOrReceived], async () => {
    const { data } = await client({
      method: 'get',
      url: SENT_OR_RECEIVED_API_PATH[sentOrReceived],
    });
    return data;
  });

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
