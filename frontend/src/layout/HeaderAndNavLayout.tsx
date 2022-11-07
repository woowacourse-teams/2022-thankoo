import { Suspense } from 'react';
import { Outlet } from 'react-router-dom';
import Spinner from '../components/@shared/Spinner';
import BottomNavBar from './BottomNavBar';
import PageLayout from './PageLayout';
import TopNavBar from './TopNavBar';

export default () => {
  return (
    <Suspense fallback={<Spinner />}>
      <PageLayout>
        <TopNavBar />
        <Outlet />
        <BottomNavBar />
      </PageLayout>
    </Suspense>
  );
};
