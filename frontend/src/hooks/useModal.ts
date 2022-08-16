import { useRef } from 'react';
import { useRecoilState } from 'recoil';
import { modalContentAtom, modalVisibleAtom } from '../recoil/atom';

const useModal = () => {
  const [visible, setVisible] = useRecoilState(modalVisibleAtom);
  const [modalContent, setModalContent] = useRecoilState(modalContentAtom);
  const modalContentRef = useRef<HTMLDivElement>(null);

  const show = () => {
    setVisible(true);
  };
  const close = () => {
    setVisible(false);
  };

  return { show, close, visible, setModalContent, modalContentRef };
};

export default useModal;
