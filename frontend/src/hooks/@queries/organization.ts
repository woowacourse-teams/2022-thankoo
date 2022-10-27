import { AxiosError } from 'axios';
import { useMutation, useQuery } from 'react-query';
import { client } from '../../apis/axios';
import { API_PATH } from '../../constants/api';
import { ErrorType, QueryHandlers } from '../../types/api';

export const ORGANIZATION_QUERY_KEY = {
  organizations: 'organizations',
};

type OrganizationResponse = {
  organizationId: number;
  organizationName: string;
  organizationCode: string;
  orderNumber: number;
  lastAccessed: boolean;
};

export const useGetOrganizations = () => {
  const { data: organizations, ...rest } = useQuery<OrganizationResponse[]>(
    [ORGANIZATION_QUERY_KEY.organizations],
    () => getOrganizations(),
    { staleTime: 0, cacheTime: 0 }
  );

  return { organizations: organizations || ([] as OrganizationResponse[]), ...rest };
};

export const useGetLastAccessedOrganizations = () => {
  const { organizations } = useGetOrganizations();
  const lastAccessedOrganization = organizations.find(organization => organization.lastAccessed);

  return lastAccessedOrganization || ({} as OrganizationResponse);
};

export const usePutJoinOrganization = ({ onSuccess, onError }: QueryHandlers) =>
  useMutation(postJoinNewOrganization, {
    onSuccess: () => {
      onSuccess?.();
    },
    onError: (error: AxiosError<ErrorType>) => {
      onError?.(error);
    },
    retry: false,
  });

export const usePutLastAccessedOrganization = ({ onSuccess, onError }: QueryHandlers) =>
  useMutation((id: string) => putUpdateLastAccessedOrganization(id), {
    onSuccess: () => {
      onSuccess?.();
    },
    onError: (error: AxiosError<ErrorType>) => {
      onError?.(error);
    },
    retry: false,
  });

//   FETCHER

const getOrganizations = async () => {
  const { data } = await client({ method: 'get', url: API_PATH.ORGANIZATIONS });

  return data;
};

const postJoinNewOrganization = (code: string) =>
  client({ method: 'post', url: API_PATH.JOIN_ORGANIZATION, data: { code } });

const putUpdateLastAccessedOrganization = (id: string) =>
  client({ method: 'put', url: API_PATH.UPDATE_LAST_ACCESSED_ORGANIZATION(id) });
