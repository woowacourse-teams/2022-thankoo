import styled from '@emotion/styled';
import GridViewCoupon from './GridViewCoupon';
import { Coupon } from '../../types';

const GridViewCoupons = ({ coupons }: { coupons: Coupon[] }) => {
  return (
    <S.Container>
      {coupons.map(coupon => (
        <GridViewCoupon key={coupon.couponHistoryId} coupon={coupon} />
      ))}
    </S.Container>
  );
};

export default GridViewCoupons;

const S = {
  Container: styled.div`
    display: grid;
    grid-template-columns: repeat(auto-fill, minmax(155px, 160px));
    gap: 30px 15px;
    max-height: 70vh;
    overflow: scroll;
    place-items: center;
    justify-content: space-around;
  `,
};
