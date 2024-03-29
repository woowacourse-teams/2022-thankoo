import { css } from '@emotion/react';
import styled from '@emotion/styled';
import Button, { ButtonProps, ButtonStyleOptions } from './Button/Button';

interface LongButtonStyleProps extends ButtonProps {}

const LongButton = styled(Button)<LongButtonStyleProps>`
  ${({ size = 'medium' }) => css`
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: ${ButtonStyleOptions.size[size]} 2rem;
    border-radius: 30px;
  `}
`;

export default LongButton;
