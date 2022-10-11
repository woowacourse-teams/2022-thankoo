import { useQuery } from 'react-query';
import { client } from '../../apis/axios';
import { API_PATH } from '../../constants/api';

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

const getOrganizations = async () => {
  const { data } = await client({ method: 'get', url: API_PATH.ORGANIZATIONS });

  return data;
};

export const useGetOrganizations = () =>
  useQuery<OrganizationResponse[]>([ORGANIZATION_QUERY_KEY.organizations], () =>
    getOrganizations()
  );
