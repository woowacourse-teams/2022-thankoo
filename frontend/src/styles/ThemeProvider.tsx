import { ThemeProvider as BaseThemeProvider } from '@emotion/react';
import { useRecoilState } from 'recoil';
import { themeAtom } from '../recoil/atom';

const PALETTE = {
  //SUNSET ORANGE
  SUNSET_ORANGE_50: '#fff3f1',
  SUNSET_ORANGE_100: '#ffe4e1',
  SUNSET_ORANGE_200: '#ffcdc7',
  SUNSET_ORANGE_300: '#ffaba0',
  SUNSET_ORANGE_400: '#ff6450',
  SUNSET_ORANGE_500: '#f8513b',
  SUNSET_ORANGE_600: '#e5341d',
  SUNSET_ORANGE_700: '#c12814',
  SUNSET_ORANGE_800: '#a02414',
  SUNSET_ORANGE_900: '#842418',

  //GRAY
  GRAY_50: '#f7f7f7',
  GRAY_100: '#e3e3e3',
  GRAY_200: '#c8c8c8',
  GRAY_300: '#a4a4a4',
  GRAY_400: '#808080',
  GRAY_500: '#666666',
  GRAY_600: '#515151',
  GRAY_700: '#434343',
  GRAY_800: '#383838',
  GRAY_900: '#313131',

  WHITE: '#FFFFFF',
  BLACK: '#000000',
};

export const COLOR_SET = {
  light: {},
  dark: {
    header: {
      color: PALETTE.GRAY_50,
    },
    button: {
      background: '',
      color: '',
      opacity: '',
    },
  },
};

// const theme = mode === 'dark' ? COLOR_SET.dark : COLOR_SET.light;
export type Theme = typeof COLOR_SET.dark;

export const ThemeProvider = ({ children }) => {
  const [mode] = useRecoilState(themeAtom);
  const theme = mode === 'dark' ? COLOR_SET.dark : COLOR_SET.light;

  return <BaseThemeProvider theme={theme}>{children}</BaseThemeProvider>;
};
