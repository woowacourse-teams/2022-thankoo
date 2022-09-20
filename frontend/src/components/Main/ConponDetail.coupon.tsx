import styled from '@emotion/styled';
import { forwardRef, LegacyRef } from 'react';
import GridViewCoupon from './GridViewCoupon';
import CouponLayout from '../@shared/CouponLayout';
import { Coupon } from '../../types';
import { useRecoilValue } from 'recoil';
import { sentOrReceivedAtom } from '../../recoil/atom';

const ConponDetailNotUsed = ({ coupon }: { coupon: Coupon }, ref: LegacyRef<HTMLDivElement>) => {
  const sentOrReceived = useRecoilValue(sentOrReceivedAtom);
  const isSent = sentOrReceived === '보낸';

  return (
    <S.Contents ref={ref}>
      <S.CouponArea>
        {sentOrReceived === '보낸' ? (
          <CouponLayout
            id={coupon?.receiver.id}
            couponType={coupon?.content.couponType}
            name={coupon?.receiver.name}
            title={coupon?.content.title}
          />
        ) : (
          <GridViewCoupon coupon={coupon as Coupon} />
        )}
      </S.CouponArea>
      <S.RowWrapper>
        <S.FlexColumn>
          <S.Label>{isSent ? '받은' : '보낸'} 사람</S.Label>
          <S.Sender>{isSent ? coupon?.receiver.name : coupon?.sender.name}</S.Sender>
        </S.FlexColumn>
        <S.FlexColumn>
          <S.Label>{isSent ? '받은' : '보낸'} 날짜</S.Label>
          <S.Sender>{coupon?.createdDate}</S.Sender>
        </S.FlexColumn>
      </S.RowWrapper>
      <S.FlexColumn>
        <S.Label>{isSent && '내가 보낸 '}메세지</S.Label>
        <S.Message>{coupon?.content.message}</S.Message>
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
    gap: 25px;
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
  RowWrapper: styled.div`
    display: flex;
    justify-content: space-between;
  `,
};

export default forwardRef(ConponDetailNotUsed);
