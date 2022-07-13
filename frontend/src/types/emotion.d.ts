import '@emotion/react';
import { colorSet } from '../styles/ThemeProvider';

type ThemeConfig = typeof colorSet.dark;
declare module '@emotion/react' {
  export interface Theme extends ThemeConfig {}
}
