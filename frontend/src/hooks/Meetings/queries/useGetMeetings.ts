import { useQuery } from 'react-query';
import { client } from '../../../apis/axios';
import { API_PATH } from '../../../constants/api';
import { Meeting } from '../../../types';

export const useGetMeetings = () =>
  useQuery<Meeting[]>('meetings', async () => {
    const { data } = await client({ method: 'get', url: API_PATH.MEETINGS });

    return data;
  });
