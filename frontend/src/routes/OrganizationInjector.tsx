import { Suspense } from 'react';
import { Navigate, Outlet } from 'react-router-dom';
import { injectOrganizationToRequest } from '../apis/axios';
import Spinner from '../components/@shared/FallbackSpinner';
import { ROUTE_PATH } from '../constants/routes';
import { useGetOrganizations } from '../hooks/@queries/organization';

const OrganizationInjector = () => {
  return (
    <Suspense fallback={<Spinner />}>
      {(() => {
        const { organizations } = useGetOrganizations();

        const organization = organizations.find(organization => organization.lastAccessed);

        if (organization) {
          injectOrganizationToRequest(String(organization.organizationId));

          return <Outlet />;
        }

        return <Navigate to={ROUTE_PATH.JOIN_ORGANIZATION} />;
      })()}
    </Suspense>
  );
};

export default OrganizationInjector;
