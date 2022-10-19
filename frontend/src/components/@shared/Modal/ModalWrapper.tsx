import styled from '@emotion/styled';
import { ReactNode } from 'react';
import useModal from '../../../hooks/useModal';

type ModalWrapperType = {
  children: JSX.Element;
  modal: ReactNode;
};

const ModalWrapper = ({ children, modal }: ModalWrapperType) => {
  const { visible, show, setModalContent } = useModal();

  return (
    <Container
      onClick={() => {
        show();
        setModalContent(modal);
      }}
    >
      {children}
    </Container>
  );
};

export default ModalWrapper;

const Container = styled.div`
  cursor: pointer;
`;
