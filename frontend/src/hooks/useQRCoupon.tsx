import { useEffect } from 'react';
import { useQuery } from 'react-query';
import { useLocation, useNavigate } from 'react-router-dom';
import { client } from '../apis/axios';
import QRCouponRegisterModal from '../components/Main/QRCouponRegisterModal';
import { API_PATH } from '../constants/api';
import { ROUTE_PATH } from '../constants/routes';
import { CouponType } from '../types';
import useModal from './useModal';

export type QRCouponResponse = {
  id: number;
  senderId: number;
  senderName: string;
  couponType: CouponType;
};

const useQRCoupon = () => {
  const { search } = useLocation();
  const { show, setModalContent } = useModal();
  const navigate = useNavigate();

  const query = localStorage.getItem('query') || search;
  const code = query.includes('?code=') ? query.replace('?code=', '') : '';
  localStorage.removeItem('query');

  const { data: QRCoupon, refetch } = useQuery<QRCouponResponse>(
    ['QRCoupon'],
    async () => {
      const { data } = await client({
        method: 'get',
        url: API_PATH.GET_QR_COUPON(code),
      });

      return data;
    },
    {
      onSuccess: res => {
        show();
        setModalContent(<QRCouponRegisterModal QRCode={res} serialCode={code} />);
      },
      onError: () => {
        alert('등록되지 않은 쿠폰입니다.');
        navigate(ROUTE_PATH.EXACT_MAIN);
      },
      retry: false,
      enabled: !!code,
    }
  );

  useEffect(() => {
    if (code) {
      refetch();
    }
  }, [code]);

  return { QRCoupon };
};

export default useQRCoupon;
