import { css } from '@emotion/react';
import styled from '@emotion/styled';
import { BASE_URL } from '../constants/api';

type AvatarSize = 30 | 50 | 100;

type AvatarProps = {
  src: string | undefined;
  userName: string | undefined;
};
type AvatarStyleProps = {
  size?: AvatarSize;
};

const Avatar = ({ src, size = 30, userName = '' }: AvatarProps & AvatarStyleProps) => {
  return (
    <>
      {src ? (
        <Container src={`${BASE_URL}${src}`} size={size} alt={userName} />
      ) : (
        <AvatarPlaceholder aria-label={userName} />
      )}
    </>
  );
};

export default Avatar;

const Container = styled.img<AvatarStyleProps>`
  ${({ size = 'sm' }) => css`
    width: ${size}px;
    height: ${size}px;
    border-radius: 50%;
  `}
`;

const AvatarPlaceholder = styled.div<AvatarStyleProps>`
  ${({ size = 'sm' }) => css`
    width: ${size}px;
    height: ${size}px;
    border-radius: 50%;
    background-color: #8e8e8e;
  `}
`;
