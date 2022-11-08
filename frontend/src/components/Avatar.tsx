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
        <Image src={`${BASE_URL}${src}`} size={size} alt={userName} />
      ) : (
        <AvatarPlaceholder aria-label={userName} />
      )}
    </>
  );
};

export default Avatar;

const Image = styled.img<AvatarStyleProps>`
  ${({ size = 30 }) => css`
    width: ${size}px;
    height: ${size}px;
  `}
`;

const AvatarPlaceholder = styled.div<AvatarStyleProps>`
  ${({ size = 30 }) => css`
    width: ${size}px;
    height: ${size}px;
    background-color: #8e8e8e;
  `}
`;
