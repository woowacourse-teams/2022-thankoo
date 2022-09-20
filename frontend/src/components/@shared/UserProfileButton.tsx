import { Link } from 'react-router-dom';
import { ROUTE_PATH } from '../../constants/routes';
import { useGetUserProfile } from '../../hooks/@queries/profile';
import ProfileIcon from './ProfileIcon';

const UserProfileButton = () => {
  const { data: profile } = useGetUserProfile();

  return (
    <Link to={ROUTE_PATH.PROFILE}>
      <ProfileIcon src={profile ? profile?.imageUrl : 'default'} size={'28px'} />
    </Link>
  );
};

export default UserProfileButton;
