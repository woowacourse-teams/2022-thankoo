import styled from '@emotion/styled';
import { Suspense } from 'react';
import { COUPON_STATUS_STRAP_TEXT } from '../../constants/coupon';
import { CouponStatus, CouponTransmitStatus, CouponType } from '../../types/coupon';
import CustomErrorBoundary from './../../errors/CustomErrorBoundary';
import ErrorFallBack from './../../errors/ErrorFallBack';
import useGridViewCoupons from './../../hooks/Main/useGridViewCoupons';
import ModalWrapper from './../@shared/Modal/ModalWrapper';
import NoReceivedCoupon from './../@shared/noContent/NoReceivedCoupon';
import NoSendCoupon from './../@shared/noContent/NoSendCoupon';
import Spinner from './../@shared/Spinner';
import CouponDetail from './CouponDetail';
import GridViewCoupon from './GridViewCoupon';

const strapStatus = ['reserving', 'reserved'];

const GridViewCoupons = ({
  currentType,
  sentOrReceived,
  showUsedCouponsWith,
}: {
  currentType: CouponType;
  sentOrReceived: CouponTransmitStatus;
  showUsedCouponsWith: boolean;
}) => {
  const { coupons } = useGridViewCoupons(currentType, sentOrReceived, showUsedCouponsWith);
  const isOnReserve = status => strapStatus.includes(status);
  const isCompleted = status => status === 'used' || status === 'immediately_used';

  if (coupons.length === 0 && sentOrReceived === 'sent') {
    return <NoSendCoupon />;
  }
  if (coupons.length === 0 && sentOrReceived === 'received') {
    return <NoReceivedCoupon />;
  }

  return (
    <S.Container>
      {coupons.map(coupon => (
        <ModalWrapper
          key={coupon.couponId}
          modal={
            <CustomErrorBoundary fallbackComponent={ErrorFallBack}>
              <Suspense fallback={<Spinner />}>
                <CouponDetail couponId={coupon.couponId} />
              </Suspense>
            </CustomErrorBoundary>
          }
        >
          <S.Relative>
            {isCompleted(coupon.status) && <S.CompleteDeem>사용 완료</S.CompleteDeem>}
            {isOnReserve(coupon.status) && (
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

    place-items: center;
    justify-content: space-around;
    overflow-y: auto;
    padding-bottom: 5rem;

    &::-webkit-scrollbar {
      width: 2px;
      background-color: transparent;
    }
    &::-webkit-scrollbar-thumb {
      background-color: transparent;
      border-radius: 5px;
    }

    &:hover {
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
    transition: all ease-in-out 0.1s;
    cursor: pointer;
    :hover,
    :active {
      opacity: 0.8;
    }
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
  CompleteDeem: styled.div`
    position: absolute;
    background-color: #232323b2;
    width: 100%;
    height: 100%;
    top: 0;
    left: 0;
    z-index: 1;
    display: flex;
    align-items: center;
    justify-content: center;
    color: white;
    font-size: 1.5rem;
  `,
};
