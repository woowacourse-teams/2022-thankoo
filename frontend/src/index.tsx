import React from 'react';
import { createRoot } from 'react-dom/client';

import App from './App';

import reset from './styles/GlobalReset';
import global from './styles/GlobalStyled';
import { css, Global } from '@emotion/react';

const rootElement = document.getElementById('root')!;
const root = createRoot(rootElement);

if (process.env.NODE_ENV === 'development') {
  const { worker } = require('./mocks/browser');
  worker.start();
}

root.render(
  <React.StrictMode>
    <Global styles={css([reset, global])} />
    <App />
  </React.StrictMode>
);
