import styled from '@emotion/styled';
import { useRecoilValue } from 'recoil';
import Portal from '../../Portal';
import { modalContentAtom } from '../../recoil/atom';
import useModal from './../../hooks/useModal';

const Modal = () => {
  const modalContent = useRecoilValue(modalContentAtom);
  const { close } = useModal();

  return (
    <Portal>
      <section>
        <S.Dimmer onClick={() => close()} />
        <S.Content>{modalContent}</S.Content>
      </section>
    </Portal>
  );
};

export default Modal;

const S = {
  Dimmer: styled.div`
    position: fixed;
    width: 100%;
    height: 100vh;
    top: 0;
    left: 0;
    background-color: #00000082;
  `,
  Content: styled.div`
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
  `,
};
