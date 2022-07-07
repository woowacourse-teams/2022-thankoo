import { Routes, Route } from 'react-router-dom';

import styled from '@emotion/styled';
import Main from './pages/Main';
import SelectReceiver from './pages/SelectReceiver';
import EnterCouponContent from './pages/EnterCouponContent';
import { QueryClient, QueryClientProvider } from 'react-query';

const queryClient = new QueryClient();

function App() {
  return (
    <QueryClientProvider client={queryClient}>
      <MobileDiv>
        <Routes>
          <Route path='/' element={<Main />} />
          <Route path='/select-receiver' element={<SelectReceiver />} />
          <Route path='/enter-coupon' element={<EnterCouponContent />} />
        </Routes>
      </MobileDiv>
    </QueryClientProvider>
  );
}

const MobileDiv = styled.div`
  min-width: 360px;
  max-width: 1080px;
  margin: 0 auto;
  height: 100vh;
  background-color: #232323;
`;

export default App;
