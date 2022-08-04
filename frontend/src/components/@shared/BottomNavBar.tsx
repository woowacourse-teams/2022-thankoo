import styled from '@emotion/styled';
import MyPageButton from '../PageButton/MyPageButton';
import ScheduledPageButton from '../PageButton/ScheduledPageButton';
import SendButton from './SendButton';
import ReservationPageButton from '../PageButton/ReservationPageButton';

const buttons = [
  <MyPageButton />,
  <ReservationPageButton />,
  <ScheduledPageButton />,
  <SendButton />,
];

const BottomNavBar = () => {
  return (
    <S.Bar>
      {buttons.map((button, idx) => {
        return <S.Button key={idx}>{button}</S.Button>;
      })}
    </S.Bar>
  );
};

const S = {
  Bar: styled.div`
    position: fixed;
    bottom: 0px;
    left: 0px;
    width: 100%;
    display: flex;
    justify-content: space-around;
    border-top: 0.5px solid #8e8e8e90;
    background-color: #232323;
    align-items: center;
    z-index: 50;
  `,
  Button: styled.button`
    width: 4rem;
    height: 4rem;
    color: white;
    background-color: inherit;
    border: none;
  `,
};

export default BottomNavBar;
