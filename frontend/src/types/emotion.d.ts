import '@emotion/styled';
import '@emotion/react';
import { COLOR_SET } from 'styles/ThemeProvider';

const theme = {
  header: {
    color: white,
  },
};

type CustomTheme = typeof theme;

declare module '@emotion/styled' {
  export interface Theme extends CustomTheme {}
}

declare module '@emotion/react' {
  export interface Theme {
    header: {
      color: string;
    };
  }
}

declare module 'styled' {
  export interface Theme extends CustomTheme {}
}
