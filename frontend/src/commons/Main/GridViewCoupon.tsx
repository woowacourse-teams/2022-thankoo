import styled from '@emotion/styled';
import { Coupon } from '../../types';

const GridViewCoupon = ({ coupon }: { coupon: Coupon }) => {
  const { sender, content } = coupon;

  return (
    <S.Layout>
      <S.Content>
        <S.Title>{content.title}</S.Title>
        <S.Coupon>{content.couponType}이미지</S.Coupon>
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

const S = {
  Layout: styled.div`
    display: flex;
    flex-direction: column;
    background: #fff;
    border-radius: 8px;

    width: 155px;
    height: 200px;
  `,
  Content: styled.div`
    flex: 1;
    border-radius: 8px 8px 0 0;
    padding: 0.5rem;
    background-color: #00a05f;
    color: white;
  `,
  Coupon: styled.div`
    display: flex;
    justify-content: center;
    align-items: center;
    border-radius: 8px 8px 0 0;
    height: 4rem;
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
  `,
  SenderPrefix: styled.span`
    font-size: 12px;
    margin-right: 5px;
  `,
  SenderImage: styled.img`
    border-radius: 50%;
    width: 2.5rem;
    height: 2.5rem;
  `,
  Tip: styled.button`
    position: relative;
    text-align: center;
    padding: 15px;
    border-radius: 10px 8px 8px 12px;
    box-shadow: rgba(0, 0, 0, 0.4) 0px 0.1px 3px 1px;
    border: none;
  `,
  SplitLine: styled.div`
    position: relative;
    flex: 0 0 0;
    border-top: 2px dashed #232323;
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
