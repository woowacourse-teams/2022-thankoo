import { css } from '@emotion/react';

const global = css`
  * {
    -webkit-tap-highlight-color: transparent;
    @font-face {
      font-family: 'EarlyFontDiary';
      src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_220508@1.0/EarlyFontDiary.woff2')
        format('woff2');
      font-weight: normal;
      font-style: normal;
    }
    @font-face {
      font-family: 'Pretendard-Regular';
      src: url('https://cdn.jsdelivr.net/gh/Project-Noonnu/noonfonts_2107@1.1/Pretendard-Regular.woff')
        format('woff');
      font-weight: 400;
      font-style: normal;
    }
    font-family: 'Pretendard-Regular' !important;
  }
  html {
    //Todo 반응형으로 fontSize로 조정하기 default = 3
    font-size: 10px;
  }

  button {
    cursor: pointer;
  }
`;

export default global;
