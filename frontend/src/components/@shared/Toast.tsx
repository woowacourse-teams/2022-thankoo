import styled from '@emotion/styled';
import CheckCircleIcon from '@mui/icons-material/CheckCircle';
import { flexCenter } from '../../styles/mixIn';
import useToast from './../../hooks/useToast';
import Portal from './../../Portal';

const Toast = () => {
  const { visible } = useToast();

  return (
    <Portal>
      <S.Container className='toast'>
        <S.Content>
          <S.CheckCircleIcon />
          <S.Comment>수정에 성공했습니다</S.Comment>
        </S.Content>
      </S.Container>
    </Portal>
  );
};

const S = {
  CheckCircleIcon: styled(CheckCircleIcon)`
    fill: green;
  `,
  Comment: styled.p`
    margin-left: 5px;
    display: inline;
  `,

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
    border: 3px green solid;
    position: absolute;
    top: 90%;
    left: 50%;
    transform: translate(-50%, 0);
    z-index: 1001;
    text-align: center;

    ${flexCenter}
  `,
};

export default Toast;
