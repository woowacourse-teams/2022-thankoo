import styled from '@emotion/styled';
import GoogleIcon from '@mui/icons-material/Google';
import PageLayout from '../components/@shared/PageLayout';
import { flexCenter } from '../styles/mixIn';
import BirdLogoWhite from './../components/@shared/LogoWhite';
import useSignIn from './../hooks/SignIn/useSignIn';

const SignIn = () => {
  const { redirectGoogleAuth } = useSignIn();

  return (
    <PageLayout>
      <S.Body>
        <BirdLogoWhite size='120rem' />
        <S.SignInButton onClick={redirectGoogleAuth}>
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
