import { css } from '@emotion/react';

const flexCenter = `
  display: flex;
  justify-content: center;
  align-items: center;
`;

const RouteButton = `
${({ active }) =>
  active &&
  css`
    background-color: tomato;
  `}
`;

export { flexCenter, RouteButton };
