import styled from '@emotion/styled';

const GridViewCoupon = () => {
  return (
    <S.Layout>
      <S.Content>
        <S.Coupon>호호의 커피쿠폰</S.Coupon>
        <S.Title>고마워 비녀~~</S.Title>
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
  `,
  Content: styled.div`
    flex: 1;
    border-radius: 8px 8px 0 0;
  `,
  Coupon: styled.div`
    display: flex;
    justify-content: center;
    align-items: center;
    color: white;
    background-color: #00a05f;
    border-radius: 8px 8px 0 0;
    height: 5rem;
  `,
  Title: styled.div`
    display: flex;
    justify-content: center;
    align-items: center;
    font-size: 10px;
    text-align: center;
    height: 2rem;
  `,
  Tip: styled.div`
    position: relative;
    text-align: center;
    padding: 15px;
    border-radius: 10px 8px 8px 12px;
    box-shadow: rgba(0, 0, 0, 0.4) 0px 0.1px 3px 1px;
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
      width: 9px;
      height: 16px;
      background: #232323;
      z-index: 1;
      top: -7px;
    }

    &::after {
      left: -5px;
      border-radius: 0 8px 8px 0;
      clip: rect(auto, auto, 285px, auto);
    }
    &::before {
      right: -5px;
      border-radius: 8px 0 0 8px;
      clip: rect(auto, auto, 285px, auto);
    }
  `,
};
