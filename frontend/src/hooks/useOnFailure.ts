import { useNavigate } from 'react-router-dom';
import { ROUTE_PATH } from '../constants/routes';

const useOnFailure = () => {
  const navigate = useNavigate();

  const failureNavigate = (endRoutePath: string) => {
    navigate(`${ROUTE_PATH.ON_FAILURE}`);
    setTimeout(() => {
      navigate(endRoutePath);
    }, 1300);
  };

  return { failureNavigate };
};

export default useOnFailure;
