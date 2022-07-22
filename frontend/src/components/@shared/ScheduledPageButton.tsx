import styled from '@emotion/styled';
import EventAvailableIcon from '@mui/icons-material/EventAvailable';
import { Link } from 'react-router-dom';
import { ROUTE_PATH } from './../../constants/routes';

const ScheduledPageButton = () => {
  return (
    <Link to={`${ROUTE_PATH.RESERVATIONS}`}>
      <StyledButton />
    </Link>
  );
};

const StyledButton = styled(EventAvailableIcon)`
  transform: scale(1.1);
  border-radius: 50%;
  padding: 0.5rem;

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
