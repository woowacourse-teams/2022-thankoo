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
    //dimmer 클릭했을땐 targe null....
    const target = modalContentRef.current;
    target?.classList.remove('onMount');
    target?.classList.add('unMount');

    setTimeout(() => {
      setVisible(false);
    }, 200);
  };

  return { show, close, visible, setModalContent, modalContentRef };
};

export default useModal;
