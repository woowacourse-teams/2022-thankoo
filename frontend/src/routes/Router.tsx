import { lazy, Suspense } from 'react';
import { Outlet, Route, Routes } from 'react-router-dom';
import Spinner from '../components/@shared/FallbackSpinner';
import HeaderAndNavLayout from '../layout/HeaderAndNavLayout';
import { ROUTE_PATH } from './../constants/routes';
import { AuthOnly, UnAuthOnly } from './AuthChecker';

const CreateReservation = lazy(() => import('../pages/CreateReservation/CreateReservation'));
const EnterCouponContent = lazy(() => import('../pages/EnterCouponContent/EnterCouponContent'));
const EnterNickname = lazy(() => import('../pages/EnterNickname/EnterNickname'));
const Hearts = lazy(() => import('../pages/Hearts/Hearts'));
const Main = lazy(() => import('../pages/Main/Main'));
const Meetings = lazy(() => import('../pages/Meetings/Meetings'));
const OnFailurePage = lazy(() => import('../pages/OnFailure/OnFailurePage'));
const OnSuccessPage = lazy(() => import('../pages/OnSuccess/OnSuccessPage'));
const Reservations = lazy(() => import('../pages/Reservations/Reservations'));
const SelectReceiver = lazy(() => import('../pages/SelectReceiver/SelectReceiver'));
const SignIn = lazy(() => import('../pages/SignIn/SignIn'));
const UserProfile = lazy(() => import('../pages/Profile/UserProfile'));
const Organization = lazy(() => import('../pages/Organization/Organization'));
const NotFound = lazy(() => import('../pages/NotFound/NotFound'));

const OrganizationInjector = lazy(() => import('./OrganizationInjector'));
const OrganizationRedirector = lazy(() => import('./OrganizationRedirector'));

const Router = () => {
  return (
    <Routes>
      <Route element={<SuspenseWrapper />}>
        <Route path={ROUTE_PATH.ON_SUCCESS} element={<OnSuccessPage />} />
        <Route path={ROUTE_PATH.ON_FAILURE} element={<OnFailurePage />} />
        <Route element={<AuthOnly />}>
          <Route path={ROUTE_PATH.JOIN_ORGANIZATION} element={<Organization />} />
          <Route path={ROUTE_PATH.ORGANIZATIONS}>
            <Route path={`:id`} element={<OrganizationRedirector />}>
              <Route path={`:page`} element={<OrganizationRedirector />} />
            </Route>
          </Route>
          <Route element={<OrganizationInjector />}>
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

const SuspenseWrapper = () => {
  return (
    <Suspense fallback={<Spinner />}>
      <Outlet />
    </Suspense>
  );
};
