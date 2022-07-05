import styled from '@emotion/styled';
import Main from './pages/Main';

function App() {
  return (
    <MobileDiv>
      <Main />
    </MobileDiv>
  );
}

const MobileDiv = styled.div`
  width: 360px;
  height: 800px;
  padding: 1rem;
  background-color: #232323;
`;

export default App;
