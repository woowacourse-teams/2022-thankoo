import styled from '@emotion/styled';
import { forwardRef, LegacyRef } from 'react';
import { Link } from 'react-router-dom';
import { useReservationDetail } from '../../hooks/Main/useCouponDetail';

const CouponDetailReserve = (
  { couponId }: { couponId: number },
  ref: LegacyRef<HTMLDivElement>
) => {
  //todo: couponDetailNotUsed에서도 재사용
  const { coupon, time, isLoading } = useReservationDetail(couponId);

  if (isLoading) return <></>;

  return (
    <S.Contents ref={ref}>
      <S.MeetingMembers>
        <S.Label>만날 사람</S.Label>
        <S.MeetingMembersWrapper>
          <S.MeetingMemberImg src={coupon.sender.imageUrl} />
          <S.Sender>{coupon.sender.name}</S.Sender>
        </S.MeetingMembersWrapper>
      </S.MeetingMembers>
      <S.DateWrapper>
        <S.FlexColumn>
          <S.Label>날짜</S.Label>
          <S.ContentText>{time.meetingTime.date}</S.ContentText>
        </S.FlexColumn>
        <S.FlexColumn>
          <S.Label>시간</S.Label>
          <S.ContentText>{time.meetingTime.time}</S.ContentText>
        </S.FlexColumn>
      </S.DateWrapper>
    </S.Contents>
  );
};

const S = {
  Header: styled.div`
    display: flex;
    align-items: center;
    justify-content: space-between;
    height: 10%;
    width: 108%;
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
  Contents: styled.div`
    display: flex;
    flex-direction: column;
    justify-content: center;
    height: 100%;
    width: 100%;
    span {
      font-size: 15px;
    }
  `,
  DateWrapper: styled.div`
    display: flex;
    justify-content: space-between;
    align-items: center;
    flex: 1;
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
    flex: 1;
    gap: 5px;
  `,
  Footer: styled.div`
    display: flex;
    justify-content: center;
    /* height: 15%; */
    align-items: flex-end;
  `,
  Button: styled.button`
    border: none;
    border-radius: 4px;
    background-color: ${({ theme }) => theme.primary};
    color: ${({ theme }) => theme.button.abled.color};
    width: 100%;
    padding: 0.7rem;
    font-size: 15px;
    height: fit-content;
  `,
  UseCouponLink: styled(Link)`
    width: 100%;
  `,
};

export default forwardRef(CouponDetailReserve);
