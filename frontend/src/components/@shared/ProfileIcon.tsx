import styled from '@emotion/styled';
import PersonIcon from '@mui/icons-material/Person';

import { flexCenter } from '../../styles/mixIn';
import { BASE_URL } from './../../constants/api';

const ProfileIcon = ({ imageUrl, size }: { imageUrl: string; size: any }) => {
  const src = `${BASE_URL}${imageUrl}`;

  if (imageUrl.includes('corgi'))
    return (
      <StyledIconBackGround color={'#c2e27e'} size={size}>
        <StyledProfileIcon src={src} size={size} />
      </StyledIconBackGround>
    );
  if (imageUrl.includes('tiger'))
    return (
      <StyledIconBackGround color={'#e46868'} size={size}>
        <StyledProfileIcon src={src} size={size} />
      </StyledIconBackGround>
    );
  if (imageUrl.includes('dino'))
    return (
      <StyledIconBackGround color={'#42ad3f'} size={size}>
        <StyledProfileIcon src={src} size={size} />
      </StyledIconBackGround>
    );
  if (imageUrl.includes('mint'))
    return (
      <StyledIconBackGround color={'#48b2af'} size={size}>
        <StyledProfileIcon src={src} size={size} />
      </StyledIconBackGround>
    );
  if (imageUrl.includes('otter'))
    return (
      <StyledIconBackGround color={'#ffe3bc'} size={size}>
        <StyledProfileIcon src={src} size={size} />
      </StyledIconBackGround>
    );
  if (imageUrl.includes('panda'))
    return (
      <StyledIconBackGround color={'#4f4f4f'} size={size}>
        <StyledProfileIcon src={src} size={size} />
      </StyledIconBackGround>
    );
  if (imageUrl.includes('skull'))
    return (
      <StyledIconBackGround color={'#903fad'} size={size}>
        <StyledProfileIcon src={src} size={size} />
      </StyledIconBackGround>
    );
  if (imageUrl.includes('pig'))
    return (
      <StyledIconBackGround color={'#ffb9f4'} size={size}>
        <StyledProfileIcon src={src} size={size} />
      </StyledIconBackGround>
    );
  return (
    <StyledIconBackGround color={'#transparent'} size={size}>
      <PersonIcon />
    </StyledIconBackGround>
  );
};

type IconProp = {
  size: string;
};

type BackgroundProp = {
  color: string;
  size: string;
};

const StyledProfileIcon = styled.img<IconProp>`
  width: ${({ size }) => size};
`;
const StyledIconBackGround = styled.div<BackgroundProp>`
  ${flexCenter}
  width: ${({ size }) => Number(size.split(/[^0-9]/g)[0]) * 1.3 + 'px'};
  height: ${({ size }) => Number(size.split(/[^0-9]/g)[0]) * 1.3 + 'px'};
  background-color: ${({ color }) => color};
  border-radius: 50%;
  padding: 0.2rem;
  object-fit: cover;

  transition: all ease-in;
  transition-duration: 0.2s;
  -webkit-transition-duration: 0.2s;

  /* &:active,
  &:hover {
    background-color: ${({ theme }) => theme.button.active.background};
  } */
`;

export default ProfileIcon;
