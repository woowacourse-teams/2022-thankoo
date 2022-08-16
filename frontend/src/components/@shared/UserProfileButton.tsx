import styled from '@emotion/styled';
import { Link } from 'react-router-dom';
import defaultUserImage from '../../assets/images/default_user.jpeg';
import { ROUTE_PATH } from '../../constants/routes';
import { useGetUserProfile } from '../../hooks/@queries/profile';

const UserProfileButton = () => {
  const { data: profile } = useGetUserProfile();

  return (
    <Link to={ROUTE_PATH.PROFILE}>
      <StyledUserProfileButton
        src={profile ? profile.imageUrl : defaultUserImage}
        alt='user-profile-button'
      />
    </Link>
  );
};

export default UserProfileButton;

const StyledUserProfileButton = styled.img`
  width: 36px;
  height: 36px;

  border-radius: 50%;
  padding: 0.2rem;
  object-fit: cover;

  transition: all ease-in;
  transition-duration: 0.2s;
  -webkit-transition-duration: 0.2s;

  &:active,
  &:hover {
    background-color: ${({ theme }) => theme.button.active.background};
  }
`;
