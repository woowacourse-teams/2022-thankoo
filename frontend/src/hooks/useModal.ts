import { useEffect, useRef } from 'react';
import { useRecoilState } from 'recoil';
import { modalUnMountTime } from '../constants/modal';
import { modalContentAtom, modalVisibleAtom } from '../recoil/atom';

const useModal = () => {
  const [visible, setVisible] = useRecoilState(modalVisibleAtom);
  const [modalContent, setModalContent] = useRecoilState(modalContentAtom);
  const modalContentRef = useRef<HTMLDivElement>(null);

  useEffect(() => {
    if (modalContentRef.current) {
      const target = modalContentRef.current;
      target?.classList.add('modalContainer', 'onMount');
    }
  }, []);

  const show = () => {
    setVisible(true);
  };

  const close = () => {
    const target = document.getElementsByClassName('onMount modalContainer')[0];
    if (!target) {
      setVisible(false);
      return;
    }

    target?.classList.remove('onMount');
    target?.classList.add('unMount');
    setTimeout(() => {
      setVisible(false);
    }, modalUnMountTime);
  };

  return { show, close, visible, setModalContent, modalContentRef };
};

export default useModal;
