import styled from '@emotion/styled';
import { Link } from 'react-router-dom';
import Avatar from '../components/Avatar';
import { ROUTE_PATH } from '../constants/routes';
import { useGetUserProfile } from '../hooks/@queries/profile';
import OrganizationsDropdown from '../pages/Organization/components/OrganizationsDropdown';

const TopNavBar = () => {
  const { data: profile } = useGetUserProfile();

  return (
    <Container>
      <OrganizationsDropdown />
      <Link to={ROUTE_PATH.PROFILE}>
        <div
          css={{
            display: 'grid',
            placeItems: 'center',
            width: '45px',
            height: '45px',
            backgroundColor: '#4a4a4a',
            borderRadius: '50%',
          }}
        >
          <Avatar src={profile?.imageUrl} alt={`${profile?.name} 프로필`} />
        </div>
      </Link>
    </Container>
  );
};
export default TopNavBar;

const Container = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin: 1rem;
`;
