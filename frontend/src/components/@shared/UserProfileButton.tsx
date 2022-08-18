import { Link } from 'react-router-dom';
import { ROUTE_PATH } from '../../constants/routes';
import { useGetUserProfile } from '../../hooks/@queries/profile';
import ProfileIcon from './ProfileIcon';

const UserProfileButton = () => {
  const { data: profile } = useGetUserProfile();

  //Todo Const 제거 후 query 값으로 변경
  return (
    <Link to={ROUTE_PATH.PROFILE}>
      <ProfileIcon iconName={'Corgi'} size={'28px'} />
    </Link>
  );
};

export default UserProfileButton;
