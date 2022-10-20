import { lazy, Suspense } from 'react';
import { Navigate, Outlet, Route, Routes, useLocation } from 'react-router-dom';
import Spinner from '../components/@shared/Spinner';
import HeaderAndNavLayout from '../layout/HeaderAndNavLayout';
import Organization from '../pages/Organization';
import OrganizationsAccess from '../pages/OrganizationsAccess';
import { ROUTE_PATH } from './../constants/routes';
import NotFound from './../pages/NotFound';

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
const AccessController = lazy(() => import('./AccessController'));

const AuthOnly = () => {
  const storageToken = localStorage.getItem('token');

  const { search } = useLocation();
  if (search) {
    localStorage.setItem('query', search);
  }

  return storageToken ? <Outlet /> : <Navigate to={ROUTE_PATH.SIGN_IN} replace />;
};

const UnAuthOnly = () => {
  const storageToken = localStorage.getItem('token');

  return !storageToken ? <Outlet /> : <Navigate to={ROUTE_PATH.EXACT_MAIN} replace />;
};

const SpinnerSuspense = () => {
  return (
    <Suspense fallback={<Spinner />}>
      <Outlet />
    </Suspense>
  );
};

const Router = () => {
  return (
    <Routes>
      <Route element={<SpinnerSuspense />}>
        <Route path={ROUTE_PATH.ON_SUCCESS} element={<OnSuccessPage />} />
        <Route path={ROUTE_PATH.ON_FAILURE} element={<OnFailurePage />} />
        <Route element={<AuthOnly />}>
          <Route path={ROUTE_PATH.JOIN_ORGANIZATION} element={<Organization />} />
          <Route path={ROUTE_PATH.ORGANIZATIONS}>
            <Route path={`:id`} element={<OrganizationsAccess />}>
              <Route path={`:page`} element={<OrganizationsAccess />} />
            </Route>
          </Route>
          <Route element={<AccessController />}>
            <Route element={<HeaderAndNavLayout />}>
              <Route path={ROUTE_PATH.EXACT_MAIN} element={<Main />} />
              <Route path={ROUTE_PATH.SELECT_RECEIVER} element={<SelectReceiver />} />
              <Route path={ROUTE_PATH.RESERVATIONS} element={<Reservations />} />
              <Route path={ROUTE_PATH.MEETINGS} element={<Meetings />} />
              <Route path={ROUTE_PATH.HEARTS} element={<Hearts />} />
            </Route>
            <Route path={ROUTE_PATH.CREATE_RESERVATION} element={<CreateReservation />} />
            <Route path={ROUTE_PATH.ENTER_COUPON_CONTENT} element={<EnterCouponContent />} />
            <Route path={ROUTE_PATH.PROFILE} element={<UserProfile />} />
            <Route path={ROUTE_PATH.NOT_FOUND} element={<NotFound />} />
          </Route>
        </Route>
        <Route element={<UnAuthOnly />}>
          <Route path={ROUTE_PATH.SIGN_IN} element={<SignIn />} />
          <Route path={ROUTE_PATH.ENTER_NICKNAME} element={<EnterNickname />} />
        </Route>
      </Route>
    </Routes>
  );
};

export default Router;
