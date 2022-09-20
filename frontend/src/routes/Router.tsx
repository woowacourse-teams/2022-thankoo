import { lazy, Suspense } from 'react';
import { Navigate, Outlet, Route, Routes } from 'react-router-dom';
import Spinner from '../components/@shared/Spinner';
import { ROUTE_PATH } from './../constants/routes';

const CreateReservation = lazy(() => import('./../pages/CreateReservation'));
const EnterCouponContent = lazy(() => import('./../pages/EnterCouponContent'));
const EnterNickname = lazy(() => import('./../pages/EnterNickname'));
const Hearts = lazy(() => import('./../pages/Hearts'));
const Main = lazy(() => import('./../pages/Main'));
const Meetings = lazy(() => import('./../pages/Meetings'));
const OnFailurePage = lazy(() => import('./../pages/OnFailurePage'));
const OnSuccessPage = lazy(() => import('./../pages/OnSuccessPage'));
const Reservations = lazy(() => import('./../pages/Reservations'));
const SelectReceiver = lazy(() => import('./../pages/SelectReceiver'));
const SignIn = lazy(() => import('./../pages/SignIn'));
const UserProfile = lazy(() => import('./../pages/UserProfile'));

const AuthOnly = () => {
  const storageToken = localStorage.getItem('token');

  return storageToken ? <Outlet /> : <Navigate to={ROUTE_PATH.SIGN_IN} replace={true} />;
};

const UnAuthOnly = () => {
  const storageToken = localStorage.getItem('token');

  return !storageToken ? <Outlet /> : <Navigate to={ROUTE_PATH.EXACT_MAIN} replace={true} />;
};

const Router = () => {
  return (
    <Suspense fallback={<Spinner />}>
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
          <Route path={ROUTE_PATH.MEETINGS} element={<Meetings />} />
          <Route path={ROUTE_PATH.HEARTS} element={<Hearts />} />
          <Route path={ROUTE_PATH.HEARTS} element={<Hearts />} />
        </Route>
        <Route element={<UnAuthOnly />}>
          <Route path={ROUTE_PATH.SIGN_IN} element={<SignIn />} />
          <Route path={ROUTE_PATH.ENTER_NICKNAME} element={<EnterNickname />} />
        </Route>
      </Routes>
    </Suspense>
  );
};

export default Router;
