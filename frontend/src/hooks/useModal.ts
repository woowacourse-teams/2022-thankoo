import { useState } from 'react';

const useModal = () => {
  const [visible, setVisible] = useState(false);

  const show = () => {
    setVisible(true);
  };
  const close = () => {
    setVisible(false);
  };

  return { show, close, visible };
};

export default useModal;
