import { useNavigate } from 'react-router-dom';
import { ROUTE_PATH } from './../constants/routes';

const useOnSuccess = () => {
  const navigate = useNavigate();

  const successNavigate = (endRoutePath: string) => {
    navigate(ROUTE_PATH.ON_SUCCESS);
  };

  return { successNavigate };
};

export default useOnSuccess;
