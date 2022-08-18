import styled from '@emotion/styled';
import { useEffect } from 'react';
import { useRecoilValue } from 'recoil';
import { flexCenter } from '../../../styles/mixIn';
import useToast from './../../../hooks/useToast';
import { toastStackAtom } from './../../../recoil/atom';
import { onMountToast, unMountToast } from './../../../styles/Animation';
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
    width: 170px;
    height: 20px;
    padding: 10px 10px;
    background: #4b4b4b;
    color: white;
    border-radius: 4px;
    font-size: 1.2rem;
    /* border: 3px green solid; */

    ${flexCenter}

    &.onMount {
      animation: ${onMountToast} 500ms ease-in-out; //todo 2000ms duration으로 교체
    }
    &.unMount {
      animation: ${unMountToast} 2000ms;
    }
  `,

  Comment: styled.p`
    display: inline;
  `,
  Interact: styled.div`
    height: 80%;
    display: flex;
    align-items: center;
    position: absolute;
    top: 7px;
    right: -2px;
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
      background-color: tomato;
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
};

type ProgressBarProps = {
  duration: number;
};

export default ToastItem;
