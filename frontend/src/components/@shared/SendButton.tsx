import styled from '@emotion/styled';
import SendIcon from '@mui/icons-material/Send';
import { Link } from 'react-router-dom';
import { ROUTE_PATH } from './../../constants/routes';

const SendButton = () => {
  return (
    <Link to={`${ROUTE_PATH.SELECT_RECEIVER}`}>
      <StyledButton fontSize='small' />
    </Link>
  );
};

const StyledButton = styled(SendIcon)`
  transform: rotate(-45deg) scale(1.4);
  border-radius: 50%;
  padding: 0.5rem;

  transition: all ease-in;
  transition-duration: 0.2s;
  -webkit-transition-duration: 0.2s;

  &:active {
    background-color: ${({ theme }) => theme.button.active.background};
  }
`;

export default SendButton;
