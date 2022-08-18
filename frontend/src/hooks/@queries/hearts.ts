import { useMutation, useQuery, useQueryClient } from 'react-query';
import { API_PATH } from '../../constants/api';
import { client } from './../../apis/axios';

const QUERY_KEY = {
  heart: 'heart',
};

type Sent = {
  heartId: number;
  senderId: number;
  receiverId: number;
  count: number;
  last: boolean;
  modifiedAt: string;
};

type Received = {
  heartId: number;
  senderId: number;
  receiverId: number;
  count: number;
  last: boolean;
  modifiedAt: string;
};

type HeartHistory = {
  sent: Sent[];
  received: Received[];
};

export const useGetHearts = () => useQuery<HeartHistory>(QUERY_KEY.heart, getUserHeartsRequest);

//FETCHER
const getUserHeartsRequest = async () => {
  const { data } = await client({
    method: 'GET',
    url: API_PATH.GET_USER_HEARTS,
  });
  return data;
};

type postHeartReqest = {
  receiverId: number;
};
export const usePostHeartMutation = () => {
  const queryClient = useQueryClient();

  return useMutation((receiverId: number) => postUserHeartRequest(receiverId), {
    onSuccess: () => {
      queryClient.invalidateQueries([QUERY_KEY.heart]);
    },
  });
};
const postUserHeartRequest = async receiverId =>
  client({
    method: 'post',
    url: API_PATH.POST_USER_HEART,
    data: { receiverId },
  });
