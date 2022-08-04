import styled from '@emotion/styled';
import useToast from './../../hooks/useToast';
import Portal from './../../Portal';

const Toast = () => {
  const { visible } = useToast();

  return (
    <Portal>
      <S.Container className='toast'>
        <S.Content>{`토스트 메세지`}</S.Content>
      </S.Container>
    </Portal>
  );
};

const S = {
  Container: styled.div`
    .toast-enter {
      opacity: 0;
      transform: translate(-50%, -150%);
    }
    .toast-enter.toast-enter-active {
      opacity: 1;
      transform: translateY(-50%);

      transition: transform 2000ms ease-in-out;
    }
    .toast-exit {
      opacity: 1;
      transform: translateX(-50%);
    }
    .toast-exit.toast-exit-active {
      opacity: 0;
      transform: translate(-50%, -150%);

      transition: transform 2000ms ease-in-out;
    }
  `,

  Content: styled.div`
    width: 200px;
    height: 20px;
    padding: 10px;
    background: white;
    border-radius: 10px;
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
    z-index: 1001;
    text-align: center;
  `,
};

export default Toast;
