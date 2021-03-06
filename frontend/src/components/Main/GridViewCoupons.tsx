import styled from '@emotion/styled';
import { Coupon } from '../../types';
import CouponDetails from './CouponDetails';
import GridViewCoupon from './GridViewCoupon';
import ModalWrapper from './ModalWrapper';

const GridViewCoupons = ({ coupons }: { coupons: Coupon[] }) => {
  return (
    <S.Container>
      {coupons.map(coupon => (
        <ModalWrapper modalContent={<CouponDetails coupon={coupon} />}>
          <GridViewCoupon coupon={coupon} />
        </ModalWrapper>
      ))}
    </S.Container>
  );
};

export default GridViewCoupons;

const S = {
  Container: styled.div`
    display: grid;
    grid-template-columns: repeat(auto-fill, minmax(145px, 150px));
    gap: 30px 15px;
    max-height: 70vh;
    place-items: center;
    justify-content: space-around;
    overflow-y: overlay;

    &::-webkit-scrollbar {
      width: 2px;
      background-color: transparent;
    }
    &::-webkit-scrollbar-thumb {
      background-color: transparent;
      border-radius: 5px;
    }

    &:hover {
      overflow-y: overlay;

      &::-webkit-scrollbar {
        width: 2px;
        background-color: transparent;
      }
      &::-webkit-scrollbar-thumb {
        background-color: #afafaf;
        border-radius: 5px;
      }
    }
  `,
};
