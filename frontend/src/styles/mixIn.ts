import { css } from '@emotion/react';

const FlexCenter = css`
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

const FlexSpaceBetween = css`
  display: flex;
  justify-content: space-between;
`;

const FlexColumn = css`
  display: flex;
  flex-flow: column;
`;

const gap = gap => css`
  gap: ${gap};
`;

export { FlexCenter, RouteButton, FlexSpaceBetween, FlexColumn, gap };
