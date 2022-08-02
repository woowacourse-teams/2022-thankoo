import styled from '@emotion/styled';
import { COUPON_STATUS_BUTTON_TEXT, COUPON_STATUS_STRAP_TEXT } from '../../constants/coupon';
import { Coupon, CouponStatus } from '../../types';
import CouponDetail from './CouponDetail';
import GridViewCoupon from './GridViewCoupon';
import ModalWrapper from './ModalWrapper';

const strapStatus = ['reserving', 'reserved'];

const GridViewCoupons = ({ coupons }: { coupons: Coupon[] }) => {
  return (
    <S.Container>
      {coupons.map(coupon => (
        <ModalWrapper modalContent={<CouponDetail coupon={coupon} />}>
          <S.Relative>
            {strapStatus.includes(coupon.status) && (
              <S.StatusStrap status={coupon.status}>
                {COUPON_STATUS_STRAP_TEXT[coupon.status]}
              </S.StatusStrap>
            )}

            <GridViewCoupon coupon={coupon} />
          </S.Relative>
        </ModalWrapper>
      ))}
    </S.Container>
  );
};

export default GridViewCoupons;

type StatusStrapProps = {
  status: CouponStatus;
};

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
  Relative: styled.div`
    overflow: hidden;
    position: relative;
  `,
  StatusStrap: styled.div<StatusStrapProps>`
    position: absolute;
    bottom: 4%;
    right: -18%;
    color: white;
    font-size: 13px;
    z-index: 1;
    transform: rotate(-40deg);
    background-color: ${({ status }) => (status === 'reserving' ? 'tomato' : 'dimgray')};
    padding: 8px 28px;
  `,
};
