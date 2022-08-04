import styled from '@emotion/styled';
import { Link } from 'react-router-dom';
import ArrowBackButton from '../components/@shared/ArrowBackButton';
import Slider from '../components/@shared/ChoiceSlider';
import Header from '../components/@shared/Header';
import HeaderText from '../components/@shared/HeaderText';
import PageLayout from '../components/@shared/PageLayout';
import { COUPON_IMAGE } from '../constants/coupon';
import { ROUTE_PATH } from '../constants/routes';
import useMeetings from '../hooks/Meetings/domain/useMeetings';

const Meetings = () => {
  const { isLoading, meetings, isError, isTodayMeetingExist, diffWithNearestDate } = useMeetings();

  if (isLoading) {
    return <div></div>;
  }

  return (
    <PageLayout>
      <Header>
        <Link to={ROUTE_PATH.EXACT_MAIN}>
          <ArrowBackButton />
        </Link>
        <HeaderText>
          {meetings?.length
            ? isTodayMeetingExist
              ? '오늘 예정된 약속이 있습니다'
              : `${diffWithNearestDate}일 뒤 약속이 있습니다`
            : '예정된 약속이 없습니다.'}
        </HeaderText>
      </Header>
      <S.Body>
        {meetings?.map((meeting, idx) => (
          // <Slider></Slider>
          <S.Meeting isToday={diffWithNearestDate === 0 && idx === 0} key={idx}>
            {diffWithNearestDate === 0 && idx === 0 && <S.TodayStrap>오늘</S.TodayStrap>}
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
                  <span>{meeting.time?.meetingTime.split(' ')[0]}</span>
                </S.DetailWrapper>
                <S.DetailWrapper>
                  <S.Label>시간</S.Label>
                  <span>{meeting.time?.meetingTime.split(' ')[1].slice(0, 5)}</span>
                </S.DetailWrapper>
              </S.DateWrapper>
            </S.MeetingDetail>
          </S.Meeting>
        ))}
      </S.Body>
    </PageLayout>
  );
};

export default Meetings;

type MeetingWrapperProps = {
  isToday: boolean;
};

const S = {
  Body: styled.section`
    display: flex;
    flex-direction: column;
    gap: 1rem;
    padding: 5vh 3vw;
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
