import { css } from '@emotion/react';

const global = css`
  * {
    font-family: 'Noto Sans KR', sans-serif !important;
    -webkit-tap-highlight-color: transparent;
  }
  html {
    //Todo 반응형으로 fontSize로 조정하기 default = 3
    //1. Se 기준
    font-size: 10px;
  }

  button {
    cursor: pointer;
  }
`;

export default global;
