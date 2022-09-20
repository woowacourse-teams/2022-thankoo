import styled from '@emotion/styled';
import { Suspense, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { useRecoilValue } from 'recoil';
import Spinner from '../components/@shared/Spinner';
import CreateReservationSuccess from '../components/CreateReservation/CreateReservationSuccess';
import EnterCouponContentSuccess from '../components/EnterCouponContent/EnterCouponContentSuccess';
import { ROUTE_PATH } from '../constants/routes';
import { onSuccessContentAtom } from '../recoil/atom';

const OnSucessModalComponents = {
  [ROUTE_PATH.CREATE_RESERVATION]: props => <CreateReservationSuccess {...props} />,
  [ROUTE_PATH.ENTER_COUPON_CONTENT]: props => <EnterCouponContentSuccess {...props} />,
};

const OnSuccessPage = () => {
  const navigate = useNavigate();
  const { page, props } = useRecoilValue(onSuccessContentAtom);

  useEffect(() => {
    if (!page) {
      navigate(ROUTE_PATH.EXACT_MAIN);
    }
  }, []);

  return (
    <Suspense fallback={<Spinner />}>
      <S.Layout>{OnSucessModalComponents[page]?.(props)}</S.Layout>
    </Suspense>
  );
};

const S = {
  Layout: styled.div`
    position: absolute;
    background-color: black;
    width: 100vw;
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
    border: 4px solid #4caf50;

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
    background-color: #4caf50;
    display: block;
    border-radius: 2px;
    position: absolute;
    z-index: 10;

    //line-tip
    top: 46px;
    left: 14px;
    width: 25px;
    transform: rotate(45deg);
    animation: icon-line-tip 0.75s;

    @keyframes icon-line-tip {
      0% {
        width: 0;
        left: 1px;
        top: 19px;
      }
      54% {
        width: 0;
        left: 1px;
        top: 19px;
      }
      70% {
        width: 50px;
        left: -8px;
        top: 37px;
      }
      84% {
        width: 17px;
        left: 21px;
        top: 48px;
      }
      100% {
        width: 25px;
        left: 14px;
        top: 45px;
      }
    }
  `,
  IconLong: styled.div`
    //icon-line
    height: 5px;
    background-color: #4caf50;
    display: block;
    border-radius: 2px;
    position: absolute;
    z-index: 10;

    // line-long
    top: 38px;
    right: 8px;
    width: 47px;
    transform: rotate(-45deg);
    animation: icon-line-long 0.75s;

    @keyframes icon-line-long {
      0% {
        width: 0;
        right: 46px;
        top: 54px;
      }
      65% {
        width: 0;
        right: 46px;
        top: 54px;
      }
      84% {
        width: 55px;
        right: 0px;
        top: 35px;
      }
      100% {
        width: 47px;
        right: 8px;
        top: 38px;
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
    border: 4px solid rgba(76, 175, 80, 0.5);
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

export default OnSuccessPage;
