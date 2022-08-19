import { createPortal } from 'react-dom';

const Portal = ({ children }) => {
  const portalElement = document.getElementById('modal')!;
  return createPortal(children, portalElement);
};

export default Portal;
