import { ReactNode } from 'react';
import useModal from '../../hooks/useModal';

const ModalButton = ({ children, inner }: { children: JSX.Element; inner: ReactNode }) => {
  const { setModalContent, show } = useModal();
  const onClickBindedChildren = {
    ...children,
    props: {
      ...children.props,
      onClick: () => {
        show();
        setModalContent(inner);
      },
    },
  };

  return <>{onClickBindedChildren}</>;
};
export default ModalButton;
