import { Navigate, Outlet } from 'react-router-dom';
import { interceptRequest } from '../apis/axios';
import Spinner from '../components/@shared/Spinner';
import { ROUTE_PATH } from '../constants/routes';
import { useGetOrganizations } from '../hooks/@queries/organization';

export const AccessController = () => {
  const { data: organizations } = useGetOrganizations();

  const organization = organizations?.find(organization => organization.lastAccessed);

  if (typeof organizations === 'undefined') {
    return <Spinner />;
  }

  interceptRequest(String(organization?.organizationId));

  return organizations?.length ? (
    <Outlet />
  ) : (
    <Navigate to={ROUTE_PATH.JOIN_ORGANIZATION} replace />
  );
};
