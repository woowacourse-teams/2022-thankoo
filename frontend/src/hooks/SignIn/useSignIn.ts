import axios from 'axios';
import { useQuery } from 'react-query';
import { useLocation } from 'react-router-dom';
import { API_PATH } from '../../constants/api';

const useSignIn = () => {
  const location = useLocation();
  const userCode = location.search.substring(6).split('&')[0];

  const { data, refetch: refetchToken } = useQuery(
    'token',
    async () => {
      const res = await axios({
        method: 'GET',
        url: `${API_PATH.SIGN_IN(userCode)}`,
      });
      return res.data;
    },
    {
      retry: false,
      refetchOnWindowFocus: false,
      enabled: false, // disable this query from automatically running
      onError: error => {
        alert('로그인에 실패하였습니다');
      },
    }
  );

  const saveAuth = (accessToken: string) => {
    localStorage.setItem('token', accessToken);
  };

  return { refetchToken, userCode, data, saveAuth };
};

export default useSignIn;
