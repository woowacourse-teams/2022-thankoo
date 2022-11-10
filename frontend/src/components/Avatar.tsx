import { css } from '@emotion/react';
import styled from '@emotion/styled';
import { BASE_URL } from '../constants/api';

type AvatarSize = 30 | 50 | 80 | 100;

type AvatarProps = {
  src?: string;
  alt: string;
};
type AvatarStyleProps = {
  size?: AvatarSize;
};

const Avatar = ({ src, size = 30, alt }: AvatarProps & AvatarStyleProps) => {
  return (
    <>
      {src ? (
        <Image src={`${BASE_URL}${src}`} size={size} alt={alt} />
      ) : (
        <AvatarSkeleton aria-label={alt} />
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

const AvatarSkeleton = styled.div<AvatarStyleProps>`
  ${({ size = 30 }) => css`
    width: ${size}px;
    height: ${size}px;
    background-color: #8e8e8e;
    border-radius: 50%;
  `}
`;
