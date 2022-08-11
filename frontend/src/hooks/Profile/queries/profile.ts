import { useQuery } from 'react-query';
import { client } from '../../../apis/axios';
import { API_PATH } from '../../../constants/api';
import { UserProfile } from '../../../types';

export const useGetProfile = (
  { onSuccess: handleSuccess } = { onSuccess: (data: UserProfile) => {} }
) =>
  useQuery<UserProfile>(
    'profile',
    async () => {
      const { data } = await client({
        method: 'get',
        url: `${API_PATH.PROFILE}`,
      });

      return data;
    },
    {
      refetchOnWindowFocus: false,
      onSuccess: data => {
        handleSuccess?.(data);
      },
    }
  );
