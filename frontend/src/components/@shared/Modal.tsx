import styled from '@emotion/styled';
import { useEffect, useRef } from 'react';
import { useRecoilValue } from 'recoil';
import Portal from '../../Portal';
import { modalContentAtom } from '../../recoil/atom';
import useModal from './../../hooks/useModal';

const Modal = () => {
  const modalContent = useRecoilValue(modalContentAtom);
  const { close } = useModal();
  const ref = useRef<any>();

  useEffect(() => {
    ref.current.focus();
  }, []);

  return (
    <Portal>
      <section
        tabIndex={0}
        ref={ref}
        onKeyDown={e => {
          if (e.nativeEvent.key === 'Escape') close();
        }}
      >
        <S.Dimmer onClick={close} />
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
    height: 100vh;
    top: 0;
    left: 0;
    background-color: #00000082;
    z-index: 100;
  `,
};
