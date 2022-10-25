import { css } from '@emotion/react';
import styled from '@emotion/styled';
import React from 'react';
import SimpleSpinner from '../Spinner/SimpleSpinner';

export type ButtonSize = 'medium' | 'small' | 'large';
export type ButtonColor = 'primary' | 'secondary' | 'primaryLight' | 'secondaryLight';
export interface ButtonProps extends React.ComponentPropsWithoutRef<'button'> {
  size?: ButtonSize;
  color?: ButtonColor;
  isDisabled?: boolean;
  isLoading?: boolean;
}

const Button = ({
  children,
  size = 'medium',
  color = 'primary',
  isDisabled = false,
  isLoading = false,
  ...rest
}: ButtonProps) => {
  return (
    <Container
      size={size}
      color={color}
      isDisabled={isDisabled}
      aria-disabled={isDisabled}
      isLoading={isLoading}
      {...rest}
    >
      {isLoading && !isDisabled ? (
        <SimpleSpinner
          width={ButtonStyleOptions.fontSize[size]}
          height={ButtonStyleOptions.fontSize[size]}
        />
      ) : (
        children
      )}
    </Container>
  );
};

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
    primaryBright: '#ff7e67',
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

const Container = styled.button<ButtonProps>`
  ${({ size = 'medium', color = 'primary', isDisabled = false, isLoading = false }) => css`
    width: 100%;
    font-size: ${ButtonStyleOptions.fontSize[size]};
    padding: ${ButtonStyleOptions.size[size]} 1rem;
    background-color: ${ButtonStyleOptions.bg[color]};
    color: ${ButtonStyleOptions.fontColor[color]};
    border: none;
    border-radius: 4px;
    cursor: pointer;
    ${isDisabled &&
    css`
      background-color: ${ButtonStyleOptions.bg['secondary']};
      color: ${ButtonStyleOptions.fontColor['secondary']};
      cursor: not-allowed;
      pointer-events: none;
    `}
    ${isLoading &&
    !isDisabled &&
    css`
      background-color: ${ButtonStyleOptions.bg['primaryBright']};
      cursor: default;
      pointer-events: none;
    `}
  `}
`;

export default Button;
