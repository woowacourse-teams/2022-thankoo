import { useEffect, useState } from 'react';
import { useMutation, useQueryClient } from 'react-query';
import { useNavigate } from 'react-router-dom';
import { requestInstance } from '../../apis/axios';
import { API_PATH } from '../../constants/api';
import { ROUTE_PATH } from '../../constants/routes';
import { saveAuth } from '../../utils/auth';

const useEnterNickname = () => {
  const queryClient = useQueryClient();
  const navigate = useNavigate();
  const [nickname, setNickname] = useState('');
  const data = queryClient.getQueryData('token') as any;

  useEffect(() => {
    if (!data) {
      navigate(ROUTE_PATH.SIGN_IN);
    }
  }, []);

  const signUp = useMutation(
    async () =>
      await requestInstance({
        method: 'post',
        url: API_PATH.SIGN_UP,
        data: { name: nickname },
        headers: { Authorization: data?.accessToken },
      }),
    {
      onSuccess: (res: any) => {
        saveAuth(res.data.accessToken);
        navigate(ROUTE_PATH.EXACT_MAIN);
      },
    }
  );

  const signUpWithNickname = async e => {
    e.preventDefault();
    signUp.mutate();
  };

  return { signUpWithNickname, setNickname, nickname, email: data?.email };
};

export default useEnterNickname;
