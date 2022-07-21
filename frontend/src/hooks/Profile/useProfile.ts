import axios from 'axios';
import { useMutation, useQuery, useQueryClient } from 'react-query';
import { API_PATH } from '../../constants/api';

type Profile = {
  id: number;
  name: string;
  email: string;
  imageUrl: string;
};

const useProfile = () => {
  const accessToken = localStorage.getItem('token');
  const queryClient = useQueryClient();

  const { data: profile } = useQuery<Profile>(
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
