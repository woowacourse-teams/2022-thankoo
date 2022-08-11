import styled from '@emotion/styled';
import { useEffect } from 'react';
import { useRecoilValue } from 'recoil';
import { flexCenter } from '../../../styles/mixIn';
import useToast from './../../../hooks/useToast';
import { toastStackAtom } from './../../../recoil/atom';
import CloseButton from './../CloseButton';

const ToastItem = ({ toastKey, comment }) => {
  const { toastRef, closeToastItem, duration } = useToast();
  const toastStack = useRecoilValue(toastStackAtom);

  useEffect(() => {
    if (toastRef.current) {
      const target = toastRef.current;
      //Todo refactor: use target.getAnimations();
      target?.classList.add('onMount');

      setTimeout(() => {
        target?.classList.remove('onMount');
        target?.classList.add('unMount');

        setTimeout(() => {
          closeToastItem(toastKey);
        }, duration);
      }, duration);
    }
  }, []);

  return (
    <S.ToastItem className='toast' ref={toastRef}>
      <S.Comment>{comment}</S.Comment>
      <S.Interact>
        <CloseButton
          onClick={() => {
            closeToastItem(toastKey);
          }}
          color={'white'}
        />
      </S.Interact>
      <S.ProgressBar duration={duration} />
    </S.ToastItem>
  );
};

const S = {
  ToastItem: styled.div`
    position: relative;
    width: 200px;
    height: 20px;
    padding: 15px 20px;
    background: #ff6347f5;
    color: white;
    border-radius: 4px;
    /* border: 3px green solid; */

    ${flexCenter}
    @keyframes myonmount {
      0% {
        opacity: 0;
        transform: translateX(30px);
      }
      30% {
        transform: translateX(-1px);
      }
      32% {
        transform: translateX(0);
      }
      100% {
        opacity: 1;
      }
    }

    @keyframes myunmount {
      0% {
        opacity: 1;
      }
      2% {
        transform: translateX(-1px);
      }
      5% {
        transform: translateX(0);
      }
      100% {
        opacity: 0;
        transform: translateX(30px);
      }
    }

    &.onMount {
      animation: myonmount 2000ms ease-in-out; //todo 2000ms duration으로 교체
    }
    &.unMount {
      animation: myunmount 2000ms;
    }
  `,
  Interact: styled.div`
    height: 80%;
    display: flex;
    align-items: center;
    position: absolute;
    top: 8px;
    right: 2px;
  `,
  ProgressBar: styled.div<ProgressBarProps>`
    width: 100%;
    height: 5px;
    position: absolute;
    bottom: 0;
    background-color: transparent;
    border-radius: 0 0 4px 4px;

    &:after {
      content: '';
      width: 100%;
      height: inherit;
      position: absolute;
      left: 0;
      background-color: pink;
      animation: progressing ${props => `${props.duration}ms`} linear;
      border-radius: 0 0 4px 4px;
    }

    @keyframes progressing {
      from {
        width: 0;
      }
      to {
        width: 100%;
      }
    }
  `,
  Comment: styled.p`
    margin-left: 5px;
    display: inline;
  `,
};

type ProgressBarProps = {
  duration: number;
};

export default ToastItem;
