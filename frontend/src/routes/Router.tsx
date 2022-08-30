import { Navigate, Outlet, Route, Routes } from 'react-router-dom';
import { ROUTE_PATH } from './../constants/routes';
import CreateReservation from './../pages/CreateReservation';
import EnterCouponContent from './../pages/EnterCouponContent';
import EnterNickname from './../pages/EnterNickname';
import Hearts from './../pages/Hearts';
import Main from './../pages/Main';
import Meetings from './../pages/Meetings';
import OnFailurePage from './../pages/OnFailurePage';
import OnSuccessPage from './../pages/OnSuccessPage';
import Reservations from './../pages/Reservations';
import SelectReceiver from './../pages/SelectReceiver';
import SignIn from './../pages/SignIn';
import UserProfile from './../pages/UserProfile';

const AuthOnly = () => {
  const storageToken = localStorage.getItem('token');

  return storageToken ? <Outlet /> : <Navigate to={`${ROUTE_PATH.SIGN_IN}`} />;
};

const UnAuthOnly = () => {
  const storageToken = localStorage.getItem('token');

  return !storageToken ? <Outlet /> : <Navigate to={`${ROUTE_PATH.EXACT_MAIN}`} />;
};

const Router = () => {
  return (
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
      </Route>
      <Route element={<UnAuthOnly />}>
        <Route path={ROUTE_PATH.SIGN_IN} element={<SignIn />} />
        <Route path={ROUTE_PATH.ENTER_NICKNAME} element={<EnterNickname />} />
      </Route>
    </Routes>
  );
};

export default Router;
