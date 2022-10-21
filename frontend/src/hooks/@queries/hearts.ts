import { Axios, AxiosError } from 'axios';
import { useMutation, useQuery, useQueryClient } from 'react-query';
import { API_PATH } from '../../constants/api';
import { ErrorType } from '../../types/api';
import { urlQueryHandler } from '../../utils/api';
import { client } from './../../apis/axios';

export const HEART_QUERY_KEY = {
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

export const useGetHearts = () =>
  useQuery<HeartHistory>(HEART_QUERY_KEY.heart, getUserHeartsRequest);

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
export const usePostHeartMutation = ({
  onSuccess,
  onError,
}: {
  onSuccess: () => void;
  onError: (error: AxiosError<ErrorType>) => void;
}) => {
  const queryClient = useQueryClient();

  return useMutation((receiverId: number) => postUserHeartRequest(receiverId), {
    onSuccess: () => {
      queryClient.invalidateQueries([HEART_QUERY_KEY.heart]);
      onSuccess?.();
    },
    onError: (error: AxiosError<ErrorType>) => {
      onError?.(error);
    },
  });
};

export const useGetHeart = (receiverId, { onSuccess }) =>
  useQuery(
    ['heart', receiverId],
    async () => {
      const { data } = await client({
        method: 'get',
        url: urlQueryHandler(API_PATH.GET_USER_HEART, `receiver=${receiverId}`),
      });

      return data;
    },
    {
      onSuccess: res => {
        onSuccess?.(res);
      },
      enabled: false,
    }
  );

const postUserHeartRequest = async receiverId =>
  client({
    method: 'post',
    url: API_PATH.POST_USER_HEART,
    data: { receiverId },
  });
