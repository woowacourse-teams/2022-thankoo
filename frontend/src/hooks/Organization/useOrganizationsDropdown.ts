import { useQueryClient } from 'react-query';
import { useGetOrganizations, usePutLastAccessedOrganization } from '../@queries/organization';
import useToast from '../useToast';

export const useOrganizationsDropdown = () => {
  const { insertToastItem } = useToast();
  const queryClient = useQueryClient();

  const { data: organizations } = useGetOrganizations();
  const lastAccessedOrganization = organizations?.find(
    organization => organization.lastAccessed
  )?.organizationName;

  const putLastAccessedOrganizationMutation = usePutLastAccessedOrganization({
    onSuccess: () => {
      queryClient.resetQueries();
      window.location.reload();
    },
    onError: error => {
      insertToastItem(error.response?.data.message);
    },
  });
  const updateLastAccessedOrganization = (id: string) => {
    putLastAccessedOrganizationMutation.mutate(id);
  };

  return {
    updateLastAccessedOrganization,
    lastAccessedOrganization,
    organizations,
  };
};
