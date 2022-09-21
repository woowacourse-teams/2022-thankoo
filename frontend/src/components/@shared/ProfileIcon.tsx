import styled from '@emotion/styled';
import PersonIcon from '@mui/icons-material/Person';

import { FlexCenter } from '../../styles/mixIn';
import { BASE_URL } from './../../constants/api';

const ProfileIcon = ({ src, size }: { src: string; size: any }) => {
  const ImageUrl = `${BASE_URL}${src}`;

  if (src.includes('corgi'))
    return (
      <StyledIconBackGround color={'#c2e27e'} size={size}>
        <StyledProfileIcon src={ImageUrl} size={size} />
      </StyledIconBackGround>
    );
  if (src.includes('tiger'))
    return (
      <StyledIconBackGround color={'#e46868'} size={size}>
        <StyledProfileIcon src={ImageUrl} size={size} />
      </StyledIconBackGround>
    );
  if (src.includes('dino'))
    return (
      <StyledIconBackGround color={'#42ad3f'} size={size}>
        <StyledProfileIcon src={ImageUrl} size={size} />
      </StyledIconBackGround>
    );
  if (src.includes('mint'))
    return (
      <StyledIconBackGround color={'#48b2af'} size={size}>
        <StyledProfileIcon src={ImageUrl} size={size} />
      </StyledIconBackGround>
    );
  if (src.includes('otter'))
    return (
      <StyledIconBackGround color={'#ffe3bc'} size={size}>
        <StyledProfileIcon src={ImageUrl} size={size} />
      </StyledIconBackGround>
    );
  if (src.includes('panda'))
    return (
      <StyledIconBackGround color={'#4f4f4f'} size={size}>
        <StyledProfileIcon src={ImageUrl} size={size} />
      </StyledIconBackGround>
    );
  if (src.includes('skull'))
    return (
      <StyledIconBackGround color={'#903fad'} size={size}>
        <StyledProfileIcon src={ImageUrl} size={size} />
      </StyledIconBackGround>
    );
  if (src.includes('pig'))
    return (
      <StyledIconBackGround color={'#ffb9f4'} size={size}>
        <StyledProfileIcon src={ImageUrl} size={size} />
      </StyledIconBackGround>
    );
  return (
    <StyledIconBackGround color={'lightgray'} size={size}>
      <DefaultUserIcon size={size} />
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
  ${FlexCenter}
  width: ${({ size }) => Number(size.split(/[^0-9]/g)[0]) * 1.3 + 'px'};
  height: ${({ size }) => Number(size.split(/[^0-9]/g)[0]) * 1.3 + 'px'};
  background-color: ${({ color }) => color};
  border-radius: 50%;
  padding: 0.2rem;
  object-fit: cover;

  transition: all ease-in 0.2s;

  /* &:active,
  &:hover {
    background-color: ${({ theme }) => theme.button.active.background};
  } */
`;

const DefaultUserIcon = styled(PersonIcon)<IconProp>`
  font-size: ${({ size }) => (size ? size : '1.5em')};
  fill: gray;
`;

export default ProfileIcon;
