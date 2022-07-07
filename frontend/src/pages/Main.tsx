import GridViewCoupons from '../commons/Main/GridViewCoupons';
import CouponTypesNav from '../commons/Main/CouponTypesNav';
import ArrowBackButton from '../commons/Header/ArrowBackButton';
import useMain from '../hooks/Main/useMain';
import styled from '@emotion/styled';

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
        <CouponTypesNav onChangeType={setCurrentType} currentType={currentType} />
        {couponsByType && <GridViewCoupons coupons={couponsByType} />}
      </S.Body>
    </S.Container>
  );
};

const S = {
  Container: styled.div`
    display: flex;
    flex-direction: column;
    gap: 50px;
    padding: 5px;
  `,
  Header: styled.header`
    display: flex;
    flex-direction: column;
    align-items: flex-start;
    gap: 15px;
    color: white;
    margin: 10px 0 0 10px;
  `,
  HeaderText: styled.p`
    font-size: 22px;
    margin-left: 10px;
  `,
  Body: styled.div`
    display: flex;
    flex-direction: column;
    gap: 1rem;
    padding: 15px;
  `,
};

export default Main;
