import styled from '@emotion/styled';
import PersonIcon from '@mui/icons-material/Person';
import Corgi from '../../assets/images/user_profile/corgi.svg';
import Dino from '../../assets/images/user_profile/dino.svg';
import Mint from '../../assets/images/user_profile/mint.svg';
import Otter from '../../assets/images/user_profile/otter.svg';
import Panda from '../../assets/images/user_profile/panda.svg';
import Pig from '../../assets/images/user_profile/pig.svg';
import Skull from '../../assets/images/user_profile/skull.svg';
import Tiger from '../../assets/images/user_profile/tiger.svg';

import { flexCenter } from '../../styles/mixIn';
//Todo: sort 대신 다른이름으로 변경
const ProfileIcon = ({ iconName, size }) => {
  switch (iconName) {
    case 'Corgi':
      return (
        <StyledIconBackGround color={'#c2e27e'} size={size}>
          <StyledProfileIcon src={Corgi} size={size} />
        </StyledIconBackGround>
      );
    case 'Tiger':
      return (
        <StyledIconBackGround color={'#e46868'} size={size}>
          <StyledProfileIcon src={Tiger} size={size} />
        </StyledIconBackGround>
      );
    case 'Dino':
      return (
        <StyledIconBackGround color={'#42ad3f'} size={size}>
          <StyledProfileIcon src={Dino} size={size} />
        </StyledIconBackGround>
      );
    case 'Mint':
      return (
        <StyledIconBackGround color={'#48b2af'} size={size}>
          <StyledProfileIcon src={Mint} size={size} />
        </StyledIconBackGround>
      );
    case 'Otter':
      return (
        <StyledIconBackGround color={'#ffe3bc'} size={size}>
          <StyledProfileIcon src={Otter} size={size} />
        </StyledIconBackGround>
      );
    case 'Panda':
      return (
        <StyledIconBackGround color={'#4f4f4f'} size={size}>
          <StyledProfileIcon src={Panda} size={size} />
        </StyledIconBackGround>
      );
    case 'Skull':
      return (
        <StyledIconBackGround color={'#903fad'} size={size}>
          <StyledProfileIcon src={Skull} size={size} />
        </StyledIconBackGround>
      );
    case 'Pig':
      return (
        <StyledIconBackGround color={'#ffb9f4'} size={size}>
          <StyledProfileIcon src={Pig} size={size} />
        </StyledIconBackGround>
      );
    default:
      return (
        <StyledIconBackGround color={'#42ad3f'} size={size}>
          <PersonIcon />
        </StyledIconBackGround>
      );
  }
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
