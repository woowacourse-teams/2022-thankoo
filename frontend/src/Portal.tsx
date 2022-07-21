import { createPortal } from 'react-dom';

const Portal = ({ children }) => {
  const portalElement = document.getElementById('root')!;
  return createPortal(children, portalElement);
};

export default Portal;
