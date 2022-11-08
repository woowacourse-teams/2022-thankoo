import { Link } from 'react-router-dom';
import { ROUTE_PATH } from '../../constants/routes';
import { useGetUserProfile } from '../../hooks/@queries/profile';
import Avatar from '../Avatar';

const UserProfileButton = () => {
  const { data: profile } = useGetUserProfile();

  return (
    <Link to={ROUTE_PATH.PROFILE}>
      <Avatar src={profile?.imageUrl} userName={profile?.name} />
    </Link>
  );
};

export default UserProfileButton;
