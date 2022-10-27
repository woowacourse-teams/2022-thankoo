import { Navigate, Outlet } from 'react-router-dom';
import { injectOrganizationToRequest } from '../apis/axios';
import { ROUTE_PATH } from '../constants/routes';
import { useGetLastAccessedOrganization } from '../hooks/@queries/organization';

const OrganizationInjector = () => {
  const lastAccessedOrganization = useGetLastAccessedOrganization();

  if (!lastAccessedOrganization) {
    return <Navigate to={ROUTE_PATH.JOIN_ORGANIZATION} />;
  }

  injectOrganizationToRequest(String(lastAccessedOrganization.organizationId));

  return <Outlet />;
};

export default OrganizationInjector;
