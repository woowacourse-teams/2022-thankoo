import styled from '@emotion/styled';
import GridViewCoupon from './GridViewCoupon';

const GridViewCoupons = () => {
  return (
    <S.Container>
      {[0, 0, 0, 0].map((_, idx) => (
        <GridViewCoupon />
      ))}
    </S.Container>
  );
};

export default GridViewCoupons;

const S = {
  Container: styled.div`
    display: grid;
    grid-template-columns: repeat(2, 1fr);
    gap: 15px;
  `,
};
