import styled from '@emotion/styled';
import { Link } from 'react-router-dom';
import { Coupon } from '../../types';
import ConponDetailsNotUsed from './ConponDetails.notUsed';
import CouponDetailsReserve from './CouponDetails.reserve';

const CouponDetails = ({ coupon }: { coupon: Coupon }) => {
  const { couponId, status } = coupon;

  return (
    <S.Container>
      <S.Modal>
        {status === 'not_used' ? (
          <ConponDetailsNotUsed couponId={couponId} />
        ) : (
          <CouponDetailsReserve couponId={couponId} />
        )}
      </S.Modal>
    </S.Container>
  );
};

export default CouponDetails;

const S = {
  Container: styled.section`
    width: 18rem;
    height: 24rem;
    display: flex;
    align-items: center;
    justify-content: center;
    color: white;

    border-radius: 5px;
    background-color: #232323;
    padding: 1rem;
  `,
  Modal: styled.div`
    width: 100%;
    height: 100%;
    border-radius: 5px;
    background-color: #232323;
    padding: 1rem;
  `
};
