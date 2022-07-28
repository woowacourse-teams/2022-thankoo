import styled from '@emotion/styled';
import useModal from '../../hooks/useModal';

const ModalWrapper = ({ children, modalContent }) => {
  const { visible, show, setModalContent } = useModal();

  return (
    <Container
      onClick={() => {
        show();
        setModalContent(modalContent);
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
