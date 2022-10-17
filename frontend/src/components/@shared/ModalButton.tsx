import { ReactNode } from 'react';
import useModal from '../../hooks/useModal';

const ModalButton = ({ children, contents }: { children: JSX.Element; contents: ReactNode }) => {
  const { setModalContent, show } = useModal();
  const onClickBindedChildren = {
    ...children,
    props: {
      ...children.props,
      onClick: () => {
        show();
        setModalContent(contents);
      },
    },
  };

  return <>{onClickBindedChildren}</>;
};
export default ModalButton;
