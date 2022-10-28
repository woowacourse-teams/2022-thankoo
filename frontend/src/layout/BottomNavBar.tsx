import styled from '@emotion/styled';
import CouponsPageButton from '../components/PageButton/CouponListPageButton';
import HeartPageButton from '../components/PageButton/HeartPageButton';
import ScheduledPageButton from '../components/PageButton/MeetingPageButton';
import ReservationPageButton from '../components/PageButton/ReservationPageButton';
import SendCouponPageButton from '../components/PageButton/SendCouponPageButton';
import { palette } from './../styles/ThemeProvider';

const buttons = [
  <CouponsPageButton />,
  <HeartPageButton />,
  <SendCouponPageButton />,
  <ReservationPageButton />,
  <ScheduledPageButton />,
];

const BottomNavBar = () => {
  return (
    <S.Bar>
      {buttons.map((button, idx) => (
        <S.Button key={idx}>{button}</S.Button>
      ))}
    </S.Bar>
  );
};

const S = {
  Bar: styled.div`
    position: fixed;
    bottom: 0;
    left: 0;
    width: 100%;
    height: 5.5rem;
    display: flex;
    justify-content: space-around;
    align-items: center;

    border-top: 0.5px solid #8e8e8e90;
    background-color: #232323;
    z-index: 50;
  `,
  Button: styled.button`
    display: flex;
    flex-direction: column;
    justify-content: flex-start;
    align-items: center;

    width: 4rem;
    height: 5rem;
    color: ${palette.WHITE};
    background-color: inherit;
    border: none;
  `,
};

export default BottomNavBar;
