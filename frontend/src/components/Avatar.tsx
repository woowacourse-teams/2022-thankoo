import { css } from '@emotion/react';
import styled from '@emotion/styled';

type AvatarSize = 30 | 50 | 100;

type AvatarProps = {
  src: string | undefined;
  userName: string | undefined;
};
type AvatarStyleProps = {
  size?: AvatarSize;
};

const Avatar = ({ src, size = 50, userName = '' }: AvatarProps & AvatarStyleProps) => {
  return (
    <>
      {src ? (
        <Container src={src} size={size} alt={`${userName} 프로필`} />
      ) : (
        <AvatarPlaceholder aria-label={`${userName} 프로필`} />
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
