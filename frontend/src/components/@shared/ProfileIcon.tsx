import styled from '@emotion/styled';
import PersonIcon from '@mui/icons-material/Person';

import { FlexCenter } from '../../styles/mixIn';
import { BASE_URL } from './../../constants/api';

type ProfileIconProps = {
  src: string;
  size: any;
  isSelected?: boolean;
};

const ProfileIcon = ({ src, size, isSelected }: ProfileIconProps) => {
  const ImageUrl = `${BASE_URL}${src}`;

  if (src.includes('corgi'))
    return (
      <StyledIconBackGround color={'#c2e27e'} size={size} isSelected={isSelected}>
        <StyledProfileIcon src={ImageUrl} size={size} />
      </StyledIconBackGround>
    );
  if (src.includes('tiger'))
    return (
      <StyledIconBackGround color={'#ebe26d'} size={size} isSelected={isSelected}>
        <StyledProfileIcon src={ImageUrl} size={size} />
      </StyledIconBackGround>
    );
  if (src.includes('dino'))
    return (
      <StyledIconBackGround color={'#42ad3f'} size={size} isSelected={isSelected}>
        <StyledProfileIcon src={ImageUrl} size={size} />
      </StyledIconBackGround>
    );
  if (src.includes('mint'))
    return (
      <StyledIconBackGround color={'#48b2af'} size={size} isSelected={isSelected}>
        <StyledProfileIcon src={ImageUrl} size={size} />
      </StyledIconBackGround>
    );
  if (src.includes('otter'))
    return (
      <StyledIconBackGround color={'#ffe3bc'} size={size} isSelected={isSelected}>
        <StyledProfileIcon src={ImageUrl} size={size} />
      </StyledIconBackGround>
    );
  if (src.includes('panda'))
    return (
      <StyledIconBackGround color={'#4f4f4f'} size={size} isSelected={isSelected}>
        <StyledProfileIcon src={ImageUrl} size={size} />
      </StyledIconBackGround>
    );
  if (src.includes('skull'))
    return (
      <StyledIconBackGround color={'#903fad'} size={size} isSelected={isSelected}>
        <StyledProfileIcon src={ImageUrl} size={size} />
      </StyledIconBackGround>
    );
  if (src.includes('pig'))
    return (
      <StyledIconBackGround color={'#ffb9f4'} size={size} isSelected={isSelected}>
        <StyledProfileIcon src={ImageUrl} size={size} />
      </StyledIconBackGround>
    );
  return (
    <StyledIconBackGround color={'lightgray'} size={size} isSelected={isSelected}>
      <DefaultUserIcon size={size} />
    </StyledIconBackGround>
  );
};

type IconStyleProp = {
  size: string;
};

type BackgroundStyleProp = {
  color: string;
  size: string;
  isSelected?: boolean;
};

const StyledProfileIcon = styled.img<IconStyleProp>`
  width: ${({ size }) => size};
`;
const StyledIconBackGround = styled.div<BackgroundStyleProp>`
  ${FlexCenter}
  width: ${({ size }) => Number(size.split(/[^0-9]/g)[0]) * 1.3 + 'px'};
  height: ${({ size }) => Number(size.split(/[^0-9]/g)[0]) * 1.3 + 'px'};
  background-color: ${({ color }) => color};
  border-radius: 50%;
  padding: 0.2rem;
  object-fit: cover;

  box-shadow: ${({ isSelected }) => (isSelected ? '0 0 0 7px inset tomato' : '')};
`;

const DefaultUserIcon = styled(PersonIcon)<IconStyleProp>`
  font-size: ${({ size }) => (size ? size : '1.5em')};
  fill: gray;
`;

export default ProfileIcon;
