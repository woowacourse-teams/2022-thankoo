import styled from '@emotion/styled';
import { Coupon } from '../../types';

const COUPON_COLOR = {
  coffee: '#0C3C33',
  meal: '#FF6450',
};

const COUPON_IMAGE = {
  coffee: 'https://cdn-icons-png.flaticon.com/512/3054/3054889.png',
  meal: 'https://cdn-icons-png.flaticon.com/512/1405/1405021.png',
};

const GridViewCoupon = ({ coupon }: { coupon: Coupon }) => {
  const { sender, content } = coupon;

  return (
    <S.Layout>
      <S.Content backgroundColor={COUPON_COLOR[content.couponType]}>
        <S.Coupon>
          <S.CouponImage src={COUPON_IMAGE[content.couponType]} />
        </S.Coupon>
        <S.Title>{content.title}</S.Title>
        <S.Sender>
          <S.SenderPrefix>from. </S.SenderPrefix>
          {sender.name}
          <S.SenderImage src={sender.imageUrl} />
        </S.Sender>
      </S.Content>
      <S.SplitLine />
      <S.Tip>사용하기</S.Tip>
    </S.Layout>
  );
};

export default GridViewCoupon;

type ContentProp = {
  backgroundColor: string;
};

const S = {
  Layout: styled.div`
    display: flex;
    flex-direction: column;
    background: #fff;
    border-radius: 8px;

    width: 155px;
    height: 215px;
  `,
  Content: styled.div<ContentProp>`
    display: flex;
    flex-direction: column;
    justify-content: space-between;
    flex: 1;
    border-radius: 7px 7px 0 0;
    padding: 1rem 0.5rem;
    background-color: ${({ backgroundColor }) => backgroundColor};
    color: white;
  `,
  Coupon: styled.div`
    display: flex;
    justify-content: center;
    align-items: center;
    border-radius: 50%;
    background-color: white;
    width: 5rem;
    height: 5rem;
    margin: 0 auto;
    box-shadow: rgba(0, 0, 0, 0.15) 2.4px 2.4px 3.2px;
  `,
  CouponImage: styled.img`
    height: 60%;
    object-fit: cover;
  `,
  Title: styled.div`
    display: flex;
    justify-content: center;
    align-items: center;
    font-size: 15px;
    text-align: center;
    height: 2rem;
    word-break: keep-all;
  `,
  Sender: styled.div`
    display: flex;
    justify-content: flex-end;
    align-items: center;
    gap: 10px;
    font-size: 12px;
  `,
  SenderPrefix: styled.span``,
  SenderImage: styled.img`
    border-radius: 50%;
    width: 1.2rem;
    height: 1.2rem;
    object-fit: cover;
  `,
  Tip: styled.button`
    position: relative;
    text-align: center;
    padding: 15px;
    border-radius: 0 0 8px 8px;
    color: white;
    background-color: #ff6450;
    border: none;
    border-top: 2.5px dashed white;
  `,
  SplitLine: styled.div`
    position: relative;
    flex: 0 0 0;
    margin: 0 5px 0 5px;

    &::after,
    ::before {
      content: '';
      position: absolute;
      width: 10px;
      height: 16px;
      background: #232323;
      z-index: 1;
      top: -7px;
    }

    &::after {
      left: -6px;
      border-radius: 0 8px 8px 0;
      clip: rect(auto, auto, 285px, auto);
    }
    &::before {
      right: -6px;
      border-radius: 8px 0 0 8px;
      clip: rect(auto, auto, 285px, auto);
    }
  `,
};
