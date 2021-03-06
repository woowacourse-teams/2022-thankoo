import styled from '@emotion/styled';
import MyPageButton from './MyPageButton';
import ScheduledPageButton from './ScheduledPageButton';
import SendButton from './SendButton';

const buttons = [<MyPageButton />, <ScheduledPageButton />, <SendButton />];

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
    position: absolute;
    bottom: 0px;
    left: 0px;
    width: 100%;
    display: flex;
    justify-content: space-around;
    border-top: 0.5px solid #8e8e8e90;
    background-color: #232323;
    align-items: center;
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
