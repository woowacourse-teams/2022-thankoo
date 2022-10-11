import { lazy, Suspense } from 'react';
import { Navigate, Outlet, Route, Routes, useLocation, useOutlet } from 'react-router-dom';
import HeaderAndNavLayout from '../layout/HeaderAndNavLayout';
import Spinner from '../components/@shared/Spinner';
import { ROUTE_PATH } from './../constants/routes';
import { useGetOrganizations } from '../hooks/@queries/organizations';
import Organization from '../pages/Organization';

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

const OrganizationInjector = () => {
  return <Outlet />;
};

export const ORGANIZATIONS = [
  {
    organizationId: 1,
    organizationName: '땡쿠',
    organizationCode: 'THANKOO1',
    orderNumber: 1,
    lastAccessed: true,
  },
  {
    organizationId: 2,
    organizationName: '우아한테크코스 4기',
    organizationCode: 'WOOWACO1',
    orderNumber: 2,
    lastAccessed: false,
  },
];

const Router = () => {
  return (
    <Routes>
      <Route element={<SpinnerSuspense />}>
        <Route path={ROUTE_PATH.ON_SUCCESS} element={<OnSuccessPage />} />
        <Route path={ROUTE_PATH.ON_FAILURE} element={<OnFailurePage />} />
      </Route>
      <Route element={<AuthOnly />}>
        <Route path={ROUTE_PATH.JOIN_ORGANIZATION} element={<Organization />} />
        <Route element={<OrganizationInjector />}>
          <Route element={<HeaderAndNavLayout />}>
            <Route element={<SpinnerSuspense />}>
              <Route path={ROUTE_PATH.MAIN} element={<Main />} />
              <Route path={ROUTE_PATH.SELECT_RECEIVER} element={<SelectReceiver />} />
              <Route path={ROUTE_PATH.RESERVATIONS} element={<Reservations />} />
              <Route path={ROUTE_PATH.MEETINGS} element={<Meetings />} />
              <Route path={ROUTE_PATH.HEARTS} element={<Hearts />} />
            </Route>
          </Route>
          <Route element={<SpinnerSuspense />}>
            <Route path={ROUTE_PATH.CREATE_RESERVATION} element={<CreateReservation />} />
            <Route path={ROUTE_PATH.ENTER_COUPON_CONTENT} element={<EnterCouponContent />} />
            <Route path={ROUTE_PATH.PROFILE} element={<UserProfile />} />
          </Route>
        </Route>
      </Route>
      <Route element={<UnAuthOnly />}>
        <Route element={<SpinnerSuspense />}>
          <Route path={ROUTE_PATH.SIGN_IN} element={<SignIn />} />
          <Route path={ROUTE_PATH.ENTER_NICKNAME} element={<EnterNickname />} />
        </Route>
      </Route>
    </Routes>
  );
};

export default Router;
