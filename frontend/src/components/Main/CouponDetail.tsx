import styled from '@emotion/styled';
import { Link } from 'react-router-dom';
import { useCouponDetail } from '../../hooks/Main/domain/useCouponDetail';
import { Coupon, CouponDetail } from '../../types';
import CloseButton from '../@shared/CloseButton';
import PageSlider from '../@shared/PageSlider';
import CouponDetailCoupon from './ConponDetail.coupon';
import CouponDetailReservation from './CouponDetail.reservation';

const CouponDetail = ({ couponId }: { couponId: number }) => {
  const { couponDetail, isLoading, sentOrReceived, buttonOptions, close } =
    useCouponDetail(couponId);

  if (isLoading) {
    return <div></div>;
  }

  return (
    <S.Container>
      <S.Modal>
        <S.Header>
          <CloseButton onClick={close} color='white' />
        </S.Header>
        <PageSlider>
          <CouponDetailCoupon coupon={couponDetail?.coupon as Coupon} />
          {couponDetail?.coupon.status !== 'not_used' ? (
            <CouponDetailReservation couponDetail={couponDetail as CouponDetail} />
          ) : (
            <S.EmptyReservationPage>아직 예약 정보가 없습니다.</S.EmptyReservationPage>
          )}
        </PageSlider>
        <S.Footer>
          <S.ButtonWrapper>
            {buttonOptions.map((button, idx) => (
              <S.Button
                key={idx}
                bg={button.bg}
                disabled={button.disabled}
                onClick={button.onClick && button.onClick}
              >
                {button.text}
              </S.Button>
            ))}
          </S.ButtonWrapper>
        </S.Footer>
      </S.Modal>
    </S.Container>
  );
};

export default CouponDetail;

type ButtonProps = {
  bg: string;
  disabled: boolean;
};

const S = {
  Container: styled.section`
    position: fixed;
    top: 50%;
    left: 50%;
    z-index: 1000;
    transform: translate(-50%, -50%);
    width: 28rem;
    height: 40rem;
    //height: fit-content;
    display: flex;
    align-items: center;
    justify-content: center;
    color: white;

    border-radius: 5px;
    background-color: #232323;
    padding: 2rem;
  `,
  Header: styled.div`
    display: flex;
    align-items: center;
    justify-content: flex-end;
    height: 10%;
    width: 106%;
  `,
  Modal: styled.div`
    width: 100%;
    height: 100%;
    border-radius: 5px;
    background-color: #232323;
    padding: 1rem;
    display: flex;
    flex-flow: column;
  `,
  Footer: styled.div`
    display: flex;
    justify-content: center;
    height: 15%;
    align-items: flex-end;
  `,
  ButtonWrapper: styled.div`
    display: flex;
    width: 100%;
    gap: 5px;
  `,
  Button: styled.button<ButtonProps>`
    border: none;
    border-radius: 8px;
    background-color: ${({ theme, disabled, bg }) =>
      disabled ? theme.button.disbaled.background : bg};
    color: ${({ theme, disabled }) =>
      disabled ? theme.button.disbaled.color : theme.button.abled.color};
    width: 100%;
    padding: 0.7rem;
    font-size: 15px;
    height: fit-content;
  `,
  UseCouponLink: styled(Link)`
    width: 100%;
  `,
  EmptyReservationPage: styled.div`
    width: 100%;
    display: flex;
    align-self: center;
    justify-content: center;
  `,
};
