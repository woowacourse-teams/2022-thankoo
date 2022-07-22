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
    position: fixed;
    bottom: 0px;
    left: 0px;
    width: 100%;
    background-color: ${({ theme }) => theme.page.background};
    display: flex;
    justify-content: space-around;
    align-items: center;

    border-top: 2px solid ${({ theme }) => theme.page.color};
    border-radius: 16px 16px 0 0;
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
