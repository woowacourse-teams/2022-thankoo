import GridViewCoupons from '../commons/Main/GridViewCoupons';
import CouponTypesNav from '../commons/Main/CouponTypesNav';
import ArrowBackButton from '../commons/Header/ArrowBackButton';
import useMain from '../hooks/Main/useMain';
import styled from '@emotion/styled';
import SendIcon from '@mui/icons-material/Send';
import { couponTypeKeys } from '../types';
import { Link } from 'react-router-dom';

const Main = () => {
  const { setCurrentType, couponsByType, isLoading, error, currentType } = useMain();

  if (isLoading) return <>로딩중</>;
  if (error) return <>에러뜸</>;

  return (
    <S.Container>
      <S.Header>
        <ArrowBackButton />
        <S.HeaderText>쿠폰함</S.HeaderText>
      </S.Header>
      <S.Body>
        <CouponTypesNav
          onChangeType={setCurrentType}
          currentType={currentType}
          selectableCouponTypes={couponTypeKeys}
        />
        {couponsByType && <GridViewCoupons coupons={couponsByType} />}
      </S.Body>
      <Link to='/select-receiver'>
        <S.NewCouponButton fontSize='small' />
      </Link>
    </S.Container>
  );
};

const S = {
  Container: styled.div`
    display: flex;
    flex-direction: column;
    padding: 5px;
  `,
  Header: styled.header`
    display: flex;
    flex-direction: column;
    align-items: flex-start;
    gap: 15px;
    color: white;
    margin: 10px 0 0 2vw;
  `,
  HeaderText: styled.p`
    font-size: 22px;
    margin-left: calc(1vw + 6px);
  `,
  Body: styled.div`
    display: flex;
    flex-direction: column;
    gap: 1rem;
    padding: 15px;
  `,
  NewCouponButton: styled(SendIcon)`
    position: absolute;
    bottom: 3%;
    right: 5%;
    background-color: black;
    fill: white;
    border: 1px solid white;
    padding: 0.7rem;
    border-radius: 50%;
    transform: rotate(-45deg) scale(1.4);
    opacity: 0.9;
    cursor: pointer;

    &:hover {
      background-color: white;
      fill: black;
      opacity: 1;
    }
  `,
};

export default Main;
