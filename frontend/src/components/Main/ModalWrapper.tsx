import styled from '@emotion/styled';
import ModalButton from '../@shared/ModalButton';

const ModalWrapper = ({ children, modalContent }) => {
  return <ModalButton inner={modalContent}>{children}</ModalButton>;
};

export default ModalWrapper;

const Container = styled.div`
  cursor: pointer;
`;
