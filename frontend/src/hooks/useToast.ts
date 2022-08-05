import { useRecoilState } from 'recoil';
import { toastContentAtom, toastVisibleAtom } from './../recoil/atom';

const useToast = () => {
  const [visible, setVisible] = useRecoilState(toastVisibleAtom);
  const [content, setContent] = useRecoilState(toastContentAtom);

  const show = value => {
    setVisible(true);
    setContent(value);
    setTimeout(() => {
      close();
    }, 2000);
  };

  const close = () => {
    setVisible(false);
  };

  return { visible, show, close };
};

export default useToast;
