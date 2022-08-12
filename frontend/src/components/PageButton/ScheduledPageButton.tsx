import { css } from '@emotion/react';
import styled from '@emotion/styled';
import EventAvailableIcon from '@mui/icons-material/EventAvailable';
import { Link, useLocation } from 'react-router-dom';
import { ROUTE_PATH } from '../../constants/routes';

const ScheduledPageButton = () => {
  const location = useLocation();

  return (
    <S.Link to={ROUTE_PATH.MEETINGS}>
      <S.ButtonWrapper active={location.pathname === ROUTE_PATH.MEETINGS}>
        <S.Icon />
        <p>약속</p>
      </S.ButtonWrapper>
    </S.Link>
  );
};

type ButtonProps = {
  active: boolean;
};

const S = {
  Link: styled(Link)`
    line-height: 8px;
    p {
      font-size: 12px;
    }
  `,
  ButtonWrapper: styled.div<ButtonProps>`
    opacity: 0.5;
    ${({ active }) =>
      active &&
      css`
        opacity: 1;
        transform: scale(1.1);
      `};
  `,
  Icon: styled(EventAvailableIcon)`
    transform: scale(1.1);
    border-radius: 50%;
    padding: 0.5rem;

    transition: all ease-in;
    transition-duration: 0.2s;
    -webkit-transition-duration: 0.2s;
    opacity: 0.5;
    &:active {
      background-color: ${({ theme }) => theme.button.active.background};
    }
  `,
};

export default ScheduledPageButton;
