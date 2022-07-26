import { useQuery } from 'react-query';
import { useLocation } from 'react-router-dom';
import { requestInstance } from '../../api';
import { API_PATH } from '../../constants/api';

const useSignIn = () => {
  const location = useLocation();
  const userCode = location.search.substring(6).split('&')[0];

  const { data, refetch: refetchToken } = useQuery(
    'token',
    async () => {
      const res = await requestInstance({
        method: 'GET',
        url: `${API_PATH.SIGN_IN(userCode)}`,
      });
      return res.data;
    },
    {
      retry: false,
      refetchOnWindowFocus: false,
      enabled: false,
      onError: error => {
        alert('로그인에 실패하였습니다');
      },
    }
  );

  const saveAuth = (accessToken: string) => {
    requestInstance.prototype.updateAuth(accessToken);
    localStorage.setItem('token', accessToken);
  };

  return { refetchToken, userCode, data, saveAuth };
};

export default useSignIn;
