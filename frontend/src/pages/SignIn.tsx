import styled from '@emotion/styled';
import GoogleIcon from '@mui/icons-material/Google';
import { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import PageLayout from '../components/@shared/PageLayout';
import { ROUTE_PATH } from '../constants';
import { flexCenter } from '../styles/mixIn';
import BirdLogoWhite from './../components/@shared/LogoWhite';
import useSignIn from './../hooks/SignIn/useSignIn';

const GOOGLE_AUTH_URL = `https://accounts.google.com/o/oauth2/v2/auth?client_id=135992368964-20ad4ul4e3mmia6iok3r9dpg6bshp4uq.apps.googleusercontent.com&redirect_uri=http://localhost:3000/sign-in&response_type=code&scope=https://www.googleapis.com/auth/userinfo.email https://www.googleapis.com/auth/userinfo.profile openid`;

const SignIn = () => {
  const navigate = useNavigate();
  const { refetchToken, userCode, saveAuth } = useSignIn();

  useEffect(() => {
    if (userCode) {
      try {
        refetchToken().then(({ data }) => {
          saveAuth(data);
          navigate(`${ROUTE_PATH.EXACT_MAIN}`);
        });
      } catch (e) {
        alert('로그인에 실패하였습니다.');
      }
    }
  }, [userCode]);

  const onclick = () => {
    window.location.href = GOOGLE_AUTH_URL;
  };

  return (
    <PageLayout>
      <S.Body>
        <BirdLogoWhite size='120rem' />
        <S.SignInButton onClick={onclick}>
          <S.GoogleIcon />
          Google로 계속하기
        </S.SignInButton>
      </S.Body>
    </PageLayout>
  );
};

const S = {
  Body: styled.div`
    ${flexCenter}

    height: 90vh;
    display: flex;
    flex-direction: column;
    align-items: center;
    gap: 8rem;
  `,
  Introduction: styled.div`
    width: 100%;
    font-size: 40px;
    word-break: keep-all;
    text-align: center;
    line-height: 4rem;
    color: ${({ theme }) => theme.page.color};
  `,
  GoogleIcon: styled(GoogleIcon)`
    fill: white;
  `,
  SignInButton: styled.div`
    ${flexCenter};
    gap: 10px;
    width: 280px;
    height: 36px;
    border-radius: 12px;
    border: none;

    background-color: ${({ theme }) => theme.button.abled.background};
    color: ${({ theme }) => theme.button.abled.color};

    -webkit-animation: glowing 2s ease-in-out infinite alternate;
    -moz-animation: glowing 2s ease-in-out infinite alternate;
    animation: glowing 2s ease-in-out infinite alternate;

    @-webkit-keyframes glowing {
      from {
        box-shadow: 0 0 0px #fff, 0 0 2px #fff, 0 0 4px #ff6450, 0 0 6px #e49364, 0 0 8px #ff6450;
      }
      to {
        box-shadow: 0 0 0px #fff, 0 0 2px #ff6450, 0 0 4px #ff6450, 0 0 6px #ff6450, 0 0 8px #ff6450;
      }
    }

    cursor: pointer;
  `,
};

export default SignIn;
