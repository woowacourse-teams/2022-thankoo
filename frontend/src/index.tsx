import React from 'react';
import { createRoot } from 'react-dom/client';

import App from './App';

import reset from './styles/GlobalReset';
import global from './styles/GlobalStyled';
import { css, Global } from '@emotion/react';

const rootElement = document.getElementById('root')!;
const root = createRoot(rootElement);

root.render(
  <React.StrictMode>
    <Global styles={css([reset, global])} />
    <App />
  </React.StrictMode>
);
