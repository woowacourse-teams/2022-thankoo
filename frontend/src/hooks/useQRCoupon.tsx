import { useEffect } from 'react';
import { useQuery } from 'react-query';
import { useLocation, useNavigate } from 'react-router-dom';
import { client } from '../apis/axios';
import { API_PATH } from '../constants/api';
import { ROUTE_PATH } from '../constants/routes';
import QRCouponRegisterModal from '../pages/Main/components/QRCouponRegisterModal';
import { CouponType } from '../types/coupon';
import useModal from './useModal';

export type QRCouponResponse = {
  id: number;
  organizationId: number;
  organizationName: string;
  senderId: number;
  senderName: string;
  couponType: CouponType;
};

const useQRCoupon = () => {
  const { search } = useLocation();
  const { showModal, setModalContent } = useModal();
  const navigate = useNavigate();

  const query = localStorage.getItem('query') || search;
  const code = query.includes('?code=') ? query.replace('?code=', '') : '';

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
        showModal();
        setModalContent(<QRCouponRegisterModal QRCode={res} serialCode={code} />);
      },
      onError: error => {
        navigate(ROUTE_PATH.EXACT_MAIN, { replace: true });
      },
      retry: false,
      enabled: !!code,
      useErrorBoundary: false,
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
