import { Navigate, Outlet, Route, Routes } from 'react-router-dom';

import styled from '@emotion/styled';
import { useRecoilValue } from 'recoil';
import { ROUTE_PATH } from './constants';
import EnterCouponContent from './pages/EnterCouponContent';
import Main from './pages/Main';
import SelectReceiver from './pages/SelectReceiver';
import SignIn from './pages/SignIn';
import { authAtom } from './recoil/atom';

const AuthOnly = () => {
  const storageToken = localStorage.getItem('token');
  const { accessToken: tokenState } = useRecoilValue(authAtom);
  const accessToken = storageToken || tokenState;

  return accessToken ? <Outlet /> : <Navigate to={`${ROUTE_PATH.SIGN_IN}`} />;
};

const UnAuthOnly = () => {
  const storageToken = localStorage.getItem('token');
  const { accessToken: tokenState } = useRecoilValue(authAtom);
  const accessToken = storageToken || tokenState;

  return !accessToken ? <Outlet /> : <Navigate to={`${ROUTE_PATH.EXACT_MAIN}`} />;
};

function App() {
  return (
    <MobileDiv>
      <Routes>
        {/* <Route element={<AuthOnly />}> */}
        <Route path={`${ROUTE_PATH.MAIN}`} element={<Main />} />
        <Route path={`${ROUTE_PATH.SELECT_RECEIVER}`} element={<SelectReceiver />} />
        <Route path={`${ROUTE_PATH.ENTER_COUPON}`} element={<EnterCouponContent />} />
        {/* </Route> */}
        <Route element={<UnAuthOnly />}>
          <Route path={`${ROUTE_PATH.SIGN_IN}`} element={<SignIn />} />
        </Route>
      </Routes>
    </MobileDiv>
  );
}

const MobileDiv = styled.div`
  min-width: 360px;
  max-width: 1080px;
  margin: 0 auto;
  height: 100vh;
  background-color: ${({ theme }) => theme.page.background};
  position: relative;
`;

export default App;
