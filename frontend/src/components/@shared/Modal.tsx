import styled from '@emotion/styled';
import Portal from '../../Portal';

const Modal = ({ children }) => {
  return (
    <Portal>
      <S.Container>{children}</S.Container>
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
