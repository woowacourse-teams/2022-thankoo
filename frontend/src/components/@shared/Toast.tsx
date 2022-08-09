import styled from '@emotion/styled';
import { useRecoilValue } from 'recoil';
import { toastContentAtom } from '../../recoil/atom';
import { flexCenter } from '../../styles/mixIn';
import useToast from './../../hooks/useToast';
import Portal from './../../Portal';
import CloseButton from './CloseButton';

const Toast = () => {
  const { visible, toastRef, close, duration } = useToast();
  const value = useRecoilValue(toastContentAtom);

  return (
    <Portal>
      <S.Container className='toast' ref={toastRef}>
        <S.Content>
          <S.Comment>{value}</S.Comment>
          <S.Interact>
            <CloseButton onClick={close} color={'white'} />
          </S.Interact>
          <S.ProgressBar duration={duration} />
        </S.Content>
      </S.Container>
    </Portal>
  );
};

const S = {
  Comment: styled.p`
    margin-left: 5px;
    display: inline;
  `,
  Container: styled.div`
    @keyframes myonmount {
      from {
        opacity: 0;
      }
      to {
        opacity: 1;
      }
    }

    @keyframes myunmount {
      from {
        opacity: 1;
      }
      to {
        opacity: 0;
      }
    }

    &.onMount {
      animation: myonmount 2000ms ease-in-out; //todo 2000ms duration으로 교체
    }
    &.unMount {
      animation: myunmount 2000ms;
    }
  `,

  Content: styled.div`
    width: 200px;
    height: 20px;
    padding: 15px 20px;
    background: #ff6347f5;
    color: white;
    border-radius: 4px;
    /* border: 3px green solid; */
    position: absolute;
    top: 90%;
    left: 50%;
    transform: translate(-50%, 0);
    z-index: 1001;
    text-align: center;

    ${flexCenter}
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

export default Toast;
