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
        {/* <EnterCouponContent /> */}
        {/* <SelectReceiver /> */}
        <Main />
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
