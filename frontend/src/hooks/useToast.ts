import { useEffect, useRef } from 'react';
import { useRecoilState } from 'recoil';
import { toastContentAtom, toastVisibleAtom } from './../recoil/atom';

const duration = 2000;

const useToast = () => {
  const [visible, setVisible] = useRecoilState(toastVisibleAtom);
  const toastRef = useRef<HTMLDivElement>(null);
  const [content, setContent] = useRecoilState(toastContentAtom);

  const show = value => {
    setVisible(true);
    setContent(value);
  };

  const close = () => {
    setVisible(false);
  };

  useEffect(() => {
    if (toastRef.current) {
      const target = toastRef.current;

      target.classList.add('onMount');

      setTimeout(() => {
        target?.classList.remove('onMount');
        target?.classList.add('unMount');
        setTimeout(() => {
          //close();
        }, duration);
      }, duration);
    }
  }, [visible]);

  return { visible, show, close, toastRef, duration };
};

export default useToast;
