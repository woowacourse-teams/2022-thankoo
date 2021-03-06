import React from 'react';
import { createRoot } from 'react-dom/client';
import { BrowserRouter } from 'react-router-dom';

import App from './App';

import { css, Global } from '@emotion/react';
import { QueryClient, QueryClientProvider } from 'react-query';
import { RecoilRoot } from 'recoil';
import reset from './styles/GlobalReset';
import global from './styles/GlobalStyled';
import { ThemeProvider } from './styles/ThemeProvider';

const rootElement = document.getElementById('root')!;
const root = createRoot(rootElement);

if (process.env.NODE_ENV === 'development') {
  const { worker } = require('./mocks/browser');
  worker.start();
}

const queryClient = new QueryClient();

root.render(
  <React.StrictMode>
    <Global styles={css([reset, global])} />
    <BrowserRouter>
      <QueryClientProvider client={queryClient}>
        <RecoilRoot>
          <ThemeProvider>
            <App />
          </ThemeProvider>
        </RecoilRoot>
      </QueryClientProvider>
    </BrowserRouter>
  </React.StrictMode>
);
