import { css, keyframes } from '@emotion/react';
import styled from '@emotion/styled';

interface SpinnerProps {
  width: string;
  height: string;
}

const load8 = keyframes`
    0% {
        transform: rotate(0deg);
    }
    100% {
        transform: rotate(360deg);
    }
`;

const ButtonSpinner = styled.div<SpinnerProps>`
  ${({ width, height }) => css`
    width: ${width};
    height: ${height};
  `}
  border: 3px solid #fff;
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

export default ButtonSpinner;
