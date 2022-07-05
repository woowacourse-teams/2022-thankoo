import React from 'react';
import { createRoot } from 'react-dom/client';

import App from './App';

import reset from './styles/GlobalStyles';
import { Global } from '@emotion/react';

const rootElement = document.getElementById('root')!;
const root = createRoot(rootElement);

root.render(
  <React.StrictMode>
    <Global styles={reset} />
    <App />
  </React.StrictMode>
);
