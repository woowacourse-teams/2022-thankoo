import styled from '@emotion/styled';
import { forwardRef, LegacyRef } from 'react';
import { useNotUsedCouponDetail } from '../../hooks/Main/useCouponDetail';
import GridViewCoupon from './GridViewCoupon';

const ConponDetailNotUsed = (
  { couponId }: { couponId: number },
  ref: LegacyRef<HTMLDivElement>
) => {
  //todo: couponDetailNotUsed에서도 재사용
  const { coupon, isLoading, isError } = useNotUsedCouponDetail(couponId);

  if (isLoading) {
    return <></>;
  }

  return (
    <S.Contents ref={ref}>
      <S.CouponArea>
        <GridViewCoupon coupon={coupon} />
      </S.CouponArea>
      <S.FlexColumn>
        <S.Label>보낸 사람</S.Label>
        <S.Sender>{coupon.sender.name}</S.Sender>
      </S.FlexColumn>
      <S.FlexColumn>
        <S.Label>메세지</S.Label>
        <S.Message>{coupon.content.message}</S.Message>
      </S.FlexColumn>
    </S.Contents>
  );
};
const S = {
  CouponArea: styled.div`
    display: flex;
    justify-content: center;
    height: fit-content;
    transform: scale(0.8);
  `,
  Contents: styled.div`
    display: flex;
    flex-direction: column;
    justify-content: center;
    gap: 10px;
    width: 100%;
  `,
  SpaceBetween: styled.div`
    display: flex;
    justify-content: space-between;
    align-items: center;
    flex: 1;
  `,
  Sender: styled.span`
    font-size: 15px;
  `,
  Message: styled.span`
    font-size: 15px;
    overflow-y: auto;
    height: 60px;
  `,
  Label: styled.span`
    font-size: 12px;
    color: #8e8e8e;
  `,
  ContentText: styled.span`
    font-size: 15px;
  `,
  FlexColumn: styled.div`
    display: flex;
    flex-direction: column;
    gap: 5px;
  `,
};

export default forwardRef(ConponDetailNotUsed);
