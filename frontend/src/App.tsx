import { Navigate, Outlet, Route, Routes, useLocation } from 'react-router-dom';

import styled from '@emotion/styled';
import { CSSTransition, TransitionGroup } from 'react-transition-group';
import { ROUTE_PATH } from './constants/routes';
import CreateReservation from './pages/CreateReservation';
import EnterCouponContent from './pages/EnterCouponContent';
import EnterNickname from './pages/EnterNickname';
import Main from './pages/Main';
import OnFailurePage from './pages/OnFailurePage';
import OnSuccessPage from './pages/OnSuccessPage';
import Reservations from './pages/Reservations';
import SelectReceiver from './pages/SelectReceiver';
import SignIn from './pages/SignIn';
import UserProfile from './pages/UserProfile';

const AuthOnly = () => {
  const storageToken = localStorage.getItem('token');

  return storageToken ? <Outlet /> : <Navigate to={`${ROUTE_PATH.SIGN_IN}`} />;
};

const UnAuthOnly = () => {
  const storageToken = localStorage.getItem('token');

  return !storageToken ? <Outlet /> : <Navigate to={`${ROUTE_PATH.EXACT_MAIN}`} />;
};

function App() {
  const location = useLocation();

  return (
    <MobileDiv>
      <TransitionGroup component={null}>
        <CSSTransition key={location?.pathname} classNames={'slide'} timeout={300}>
          <Routes>
            <Route path={ROUTE_PATH.ON_SUCCESS} element={<OnSuccessPage />} />
            <Route path={ROUTE_PATH.ON_FAILURE} element={<OnFailurePage />} />
            <Route element={<AuthOnly />}>
              <Route path={ROUTE_PATH.MAIN} element={<Main />} />
              <Route path={ROUTE_PATH.SELECT_RECEIVER} element={<SelectReceiver />} />
              <Route path={ROUTE_PATH.ENTER_COUPON_CONTENT} element={<EnterCouponContent />} />
              <Route path={ROUTE_PATH.CREATE_RESERVATION} element={<CreateReservation />} />
              <Route path={ROUTE_PATH.PROFILE} element={<UserProfile />} />
              <Route path={ROUTE_PATH.RESERVATIONS} element={<Reservations />} />
            </Route>
            <Route element={<UnAuthOnly />}>
              <Route path={ROUTE_PATH.SIGN_IN} element={<SignIn />} />
              <Route path={ROUTE_PATH.ENTER_NICKNAME} element={<EnterNickname />} />
            </Route>
          </Routes>
        </CSSTransition>
      </TransitionGroup>
    </MobileDiv>
  );
}

const MobileDiv = styled.div`
  min-width: 360px;
  margin: 0 auto;
  height: 100vh;
  background-color: ${({ theme }) => theme.page.background};
  position: relative;
  overflow: hidden;

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
