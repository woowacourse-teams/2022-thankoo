import styled from '@emotion/styled';
import { COUPON_IMAGE, RAND_COLORS } from '../../constants/coupon';
import { Coupon } from '../../types';

const GridViewCoupon = ({ coupon }: { coupon: Coupon }) => {
  const { sender, content, status } = coupon;

  return (
    <S.Layout>
      <S.Content
        backgroundColor={RAND_COLORS[sender.id % RAND_COLORS.length].bg}
        color={RAND_COLORS[sender.id % RAND_COLORS.length].color}
      >
        <S.Title>{content.title}</S.Title>
        <S.SenderName>{sender.name}</S.SenderName>
        <S.Coupon>
          <S.CouponImage src={COUPON_IMAGE[content.couponType]} />
        </S.Coupon>
      </S.Content>
    </S.Layout>
  );
};

export default GridViewCoupon;

type ContentProp = {
  backgroundColor: string;
  color: string;
};

const S = {
  Layout: styled.div`
    display: flex;
    flex-direction: column;
    position: relative;

    width: 145px;
    height: 145px;
    box-shadow: rgba(0, 0, 0, 0.15) 0px 3px 3px 0px;
  `,
  Content: styled.div<ContentProp>`
    display: flex;
    align-items: center;
    flex-direction: column;
    gap: 5px;
    padding-top: 5px;
    flex: 1;
    border-radius: 13px;
    background-color: ${({ backgroundColor }) => backgroundColor};
    color: ${({ color }) => color};
    position: relative;
  `,
  StatusText: styled.div`
    position: absolute;
    width: 100%;
    height: 100%;
    display: flex;
    align-items: center;
    justify-content: center;
    color: white;
  `,
  Title: styled.div`
    display: flex;
    justify-content: center;
    align-items: center;
    font-size: 11px;
    text-align: center;
    height: 2rem;
  `,
  Coupon: styled.div`
    display: flex;
    justify-content: center;
    align-items: center;
    border-radius: 50%;
    width: 4rem;
    height: 4rem;
    margin: 0 auto;
  `,
  CouponImage: styled.img`
    /* width: 100%; */
    height: 100%;
    object-fit: cover;
  `,
  SenderPrefix: styled.span``,
  SenderImage: styled.img`
    border-radius: 50%;
    width: 1.2rem;
    height: 1.2rem;
    object-fit: cover;
  `,
  SenderName: styled.div`
    font-size: 20px;
    font-weight: 500;
  `,
  Sender: styled.div`
    display: flex;
    justify-content: flex-end;
    align-items: center;
    gap: 10px;
    font-size: 12px;
  `,
};
