import styled from '@emotion/styled';
import UserProfileButton from '../components/@shared/UserProfileButton';
import OrganizationsDropdown from '../pages/Organization/components/OrganizationsDropdown';

const TopNavBar = () => {
  return (
    <Container>
      <OrganizationsDropdown />
      <UserProfileButton />
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
