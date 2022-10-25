import { Suspense } from 'react';
import CustomErrorBoundary from '../../errors/CustomErrorBoundary';
import ErrorFallBack from '../../errors/ErrorFallBack';
import MainPageLayout from '../../layout/MainPageLayout';
import Spinner from '../../components/@shared/Spinner';
import ListViewMeetings from './components/ListViewMeetings';

const Meetings = () => {
  return (
    <MainPageLayout>
      <CustomErrorBoundary fallbackComponent={ErrorFallBack}>
        <Suspense fallback={<Spinner />}>
          <ListViewMeetings />
        </Suspense>
      </CustomErrorBoundary>
    </MainPageLayout>
  );
};

export default Meetings;
