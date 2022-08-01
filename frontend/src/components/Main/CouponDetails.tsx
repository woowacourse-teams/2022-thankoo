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
  `,
  Header: styled.div`
    display: flex;
    align-items: center;
    justify-content: space-between;
    height: 10%;
    width: 108%;
  `,
  CouponArea: styled.div`
    display: flex;
    align-items: center;
    justify-content: center;
    height: 30%;
  `,
  Contents: styled.div`
    display: flex;
    flex-direction: column;
    height: 45%;
    justify-content: center;
  `,
  SpaceBetween: styled.div`
    display: flex;
    justify-content: space-between;
    align-items: center;
    flex: 1;
  `,
  Sender: styled.span`
    font-size: 20px;
  `,
  Message: styled.div`
    font-size: 15px;
    overflow-y: auto;
    flex: 1;
  `,
  Footer: styled.div`
    display: flex;
    justify-content: center;
    height: 15%;
    align-items: flex-end;
  `,
  Button: styled.button`
    border: none;
    border-radius: 4px;
    background-color: ${({ theme }) => theme.primary};
    color: ${({ theme }) => theme.button.abled.color};
    width: 100%;
    padding: 0.7rem;
    font-size: 15px;
    height: fit-content;
  `,
  UseCouponLink: styled(Link)`
    width: 100%;
  `,
};
