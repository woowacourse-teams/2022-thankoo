import { useRef } from 'react';
import { useRecoilState } from 'recoil';
import { toastStackAtom } from './../recoil/atom';

const duration = 1000;

const useToast = () => {
  const [toastStack, setToastStack] = useRecoilState(toastStackAtom);
  const toastRef = useRef<HTMLDivElement>(null);

  const insertToastItem = comment => {
    const uniqueKey = Number(new Date());
    setToastStack(prev => [...prev, { key: uniqueKey, comment: comment }]);
  };

  const closeToastItem = uniqueKey => {
    setToastStack(prev => prev.filter(toastItem => toastItem.key !== uniqueKey));
  };

  return { insertToastItem, closeToastItem, toastRef, duration };
};

export default useToast;
