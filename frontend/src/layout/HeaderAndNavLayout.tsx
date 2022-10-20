import { Suspense } from 'react';
import { Outlet } from 'react-router-dom';
import Spinner from '../components/@shared/Spinner';
import BottomNavBar from './BottomNavBar';
import PageLayout from './PageLayout';
import TopNavBar from './TopNavBar';

export default () => {
  return (
    <PageLayout>
      <TopNavBar />
      <Suspense fallback={<Spinner />}>
        <Outlet />
      </Suspense>
      <BottomNavBar />
    </PageLayout>
  );
};
