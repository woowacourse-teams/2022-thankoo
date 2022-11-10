import { ComponentPropsWithoutRef, ReactNode } from 'react';
import useModal from '../../../hooks/useModal';

type ModalWrapperProps = {
  children: JSX.Element;
  modal: ReactNode;
  isDisabled?: boolean;
} & ComponentPropsWithoutRef<'div'>;

const ModalWrapper = ({ children, modal, isDisabled = false, ...props }: ModalWrapperProps) => {
  const { showModal, setModalContent } = useModal();

  return (
    <div
      onClick={() => {
        if (!isDisabled) {
          showModal();
          setModalContent(modal);
        }
      }}
      {...props}
    >
      {children}
    </div>
  );
};

export default ModalWrapper;
