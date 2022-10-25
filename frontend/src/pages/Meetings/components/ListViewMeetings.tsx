import styled from '@emotion/styled';
import { COUPON_IMAGE } from '../../../constants/coupon';
import useMeetings from '../hooks/useMeetings';
import HeaderText from '../../../layout/HeaderText';
import ConditionalViewer from '../../../components/@shared/ConditionalViewer';
import NoMeeting from '../../../components/@shared/noContent/NoMeeting';

const ListViewMeetings = () => {
  const { meetings, meetingDateAnnouncement } = useMeetings();

  return (
    <>
      <S.HeaderText>{meetingDateAnnouncement}</S.HeaderText>
      <S.Body>
        <ConditionalViewer condition={meetings?.length !== 0} replacement={<NoMeeting />}>
          {meetings?.map((meeting, idx) => (
            <S.Meeting isToday={meeting.isMeetingToday} key={idx}>
              {meeting.isMeetingToday && <S.TodayStrap>오늘</S.TodayStrap>}
              <S.CouponImageWrapper>
                <S.CouponTypeImage src={COUPON_IMAGE[meeting.couponType]} />
              </S.CouponImageWrapper>
              <S.MeetingDetail>
                <S.DetailWrapper>
                  <S.Label>만날 사람</S.Label>
                  <span>{meeting.memberName}</span>
                </S.DetailWrapper>
                <S.DateWrapper>
                  <S.DetailWrapper>
                    <S.Label>날짜</S.Label>
                    <span>{meeting.meetingDate}</span>
                  </S.DetailWrapper>
                  <S.DetailWrapper>
                    <S.Label>시간</S.Label>
                    <span>{meeting.meetingTime}</span>
                  </S.DetailWrapper>
                </S.DateWrapper>
              </S.MeetingDetail>
            </S.Meeting>
          ))}
        </ConditionalViewer>
      </S.Body>
    </>
  );
};

export default ListViewMeetings;

type MeetingWrapperProps = {
  isToday: boolean;
};

const S = {
  HeaderText: styled(HeaderText)`
    color: white;
  `,
  Body: styled.section`
    display: block;
    flex-direction: column;
    gap: 1rem;
    height: 100%;
    overflow: auto;
  `,
  Meeting: styled.div<MeetingWrapperProps>`
    position: relative;
    width: 100%;
    color: white;
    border-radius: 4px;
    display: flex;
    gap: 20px;
    box-sizing: border-box;
    align-items: center;
    padding: 10px;
    background-color: #4a4a4a;
    border: ${({ theme, isToday }) => (isToday ? '2px solid tomato' : 'unset')};
    overflow: hidden;
    font-size: 1.5rem;
    margin: 10px 0;
  `,
  TodayStrap: styled.span`
    position: absolute;
    font-size: 15px;
    top: 7px;
    right: -20px;
    padding: 5px 30px;
    background-color: ${({ theme }) => theme.primary};
    color: white;
    transform: rotate(40deg);
  `,
  MeetingDetail: styled.div`
    width: 100%;
    display: flex;
    flex-flow: column;
    gap: 5px;
  `,
  DetailWrapper: styled.div`
    display: flex;
    flex-flow: column;
    gap: 3px;
  `,
  CouponImageWrapper: styled.div`
    width: 65px;
    display: flex;
    justify-content: center;
  `,
  CouponTypeImage: styled.img`
    width: 40px;
    object-fit: cover;
  `,
  Label: styled.span`
    color: #838383;
    font-size: 12px;
  `,
  DateWrapper: styled.div`
    display: flex;
    justify-content: space-between;
  `,
};
