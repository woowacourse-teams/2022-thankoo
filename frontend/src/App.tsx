import { useLocation } from 'react-router-dom';

import styled from '@emotion/styled';
import Modal from './components/@shared/Modal';
import Toast from './components/@shared/toast/Toast';
import useModal from './hooks/useModal';
import Router from './routes/Router';

function App() {
  const location = useLocation();
  const { visible: modalVisible } = useModal();

  return (
    <MobileDiv isBlurred={modalVisible}>
      {/* <TransitionGroup component={null}> */}
      {/* <CSSTransition key={location?.pathname} classNames={'slide'} timeout={200}> */}
      <Router />
      {/* </CSSTransition> */}
      {/* </TransitionGroup> */}

      {modalVisible && <Modal />}
      {<Toast />}
    </MobileDiv>
  );
}

type MobileDivProp = {
  isBlurred: Boolean;
};

const MobileDiv = styled.div<MobileDivProp>`
  min-width: 360px;
  margin: 0 auto;
  height: 100vh;
  background-color: ${({ theme }) => theme.page.background};
  position: relative;
  overflow: hidden;

  filter: ${({ isBlurred }) => (isBlurred ? `blur(1.2px)` : '')};

  .slide-enter {
    opacity: 0;
    transform: translateX(100%);
  }
  .slide-enter.slide-enter-active {
    opacity: 1;
    transform: translateX(0);

    transition: transform 200ms ease-in-out;
  }
  .slide-exit {
    opacity: 1;
    transform: translateX(0);
  }
  .slide-exit.slide-exit-active {
    opacity: 0;
    transform: translateX(-100%);

    transition: transform 200ms ease-in-out;
  }
`;
export default App;
