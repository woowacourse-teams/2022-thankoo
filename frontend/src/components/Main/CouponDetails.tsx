import styled from '@emotion/styled';
import { Link } from 'react-router-dom';
import { ROUTE_PATH } from '../../constants/routes';
import useModal from '../../hooks/useModal';
import { Coupon } from '../../types';
import CloseButton from '../@shared/CloseButton';
import GridViewCoupon from './GridViewCoupon';

const CouponDetails = ({ coupon }: { coupon: Coupon }) => {
  const { sender, content } = coupon;
  const { close } = useModal();

  return (
    <S.Container>
      <S.Modal>
        <S.Header>
          <span></span>
          <CloseButton onClick={close} color='white' />
        </S.Header>
        <S.CouponArea>
          <GridViewCoupon coupon={coupon} />
        </S.CouponArea>
        <S.Contents>
          <S.SpaceBetween>
            <S.Sender>{sender.name}</S.Sender>
            {/* <span>2022.02.22</span> */}
          </S.SpaceBetween>
          <S.Message>{content.message}</S.Message>
        </S.Contents>
        <S.Footer>
          <S.UseCouponLink to={`${ROUTE_PATH.CREATE_RESERVATION}`}>
            <S.Button>예약하기</S.Button>
          </S.UseCouponLink>
        </S.Footer>
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
