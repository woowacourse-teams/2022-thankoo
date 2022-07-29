import { useEffect } from 'react';
import { useQuery } from 'react-query';
import { useLocation, useNavigate } from 'react-router-dom';
import { requestInstance } from '../../apis/axios';
import { API_PATH } from '../../constants/api';
import { GOOGLE_AUTH_URL } from '../../constants/googleAuth';
import { ROUTE_PATH } from '../../constants/routes';

const useSignIn = () => {
  const navigate = useNavigate();
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

  useEffect(() => {
    if (userCode) {
      try {
        refetchToken().then(({ data }) => {
          console.log(data);
          const accessToken = data.accessToken;
          saveAuth(accessToken);
          navigate(`${ROUTE_PATH.EXACT_MAIN}`);
        });
      } catch (e) {
        alert('로그인에 실패하였습니다.');
      }
    }
  }, [userCode]);

  const redirectGoogleAuth = () => {
    window.location.href = GOOGLE_AUTH_URL;
  };

  return { refetchToken, userCode, data, saveAuth, redirectGoogleAuth };
};

export default useSignIn;
