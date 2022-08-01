import styled from '@emotion/styled';
import CloseButton from '../@shared/CloseButton';
import GridViewCoupon from './GridViewCoupon';
import { Reservation } from '../../types';
import { useRecoilState } from 'recoil';
import { targetCouponAtom } from '../../recoil/atom';
import { ROUTE_PATH } from '../../constants/routes';
import { COUPON_STATUS_BUTTON_TEXT } from '../../constants/coupon';
import { Link } from 'react-router-dom';
import { useQuery } from 'react-query';
import { requestInstance } from '../../apis/axios';
import { API_PATH } from '../../constants/api';
import useModal from '../../hooks/useModal';
import { useReservationDetail } from '../../hooks/Main/useCouponDetail';

const CouponDetailsReserve = ({ couponId }: { couponId: number }) => {
  //todo: couponDetailNotUsed에서도 재사용
  const { close } = useModal();
  const [targetCouponId, setTargetCouponId] = useRecoilState(targetCouponAtom);
  const { coupon, time, isLoading } = useReservationDetail(couponId);

  if (isLoading) return <></>;

  return (
    <>
      <S.Header>
        <span></span>
        <CloseButton onClick={close} color='white' />
      </S.Header>
      <S.CouponArea>
        <GridViewCoupon coupon={coupon} />
      </S.CouponArea>
      <S.Contents>
        <S.SpaceBetween>
          <S.Sender>{coupon.sender.name}</S.Sender>
        </S.SpaceBetween>
        <span>{time.meetingTime}</span>
        <S.Message>{coupon.content.message}</S.Message>
      </S.Contents>
      <S.Footer>
        <S.UseCouponLink
          onClick={() => {
            setTargetCouponId(couponId);
            close();
          }}
          to={`${ROUTE_PATH.CREATE_RESERVATION}`}
        >
          <S.Button>{COUPON_STATUS_BUTTON_TEXT[coupon.status]}</S.Button>
        </S.UseCouponLink>
      </S.Footer>
    </>
  );
};

const S = {
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

export default CouponDetailsReserve;
