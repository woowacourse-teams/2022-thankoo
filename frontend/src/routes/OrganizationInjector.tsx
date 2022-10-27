import { Suspense } from 'react';
import { Navigate, Outlet } from 'react-router-dom';
import { injectOrganizationToRequest } from '../apis/axios';
import Spinner from '../components/@shared/Spinner';
import { ROUTE_PATH } from '../constants/routes';
import { useGetLastAccessedOrganizations } from '../hooks/@queries/organization';

const OrganizationInjector = () => {
  return (
    <Suspense fallback={<Spinner />}>
      {(() => {
        const lastAccessedOrganization = useGetLastAccessedOrganizations();

        if (lastAccessedOrganization) {
          injectOrganizationToRequest(String(lastAccessedOrganization.organizationId));

          return <Outlet />;
        }

        return <Navigate to={ROUTE_PATH.JOIN_ORGANIZATION} />;
      })()}
    </Suspense>
  );
};

export default OrganizationInjector;
