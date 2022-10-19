import React from 'react';
import { createRoot } from 'react-dom/client';
import { BrowserRouter } from 'react-router-dom';

import App from './App';

import { css, Global } from '@emotion/react';
import { QueryClientProvider } from 'react-query';
import { ReactQueryDevtools } from 'react-query/devtools';
import { RecoilRoot } from 'recoil';
import { queryClient } from './apis/queryClient';
import './assets/favicon/favicon.ico';
import CustomErrorBoundary from './errors/CustomErrorBoundary';
import ErrorFallBack from './errors/ErrorFallBack';
import reset from './styles/GlobalReset';
import global from './styles/GlobalStyled';
import { ThemeProvider } from './styles/ThemeProvider';

const rootElement = document.getElementById('root')!;
const root = createRoot(rootElement);

if (process.env.MODE === 'noServer') {
  const { worker } = require('./mocks/browser');
  worker.start();
}

root.render(
  <React.StrictMode>
    <Global styles={css([reset, global])} />
    <BrowserRouter>
      <QueryClientProvider client={queryClient}>
        <RecoilRoot>
          <ThemeProvider>
            <CustomErrorBoundary fallbackComponent={ErrorFallBack}>
              <App />
            </CustomErrorBoundary>
          </ThemeProvider>
        </RecoilRoot>
        <ReactQueryDevtools />
      </QueryClientProvider>
    </BrowserRouter>
  </React.StrictMode>
);
