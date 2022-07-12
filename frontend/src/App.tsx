import { Routes, Route, Outlet, Navigate } from 'react-router-dom';

import styled from '@emotion/styled';
import Main from './pages/Main';
import SignIn from './pages/SignIn';
import SelectReceiver from './pages/SelectReceiver';
import EnterCouponContent from './pages/EnterCouponContent';
import { useRecoilValue } from 'recoil';
import { authAtom } from './recoil/atom';

const AuthOnly = () => {
  const { accessToken } = useRecoilValue(authAtom);
  return accessToken ? <Outlet /> : <Navigate to='/signin' />;
};
const UnAuthOnly = () => {
  const { accessToken } = useRecoilValue(authAtom);
  return !accessToken ? <Outlet /> : <Navigate to='/' />;
};

function App() {
  return (
    <MobileDiv>
      <Routes>
        <Route element={<AuthOnly />}>
          <Route path='/' element={<Main />} />
          <Route path='/select-receiver' element={<SelectReceiver />} />
          <Route path='/enter-coupon' element={<EnterCouponContent />} />
        </Route>
        <Route element={<UnAuthOnly />}>
          <Route path='/signin' element={<SignIn />} />
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
  background-color: #232323;
  position: relative;
`;

export default App;
