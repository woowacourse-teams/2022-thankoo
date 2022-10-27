import styled from '@emotion/styled';
import { useEffect, useRef } from 'react';
import { useLocation } from 'react-router-dom';
import { useRecoilValue } from 'recoil';
import Portal from '../../Portal';
import { modalContentAtom } from '../../recoil/atom';
import useModal from './../../hooks/useModal';
import Dimmer from './Dimmer';

const Modal = () => {
  const { pathname } = useLocation();
  const currentPathname = useRef<string>(pathname);
  const modalContent = useRecoilValue(modalContentAtom);
  const { closeModal, visible } = useModal();
  const ref = useRef<any>();

  useEffect(() => {
    ref.current.focus();
  }, []);

  useEffect(() => {
    if (pathname !== currentPathname.current) {
      close();
    }
  }, [pathname]);

  return (
    <Portal>
      <section
        tabIndex={0}
        ref={ref}
        onKeyDown={e => {
          if (e.nativeEvent.key === 'Escape') closeModal();
        }}
      >
        <Dimmer show={!!visible} onClick={closeModal} />
        {modalContent}
      </section>
    </Portal>
  );
};

export default Modal;

const S = {
  Dimmer: styled.div`
    position: fixed;
    width: 100%;
    height: 100%;
    top: 0;
    left: 0;
    background-color: #00000082;
    z-index: 100;
  `,
};
