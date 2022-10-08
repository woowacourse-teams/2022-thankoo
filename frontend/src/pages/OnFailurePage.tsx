import styled from '@emotion/styled';
import PageLayout from '../layout/PageLayout';

const OnFailurePage = () => {
  return (
    <S.Layout>
      <S.SuccessCheckmark>
        <S.CheckIcon>
          <S.IconTip />
          <S.IconLong />
          <S.IconCircle />
          <S.IconFix />
        </S.CheckIcon>
      </S.SuccessCheckmark>
    </S.Layout>
  );
};

const S = {
  Layout: styled(PageLayout)`
    position: absolute;
    background-color: black;
    height: 100vh;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
    color: white;
  `,
  SuccessCheckmark: styled.div`
    position: relative;
    top: 40%;
    width: 80px;
    height: 115px;
    margin: 0 auto;
    background-color: transparent;
  `,
  CheckIcon: styled.div`
    width: 80px;
    height: 80px;
    position: relative;
    border-radius: 50%;
    box-sizing: content-box;
    border: 4px solid #ff0000;

    &::before {
      top: 3px;
      left: -2px;
      width: 30px;
      transform-origin: 100% 50%;
      border-radius: 100px 0 0 100px;
    }

    &::after {
      top: 0;
      left: 30px;
      width: 60px;
      transform-origin: 0 50%;
      border-radius: 0 100px 100px 0;
      animation: rotate-circle 4.25s ease-in;

      @keyframes rotate-circle {
        0% {
          transform: rotate(-45deg);
        }
        5% {
          transform: rotate(-45deg);
        }
        12% {
          transform: rotate(-405deg);
        }
        100% {
          transform: rotate(-405deg);
        }
      }
    }

    &::before,
    &::after {
      content: '';
      height: 100px;
      position: absolute;
      transform: rotate(-45deg);
    }
  `,
  IconTip: styled.div`
    //icon-line
    height: 5px;
    background-color: #ff0000;
    display: block;
    border-radius: 2px;
    position: absolute;
    z-index: 10;

    //line-tip
    top: 38px;
    right: 17px;
    width: 47px;
    transform: rotate(45deg);
    animation: icon-line-tip 0.75s;

    @keyframes icon-line-tip {
      0% {
        top: 12px;
        right: 24px;
        width: 55px;
      }
      54% {
        top: 38px;
        right: 17px;
        width: 47px;
      }
    }
  `,
  IconLong: styled.div`
    //icon-line
    height: 5px;
    background-color: #ff0000;
    display: block;
    border-radius: 2px;
    position: absolute;
    z-index: 10;

    // line-long
    top: 38px;
    right: 17px;
    width: 47px;
    transform: rotate(-45deg);
    animation: icon-line-long 0.75s;

    @keyframes icon-line-long {
      0% {
        top: 12px;
        right: 10px;
        width: 55px;
      }
      54% {
        top: 38px;
        right: 17px;
        width: 47px;
      }
    }
  `,
  IconCircle: styled.div`
    //icon-circle
    top: -4px;
    left: -4px;
    z-index: 10;
    width: 80px;
    height: 80px;
    border-radius: 50%;
    position: absolute;
    box-sizing: content-box;
    border: 4px solid rgba(175, 76, 76, 0.5);
  `,
  IconFix: styled.div`
    //icon-Fix
    top: 8px;
    width: 5px;
    left: 26px;
    z-index: 1;
    height: 85px;
    position: absolute;
    transform: rotate(-45deg);
  `,
};

export default OnFailurePage;
