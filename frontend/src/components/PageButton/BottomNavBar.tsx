import styled from '@emotion/styled';
import { flexCenter } from '../../styles/mixIn';
import CouponsPageButton from './CouponListPageButton';
import ReservationPageButton from './ReservationPageButton';
import ScheduledPageButton from './ScheduledPageButton';

const buttons = [<CouponsPageButton />, <ReservationPageButton />, <ScheduledPageButton />];

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
    bottom: 0;
    left: 0;
    width: 100%;
    height: 5rem;
    display: flex;
    justify-content: space-around;
    align-items: center;

    border-top: 0.5px solid #8e8e8e90;
    background-color: #232323;
    padding-bottom: 0.5rem;
    z-index: 50;
  `,
  Button: styled.button`
    ${flexCenter}

    width: 4rem;
    height: 4rem;
    color: white;
    background-color: inherit;
    border: none;
  `,
};

export default BottomNavBar;
