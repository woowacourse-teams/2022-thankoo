import { Navigate, Outlet, useLocation, useNavigate } from 'react-router-dom';
import { interceptRequest } from '../apis/axios';
import Spinner from '../components/@shared/Spinner';
import { ROUTE_PATH } from '../constants/routes';
import {
  useGetOrganizations,
  usePutLastAccessedOrganization,
} from '../hooks/@queries/organization';
import useToast from '../hooks/useToast';

const AccessController = () => {
  const { insertToastItem } = useToast();
  const navigate = useNavigate();
  const { pathname } = useLocation();
  const [_, domain, organizationsId, page] = pathname.split('/');

  const { data: organizations, isLoading } = useGetOrganizations();
  const { mutate: updateLastAccessed } = usePutLastAccessedOrganization({
    onSuccess: () => {
      navigate(page, { replace: true });
      window.location.reload();
    },
    onError: error => {
      insertToastItem(error.response?.data.message);
      navigate(ROUTE_PATH.EXACT_MAIN, { replace: true });
      window.location.reload();
    },
  });

  const isAccessedByOrganizationId = domain === 'organizations' && !!organizationsId;
  if (isAccessedByOrganizationId) {
    updateLastAccessed(organizationsId);
  }

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
export default AccessController;

const OrganizationInjector = ({ organization }) => {
  interceptRequest(String(organization.organizationId));

  return <Outlet />;
};
