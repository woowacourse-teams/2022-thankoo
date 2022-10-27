import styled from '@emotion/styled';
import { ReactNode } from 'react';
import useModal from '../../../hooks/useModal';

type ModalWrapperProps = {
  children: JSX.Element;
  modal: ReactNode;
  isDisabled?: boolean;
};

const ModalWrapper = ({ children, modal, isDisabled = false }: ModalWrapperProps) => {
  const { visible, show, setModalContent } = useModal();

  return (
    <Container
      onClick={() => {
        if (!isDisabled) {
          show();
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
