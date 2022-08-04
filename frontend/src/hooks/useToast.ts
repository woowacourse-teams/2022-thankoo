import { useRecoilState } from 'recoil';
import { toastVisibleAtom } from './../recoil/atom';

const useToast = () => {
  const [visible, setVisible] = useRecoilState(toastVisibleAtom);

  const show = e => {
    setVisible(true);
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
