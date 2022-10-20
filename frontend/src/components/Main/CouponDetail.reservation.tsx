import styled from '@emotion/styled';
import { forwardRef, LegacyRef } from 'react';
import { Link } from 'react-router-dom';
import { CouponDetail } from '../../types/coupon';
import { BASE_URL } from './../../constants/api';
import { getNowKrLocaleFullDateISOFormat, serverDateFormmater } from './../../utils/date';

const defaultRawTime = getNowKrLocaleFullDateISOFormat();

const CouponDetailReserve = (
  { couponDetail }: { couponDetail: CouponDetail },
  ref: LegacyRef<HTMLDivElement>
) => {
  const { coupon, reservation, meeting } = couponDetail;

  const RawTime =
    reservation?.time?.meetingTime ||
    meeting?.time.meetingTime ||
    coupon.modifiedDateTime ||
    defaultRawTime;

  const { date, time } = serverDateFormmater(RawTime);

  return (
    <S.Contents ref={ref}>
      <S.MeetingMembers>
        <S.Label>만날 사람</S.Label>
        <S.MeetingMembersWrapper>
          <S.MeetingMemberImg src={`${BASE_URL}${coupon?.sender.imageUrl}`} />
          <S.Sender>{coupon?.sender.name}</S.Sender>
        </S.MeetingMembersWrapper>
      </S.MeetingMembers>
      <S.DateWrapper>
        <S.FlexColumn>
          <S.Label>날짜</S.Label>
          <S.ContentText>{date}</S.ContentText>
        </S.FlexColumn>
        <S.FlexColumn>
          <S.Label>시간</S.Label>
          <S.ContentText>{time}</S.ContentText>
        </S.FlexColumn>
      </S.DateWrapper>
    </S.Contents>
  );
};

const S = {
  Contents: styled.div`
    position: relative;
    display: flex;
    flex-direction: column;
    justify-content: center;
    height: 100%;
    width: 100%;
    span {
      font-size: 15px;
    }
  `,
  MeetingMembers: styled.div`
    display: flex;
    flex-direction: column;
  `,
  MeetingMembersWrapper: styled.div`
    display: flex;
    padding: 1rem 0;
    gap: 10px;
    flex-direction: column;
    align-items: center;
  `,
  MeetingMemberImg: styled.img`
    width: 45px;
    height: 45px;
    border-radius: 50%;
    object-fit: cover;
  `,
  Label: styled.span`
    font-size: 12px;
    color: #8e8e8e;
  `,
  ContentText: styled.span`
    font-size: 15px;
  `,
  DateWrapper: styled.div`
    display: flex;
    justify-content: space-between;
    align-items: center;
  `,
  FlexColumn: styled.div`
    display: flex;
    flex-direction: column;
    gap: 5px;
  `,
  Sender: styled.span`
    font-size: 18px;
  `,
  Message: styled.div`
    display: flex;
    flex-flow: column;
    font-size: 15px;
    overflow-y: auto;
    gap: 5px;
  `,
  Footer: styled.div`
    display: flex;
    justify-content: center;
    /* height: 15%; */
    align-items: flex-end;
  `,
  UseCouponLink: styled(Link)`
    width: 100%;
  `,
};

export default forwardRef(CouponDetailReserve);
