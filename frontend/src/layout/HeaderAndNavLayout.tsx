import styled from '@emotion/styled';
import { Outlet } from 'react-router-dom';
import BottomNavBar from '../components/PageButton/BottomNavBar';
import UserProfileButton from '../components/@shared/UserProfileButton';
import PageLayout from './PageLayout';

export default () => {
  return (
    <PageLayout>
      <UserProfileWrapper>
        <UserProfileButton />
      </UserProfileWrapper>
      <Outlet />
      <BottomNavBar />
    </PageLayout>
  );
};

const UserProfileWrapper = styled.div`
  display: flex;
  justify-content: flex-end;
  margin: 1rem;
`;
