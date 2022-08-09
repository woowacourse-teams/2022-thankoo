import styled from '@emotion/styled';
import CheckCircleIcon from '@mui/icons-material/CheckCircle';
import { useRecoilValue } from 'recoil';
import { toastContentAtom } from '../../recoil/atom';
import { flexCenter } from '../../styles/mixIn';
import useToast from './../../hooks/useToast';
import Portal from './../../Portal';

const Toast = () => {
  const { visible, toastRef } = useToast();
  const value = useRecoilValue(toastContentAtom);

  return (
    <Portal>
      <S.Container className='toast' ref={toastRef}>
        <S.Content>
          <S.Comment>{value}</S.Comment>
        </S.Content>
      </S.Container>
    </Portal>
  );
};

const S = {
  CheckCircleIcon: styled(CheckCircleIcon)`
    fill: #2bd394;
  `,
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
      animation: myonmount 2000ms ease-in-out;
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
};

export default Toast;
