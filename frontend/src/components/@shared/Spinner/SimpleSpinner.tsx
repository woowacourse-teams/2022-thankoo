import { css, keyframes } from '@emotion/react';
import styled from '@emotion/styled';

type SpinnerStyleProps = {
  width?: string;
  height?: string;
};

const load8 = keyframes`
    0% {
        transform: rotate(0deg);
    }
    100% {
        transform: rotate(360deg);
    }
`;

const SimpleSpinner = styled.div<SpinnerStyleProps>`
  ${({ width = '1.2rem', height = '1.2rem' }) => css`
    width: ${width};
    height: ${height};
  `}
  border: 2px solid #fff;
  border-bottom-color: transparent;
  border-radius: 50%;
  display: inline-block;
  box-sizing: border-box;
  animation: ${load8} 1s linear infinite;

  :after {
    border-radius: 50%;
    width: 19em;
    height: 10em;
  }
`;

export default SimpleSpinner;
