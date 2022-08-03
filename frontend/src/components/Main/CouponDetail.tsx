import styled from '@emotion/styled';
import { Link } from 'react-router-dom';
import { useRecoilValue } from 'recoil';
import { COUPON_STATUS_BUTTON_TEXT } from '../../constants/coupon';
import { ROUTE_PATH } from '../../constants/routes';
import { useCouponDetail } from '../../hooks/Main/domain/useCouponDetail';
import { useGetCouponDetail } from '../../hooks/Main/queries/couponDetail';
import { sentOrReceivedAtom } from '../../recoil/atom';
import { Coupon, CouponDetail } from '../../types';
import CloseButton from '../@shared/CloseButton';
import useModal from './../../hooks/useModal';
import CouponDetailCoupon from './ConponDetail.coupon';
import CouponDetailReservation from './CouponDetail.reservation';

const CouponDetail = ({ couponId }: { couponId: number }) => {
  const { syncPageWithScroll, setPageRef, page, setPage, setTargetCouponId } =
    useCouponDetail(couponId);
  const { data, isError, isLoading } = useGetCouponDetail(couponId);
  const sentOrReceived = useRecoilValue(sentOrReceivedAtom);
  const { close } = useModal();

  if (isLoading) {
    return <></>;
  }

  return (
    <S.Container>
      <S.Modal>
        <S.Header>
          <span></span>
          <CloseButton onClick={close} color='white' />
        </S.Header>
        <S.PageSlider onScroll={syncPageWithScroll}>
          <CouponDetailCoupon coupon={data?.coupon as Coupon} ref={setPageRef(0)} />
          {data?.coupon.status !== 'not_used' ? (
            <CouponDetailReservation couponDetail={data as CouponDetail} ref={setPageRef(1)} />
          ) : (
            <S.EmptyReservationPage ref={setPageRef(1)}>
              아직 예약 정보가 없습니다.
            </S.EmptyReservationPage>
          )}
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
          {sentOrReceived === '받은' && (
            <S.UseCouponLink
              onClick={() => {
                setTargetCouponId(couponId);
                close();
              }}
              to={`${ROUTE_PATH.CREATE_RESERVATION}`}
            >
              <S.Button>{COUPON_STATUS_BUTTON_TEXT[data?.coupon.status as string]}</S.Button>
            </S.UseCouponLink>
          )}
        </S.Footer>
      </S.Modal>
    </S.Container>
  );
};

export default CouponDetail;

const S = {
  Container: styled.section`
    position: relative;
    width: 18rem;
    height: fit-content;
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
    background-color: ${({ theme, disabled }) =>
      disabled ? theme.button.disbaled.background : theme.primary};
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
