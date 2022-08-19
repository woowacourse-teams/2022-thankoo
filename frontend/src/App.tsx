import { useLocation } from 'react-router-dom';

import styled from '@emotion/styled';
import Modal from './components/@shared/Modal';
import Toast from './components/@shared/toast/Toast';
import useModal from './hooks/useModal';
import Router from './routes/Router';

function setScreenSize() {
  const vh = window.innerHeight * 0.01; //mobile에서 브라우저 상하단 tab이 가리는 영역 제외한 기준 1vh 재 계산
  document.documentElement.style.setProperty('--vh', `${vh}px`); // '--vh'라는 새 단위를 생성해서 계산한 값을 넣어줌
}
setScreenSize();
window.addEventListener('resize', setScreenSize);

function App() {
  const location = useLocation();
  const { visible: modalVisible } = useModal();

  return (
    <MobileDiv isBlurred={modalVisible}>
      <Router />
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
  height: calc(var(--vh, 1vh) * 100);
  background-color: ${({ theme }) => theme.page.background};
  position: relative;
  overflow: hidden;

  filter: ${({ isBlurred }) => (isBlurred ? `blur(1.2px)` : '')};
`;
export default App;
