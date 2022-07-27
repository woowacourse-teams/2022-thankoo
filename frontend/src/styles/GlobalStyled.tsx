import { css } from '@emotion/react';

const global = css`
  * {
    font-family: 'Noto Sans KR', sans-serif !important;
  }
  @font-face {
    font-family: 'Noto Sans KR', sans-serif;
    src: url('/assets/fonts/NotoSansKR-Regular.otf') format('truetype');
  }

  button {
    cursor: pointer;
  }
`;

export default global;
