import { useEffect } from 'react';
import { Navigate, Outlet } from 'react-router-dom';
import { interceptRequest } from '../apis/axios';
import Spinner from '../components/@shared/Spinner';
import { ROUTE_PATH } from '../constants/routes';
import { useGetOrganizations } from '../hooks/@queries/organization';

export const AccessController = () => {
  const { data: organizations, isLoading } = useGetOrganizations();

  const organization = organizations?.find(organization => organization.lastAccessed);

  if (typeof organizations === 'undefined' || isLoading) {
    return <Spinner />;
  }

  return organizations?.length ? (
    <OrganizationInjector organization={organization} />
  ) : (
    <Navigate to={ROUTE_PATH.JOIN_ORGANIZATION} replace />
  );
};

const OrganizationInjector = ({ organization }) => {
  interceptRequest(String(organization.organizationId));

  return <Outlet />;
};
