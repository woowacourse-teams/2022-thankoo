import styled from '@emotion/styled';
import GoogleIcon from '@mui/icons-material/Google';
import GoogleLogo from '../../assets/svg/google-color-icon.svg';
import BirdLogoWhite from '../../components/@shared/LogoWhite';
import PageLayout from '../../layout/PageLayout';
import { FlexCenter } from '../../styles/mixIn';
import { palette } from './../../styles/ThemeProvider';
import useSignIn from './hooks/useSignIn';

const SignIn = () => {
  const { redirectGoogleAuth } = useSignIn();

  return (
    <PageLayout>
      <S.Inner>
        <S.Body>
          <S.LogoWrapper>
            <BirdLogoWhite size='150rem' />
          </S.LogoWrapper>
          <S.SignInButton onClick={redirectGoogleAuth}>
            <S.GoogleLogoICon src={GoogleLogo} alt='google_login' />
            Google로 계속하기
          </S.SignInButton>
        </S.Body>
      </S.Inner>
    </PageLayout>
  );
};

const S = {
  Body: styled.div`
    ${FlexCenter}

    font-size: 13px;
    height: 100%;
    display: flex;
    flex-direction: column;
    align-items: center;
    gap: 3rem;
  `,
  Inner: styled.div`
    height: 100%;
    padding: 3rem;
  `,
  LogoWrapper: styled.div`
    position: relative;
    height: 320px;
    display: flex;
    flex-direction: column;
    justify-content: center;
    text-align: right;
    color: lightgray;
  `,
  GoogleIcon: styled(GoogleIcon)`
    fill: ${palette.WHITE};
  `,
  SignInButton: styled.div`
    ${FlexCenter};
    gap: 1rem;
    width: 100%;
    height: 3.6rem;
    border-radius: 15px;
    border: none;

    background-color: #4d4d4d;
    color: ${({ theme }) => theme.button.abled.color};

    /* -webkit-animation: glowing 2s ease-in-out infinite alternate;
    -moz-animation: glowing 2s ease-in-out infinite alternate;
    animation: glowing 2s ease-in-out infinite alternate;

    @-webkit-keyframes glowing {
      from {
        box-shadow: 0 0 0px #fff, 0 0 2px #fff, 0 0 4px #ff6450, 0 0 6px #e0e464, 0 0 8px #ff6450;
      }
      to {
        box-shadow: 0 0 0px #fff, 0 0 2px #ff6450, 0 0 4px #e4e7a7, 0 0 6px #ff6450, 0 0 8px #ff6450;
      } */
    /* } */

    cursor: pointer;
  `,
  GoogleLogoICon: styled.img`
    width: 20px;
    height: 20px;
  `,
};

export default SignIn;
