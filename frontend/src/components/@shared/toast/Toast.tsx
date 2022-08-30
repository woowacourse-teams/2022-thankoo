import styled from '@emotion/styled';
import { useRecoilValue } from 'recoil';
import Portal from '../../../Portal';
import { toastStackAtom } from './../../../recoil/atom';
import ToastItem from './ToastItem';

const Toast = () => {
  const toastStack = useRecoilValue(toastStackAtom);

  return (
    <Portal>
      <S.ToastItemsContainer className='toast-container'>
        {toastStack.map(item => {
          return <ToastItem key={item.key} toastKey={item.key} comment={item.comment} />;
        })}
      </S.ToastItemsContainer>
    </Portal>
  );
};

const S = {
  ToastItemsContainer: styled.div`
    position: absolute;
    bottom: 2.5%;
    left: 50%;
    transform: translate(-50%, 0);
    z-index: 1001;
    text-align: center;
    display: flex;
    flex-direction: column-reverse;
    height: fit-content;
    gap: 10px;
    z-index: 10001;
  `,
};

export default Toast;
