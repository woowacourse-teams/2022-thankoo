import React from 'react';
import { createRoot } from 'react-dom/client';
import { BrowserRouter } from 'react-router-dom';

import App from './App';

import { css, Global } from '@emotion/react';
import { QueryClientProvider } from 'react-query';
import { ReactQueryDevtools } from 'react-query/devtools';
import { RecoilRoot } from 'recoil';
import reset from './styles/GlobalReset';
import global from './styles/GlobalStyled';
import { ThemeProvider } from './styles/ThemeProvider';
import { queryClient } from './apis/queryClient';

const rootElement = document.getElementById('root')!;
const root = createRoot(rootElement);

if (process.env.NODE_ENV === 'development') {
  const { worker } = require('./mocks/browser');
  worker.start();
}

console.log('index process.env.NODE_ENV', process.env.API_URL);

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
        <ReactQueryDevtools />
      </QueryClientProvider>
    </BrowserRouter>
  </React.StrictMode>
);
