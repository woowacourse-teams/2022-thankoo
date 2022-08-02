import { useEffect } from 'react';
import { useQuery } from 'react-query';
import { useLocation, useNavigate } from 'react-router-dom';
import { client } from '../../apis/axios';
import { API_PATH } from '../../constants/api';
import { GOOGLE_AUTH_URL } from '../../constants/googleAuth';
import { ROUTE_PATH } from '../../constants/routes';
import { saveAuth } from '../../utils/auth';

const useSignIn = () => {
  const navigate = useNavigate();
  const location = useLocation();
  const userCode = location.search.substring(6).split('&')[0];

  const { data, refetch: refetchToken } = useQuery(
    'token',
    async () => {
      const res = await client({
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

  useEffect(() => {
    if (userCode) {
      try {
        refetchToken().then(({ data }) => {
          const { accessToken } = data;
          if (data.joined) {
            saveAuth(accessToken);
            navigate(`${ROUTE_PATH.EXACT_MAIN}`);
            return;
          }
          navigate(`${ROUTE_PATH.ENTER_NICKNAME}`);
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
