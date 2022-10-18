import styled from '@emotion/styled';
import { ReactNode } from 'react';
import useModal from '../../../hooks/useModal';

type ModalWrapperType = {
  children: JSX.Element;
  modal: ReactNode;
};

const ModalWrapper = ({ children, modal }: ModalWrapperType) => {
  const { visible, show, setModalContent } = useModal();
  const onClickBindedChildren = {
    ...children,
    props: {
      ...children.props,
      onClick: () => {
        children.props.onClick?.();
        show();
        setModalContent(modal);
      },
    },
  };

  return <Container>{onClickBindedChildren}</Container>;
};

export default ModalWrapper;

const Container = styled.div`
  cursor: pointer;
`;
