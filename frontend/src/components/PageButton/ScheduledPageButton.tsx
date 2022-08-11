import { css } from '@emotion/react';
import styled from '@emotion/styled';
import EventAvailableIcon from '@mui/icons-material/EventAvailable';
import { Link, useLocation } from 'react-router-dom';
import { ROUTE_PATH } from '../../constants/routes';

const ScheduledPageButton = () => {
  const location = useLocation();

  return (
    <Link to={ROUTE_PATH.MEETINGS}>
      <StyledButton active={location.pathname === ROUTE_PATH.MEETINGS} />
    </Link>
  );
};

type ButtonProps = {
  active: boolean;
};

const StyledButton = styled(EventAvailableIcon)<ButtonProps>`
  transform: scale(1.1);
  border-radius: 50%;
  padding: 0.5rem;
  ${({ active }) =>
    active &&
    css`
      background-color: tomato;
    `}

  transition: all ease-in;
  transition-duration: 0.2s;
  -webkit-transition-duration: 0.2s;

  &:active {
    background-color: ${({ theme }) => theme.button.active.background};
  }
`;
StyledButton.defaultProps = {
  fontSize: 'medium',
};

export default ScheduledPageButton;
