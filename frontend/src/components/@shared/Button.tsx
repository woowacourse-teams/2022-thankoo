import { css } from '@emotion/react';
import styled from '@emotion/styled';

export type ButtonSize = 'medium' | 'small' | 'large';
export type ButtonColor = 'primary' | 'secondary' | 'primaryLight' | 'secondaryLight';
export interface ButtonProps {
  size?: ButtonSize;
  color?: ButtonColor;
  isDisabled?: boolean;
}

export const ButtonStyleOptions = {
  size: {
    medium: '1.3rem',
    small: '1rem',
    large: '1.7rem',
  },
  fontSize: {
    medium: '1.4rem',
    small: '1.2rem',
    large: '1.7rem',
  },
  bg: {
    primary: 'tomato',
    primaryLight: '#cdcac7',
    secondary: '#404040',
    secondaryLight: '#4a4a4a',
  },
  fontColor: {
    primary: 'white',
    secondary: '#b2b2b2',
    secondaryLight: 'white',
  },
};

const Button = styled.button<ButtonProps>`
  ${({ size = 'medium', color = 'primary', isDisabled = false }) => css`
    width: 100%;
    font-size: ${ButtonStyleOptions.fontSize[size]};
    padding: ${ButtonStyleOptions.size[size]} 1rem;
    background-color: ${ButtonStyleOptions.bg[color]};
    color: ${ButtonStyleOptions.fontColor[color]};
    border: none;
    border-radius: 4px;
    ${isDisabled &&
    css`
      background-color: ${ButtonStyleOptions.bg['secondary']};
      color: ${ButtonStyleOptions.fontColor['secondary']};
      cursor: not-allowed;
    `}
  `}
`;

export default Button;
