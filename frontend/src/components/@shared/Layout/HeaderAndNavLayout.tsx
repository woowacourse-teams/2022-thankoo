import styled from '@emotion/styled';
import { Suspense } from 'react';
import { Outlet } from 'react-router-dom';
import BottomNavBar from '../../PageButton/BottomNavBar';
import Spinner from '../Spinner';
import UserProfileButton from '../UserProfileButton';
import PageLayout from './PageLayout';

export default () => {
  return (
    <PageLayout>
      <UserProfileWrapper>
        <UserProfileButton />
      </UserProfileWrapper>
      <Suspense fallback={<Spinner />}>
        <Outlet />
      </Suspense>
      <BottomNavBar />
    </PageLayout>
  );
};

const UserProfileWrapper = styled.div`
  display: flex;
  justify-content: flex-end;
  margin: 1rem;
`;
