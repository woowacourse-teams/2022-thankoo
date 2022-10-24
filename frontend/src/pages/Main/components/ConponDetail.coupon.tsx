import styled from '@emotion/styled';
import { forwardRef, LegacyRef } from 'react';
import GridViewCoupon from './GridViewCoupon';
import CouponLayout from '../../../components/@shared/CouponLayout';
import { useRecoilValue } from 'recoil';
import { sentOrReceivedAtom } from '../../../recoil/atom';
import { FlexColumn, FlexSpaceBetween, gap } from '../../../styles/mixIn';
import { Coupon } from '../../../types/coupon';

const ConponDetailNotUsed = ({ coupon }: { coupon: Coupon }, ref: LegacyRef<HTMLDivElement>) => {
  const sentOrReceived = useRecoilValue(sentOrReceivedAtom);
  const isSent = sentOrReceived === 'sent';

  return (
    <S.Contents ref={ref}>
      <S.CouponArea>
        {sentOrReceived === 'sent' ? (
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
      <div css={FlexSpaceBetween}>
        <div css={[FlexColumn, gap('5px')]}>
          <S.Label>{isSent ? '받은' : '보낸'} 사람</S.Label>
          <S.Sender>{isSent ? coupon?.receiver.name : coupon?.sender.name}</S.Sender>
        </div>
        <div css={[FlexColumn, gap('5px')]}>
          <S.Label>{isSent ? '보낸' : '받은'} 날짜</S.Label>
          <S.Sender>{coupon?.createdDate}</S.Sender>
        </div>
      </div>
      <div css={[FlexColumn, gap('5px')]}>
        <S.Label>{isSent && '내가 보낸 '}메세지</S.Label>
        <S.Message>{coupon?.content.message}</S.Message>
      </div>
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
};

export default forwardRef(ConponDetailNotUsed);
