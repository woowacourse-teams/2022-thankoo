import styled from '@emotion/styled';
import { useRef, useState } from 'react';
import { Link } from 'react-router-dom';
import { useRecoilState } from 'recoil';
import { COUPON_STATUS_BUTTON_TEXT } from '../../constants/coupon';
import { ROUTE_PATH } from '../../constants/routes';
import useModal from '../../hooks/useModal';
import { targetCouponAtom } from '../../recoil/atom';
import { Coupon } from '../../types';
import CloseButton from '../@shared/CloseButton';
import ConponDetailNotUsed from './ConponDetail.notUsed';
import CouponDetailReserve from './CouponDetail.reserve';

const CouponDetail = ({ coupon }: { coupon: Coupon }) => {
  const { couponId, status } = coupon;
  const [targetCouponId, setTargetCouponId] = useRecoilState(targetCouponAtom);
  const [page, setPage] = useState(true);
  const { close } = useModal();

  const pageRefs = useRef();

  return (
    <S.Container>
      <S.Modal>
        <S.Header>
          <span></span>
          <CloseButton onClick={close} color='white' />
        </S.Header>
        <S.PageSlider>
          <ConponDetailNotUsed couponId={couponId} />
          <CouponDetailReserve couponId={couponId} />
        </S.PageSlider>
        <S.DotWrapper>
          <S.Dot
            onClick={() => {
              setPage(prev => !prev);
            }}
            className={page ? 'active' : ''}
          />
          <S.Dot
            onClick={() => {
              setPage(prev => !prev);
            }}
            className={!page ? 'active' : ''}
          />
        </S.DotWrapper>
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
      </S.Modal>
    </S.Container>
  );
};

export default CouponDetail;

const S = {
  Container: styled.section`
    width: 18rem;
    height: 26rem;
    display: flex;
    align-items: center;
    justify-content: center;
    color: white;

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
  Modal: styled.div`
    width: 100%;
    height: 100%;
    border-radius: 5px;
    background-color: #232323;
    padding: 1rem;
    display: flex;
    flex-flow: column;
  `,
  PageSlider: styled.div`
    width: 100%;
    display: flex;
    flex-wrap: nowrap;
    overflow-x: scroll;
    overflow-y: hidden;
    scroll-snap-type: x mandatory;

    & > div {
      flex: 0 0 auto;
      scroll-margin: 0;
      scroll-snap-align: start;
    }
    &::-webkit-scrollbar {
      display: none; /* Chrome, Safari, Opera*/
    }
    -ms-overflow-style: none; /* IE and Edge */
    scrollbar-width: none; /* Firefox */
  `,
  DotWrapper: styled.div`
    margin: 10px auto;
    display: flex;
    gap: 7px;
  `,
  ActiveDot: styled.div``,
  Dot: styled.div`
    width: 10px;
    height: 10px;
    border-radius: 50%;
    background-color: #8e8e8e;
    transition: all ease-in-out 0.1s;

    &.active {
      width: 22px;
      border-radius: 10px;
      background-color: ${({ theme }) => theme.primary};
    }
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
