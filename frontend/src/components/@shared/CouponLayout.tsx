import styled from '@emotion/styled';
import { COUPON_IMAGE, RAND_COLORS } from '../../constants/coupon';
import { CouponTransmitableType } from '../../types/coupon';

type CouponLayoutProps = {
  id: number;
  name: string;
  title: string;
  couponType: CouponTransmitableType;
};

const CouponLayout = ({
  id = 0,
  name = '',
  title = '',
  couponType = 'coffee',
}: CouponLayoutProps) => {
  return (
    <S.Layout>
      <S.Content
        backgroundColor={RAND_COLORS[id % RAND_COLORS.length].bg}
        color={RAND_COLORS[id % RAND_COLORS.length].color}
      >
        <S.Title>{title}</S.Title>
        <S.Name>{name}</S.Name>
        <S.Coupon>
          <S.CouponImage src={COUPON_IMAGE[couponType]} alt={couponType} />
        </S.Coupon>
      </S.Content>
    </S.Layout>
  );
};

export default CouponLayout;

type ContentStyleProp = {
  backgroundColor: string;
  color: string;
};

const S = {
  Layout: styled.div`
    display: flex;
    flex-direction: column;
    position: relative;

    width: 14.5rem;
    height: 14.5rem;
    box-shadow: rgba(0, 0, 0, 0.15) 0px 3px 3px 0px;
  `,
  Content: styled.div<ContentStyleProp>`
    display: flex;
    align-items: center;
    flex-direction: column;
    justify-content: flex-start;
    gap: 0.8rem;
    padding-top: 0.8rem;
    flex: 1;
    border-radius: 13px;
    background-color: ${({ backgroundColor }) => backgroundColor};
    color: ${({ color }) => color};
    position: relative;
  `,
  Title: styled.div`
    display: flex;
    justify-content: center;
    align-items: center;
    font-size: 1.1rem;
    text-align: center;
    height: 2rem;
  `,
  Coupon: styled.div`
    display: flex;
    justify-content: center;
    align-items: center;
    border-radius: 50%;
    width: 4rem;
    height: 6rem;
    margin: 0 auto;
  `,
  CouponImage: styled.img`
    width: 60px;
    height: 60px;
    object-fit: cover;
  `,
  Name: styled.div`
    font-size: 2rem;
    font-weight: 500;
  `,
};
