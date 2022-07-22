import styled from '@emotion/styled';
import PersonIcon from '@mui/icons-material/Person';
import { Link } from 'react-router-dom';
import { ROUTE_PATH } from './../../constants/routes';

const MyPageButton = () => {
  return (
    <Link to={`${ROUTE_PATH.PROFILE}`}>
      <StyledButton />
    </Link>
  );
};

const StyledButton = styled(PersonIcon)`
  transform: scale(1.2);
  border-radius: 50%;
  padding: 0.5rem;

  transition: all ease-in;
  transition-duration: 0.2s;
  -webkit-transition-duration: 0.2s;

  &:active {
    background-color: ${({ theme }) => theme.button.active.background};
  }
`;

export default MyPageButton;
