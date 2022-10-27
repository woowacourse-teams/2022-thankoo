import styled from '@emotion/styled';
import { ReactNode } from 'react';
import useModal from '../../../hooks/useModal';

type ModalWrapperProps = {
  children: JSX.Element;
  modal: ReactNode;
  isDisabled?: boolean;
};

const ModalWrapper = ({ children, modal, isDisabled = false }: ModalWrapperProps) => {
  const { showModal, setModalContent } = useModal();

  return (
    <Container
      onClick={() => {
        if (!isDisabled) {
          showModal();
          setModalContent(modal);
        }
      }}
    >
      {children}
    </Container>
  );
};

export default ModalWrapper;

const Container = styled.div``;
