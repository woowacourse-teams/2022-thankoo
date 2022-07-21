import styled from '@emotion/styled';
import { useNavigate } from 'react-router-dom';
import { ROUTE_PATH } from '../../constants/routes';

const SignOutButton = () => {
  const navigate = useNavigate();

  const signOut = () => {
    localStorage.removeItem('token');
    navigate(`${ROUTE_PATH.SIGN_IN}`);
  };
  return <S.Container onClick={signOut}>로그아웃</S.Container>;
};

const S = {
  Container: styled.button`
    margin-right: 16px;
    border: none;
    background-color: transparent;
    color: ${({ theme }) => theme.input.color};
    font-size: 18px;
  `,
};
export default SignOutButton;
