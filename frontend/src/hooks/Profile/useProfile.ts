import { useMutation, useQuery, useQueryClient } from 'react-query';
import { client } from '../../apis/axios';
import { API_PATH } from '../../constants/api';
import { UserProfile } from '../../types';

const useProfile = () => {
  const queryClient = useQueryClient();

  const { data: profile } = useQuery<UserProfile>(
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
    }
  );

  const editUserName = useMutation(
    (name: string) =>
      client({
        method: 'put',
        url: `${API_PATH.PROFILE}`,
        data: {
          name,
        },
      }),
    {
      onSuccess: () => {
        queryClient.invalidateQueries('profile');
      },
    }
  );

  return { profile, editUserName };
};

export default useProfile;
