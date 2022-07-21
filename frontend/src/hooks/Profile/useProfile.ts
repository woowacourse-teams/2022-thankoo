import axios from 'axios';
import { useMutation, useQuery, useQueryClient } from 'react-query';
import { API_PATH } from '../../constants/api';
import { UserProfile } from '../../types';

const useProfile = () => {
  const accessToken = localStorage.getItem('token');
  const queryClient = useQueryClient();

  const { data: profile } = useQuery<UserProfile>(
    'profile',
    async () => {
      const { data } = await axios({
        method: 'get',
        url: `${API_PATH.PROFILE}`,
        headers: { Authorization: `Bearer ${accessToken}` },
      });

      return data;
    },
    { refetchOnWindowFocus: false }
  );

  const editUserName = useMutation(
    (name: string) =>
      axios({
        method: 'put',
        url: `${API_PATH.PROFILE}`,
        headers: { Authorization: `Bearer ${accessToken}` },
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
