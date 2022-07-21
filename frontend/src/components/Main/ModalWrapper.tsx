import useModal from '../../hooks/useModal';
import Modal from '../@shared/Modal';

const ModalWrapper = ({ children, modalContent }) => {
  const { visible, show, setModalContent } = useModal();

  return (
    <>
      <div
        onClick={() => {
          show();
          setModalContent(modalContent);
        }}
      >
        {children}
      </div>
    </>
  );
};

export default ModalWrapper;
