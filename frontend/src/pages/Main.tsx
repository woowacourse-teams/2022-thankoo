import GridViewCoupons from '../components/Main/GridViewCoupons';
import CouponTypesNav from '../components/Main/CouponTypesNav';
import ArrowBackButton from '../components/shared/ArrowBackButton';
import useMain from '../hooks/Main/useMain';
import styled from '@emotion/styled';
import SendIcon from '@mui/icons-material/Send';

import { couponTypeKeys } from '../types';
import { Link } from 'react-router-dom';
import PageLayout from '../components/shared/PageLayout';
import Header from '../components/shared/Header';
import HeaderText from '../components/shared/HeaderText';

const Main = () => {
  const { setCurrentType, couponsByType, isLoading, error, currentType } = useMain();

  if (isLoading) return <>로딩중</>;
  if (error) return <>에러뜸</>;

  return (
    <PageLayout>
      <Header>
        <ArrowBackButton />
        <HeaderText>쿠폰함</HeaderText>
      </Header>
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
    </PageLayout>
  );
};

const S = {
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
