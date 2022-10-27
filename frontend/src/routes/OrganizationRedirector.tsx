import { Navigate, useParams } from 'react-router-dom';
import { ROUTE_PATH } from '../constants/routes';
import {
  useGetOrganizations,
  usePutLastAccessedOrganization,
} from '../hooks/@queries/organization';

const OrganizationRedirector = () => {
  const { id, page } = useParams();

  const { data: joinedOrganizations } = useGetOrganizations();
  const isUserJoinedOrganization = joinedOrganizations?.some(
    organization => String(organization.organizationId) === id
  );

  if (!isUserJoinedOrganization) {
    return <Navigate to={ROUTE_PATH.EXACT_MAIN} replace />;
  }

  const { mutate: updateLastAccessed } = usePutLastAccessedOrganization({
    onSuccess: () => {
      window.location.reload();
    },
  });

  updateLastAccessed(String(id));

  return <Navigate to={page ? `/${page}` : ROUTE_PATH.EXACT_MAIN} replace />;
};

export default OrganizationRedirector;
