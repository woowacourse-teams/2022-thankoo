import styled from '@emotion/styled';
import { useRecoilValue } from 'recoil';
import Portal from '../../Portal';
import { modalContentAtom } from '../../recoil/atom';

const Modal = () => {
  const modalContent = useRecoilValue(modalContentAtom);

  return (
    <Portal>
      <S.Container>{modalContent}</S.Container>
    </Portal>
  );
};

export default Modal;

const S = {
  Container: styled.section`
    position: fixed;
    width: 100%;
    height: 100vh;
    top: 0;
    left: 0;
    background-color: #00000082;
  `,
};
