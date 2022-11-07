import { Navigate, Outlet, useLocation } from 'react-router-dom';
import { ROUTE_PATH } from '../constants/routes';

export const AuthOnly = () => {
  const storageToken = localStorage.getItem('token');

  const { search } = useLocation();
  if (search) {
    localStorage.setItem('query', search);
  }

  return storageToken ? <Outlet /> : <Navigate to={ROUTE_PATH.SIGN_IN} replace />;
};

export const UnAuthOnly = () => {
  const storageToken = localStorage.getItem('token');

  return !storageToken ? <Outlet /> : <Navigate to={ROUTE_PATH.EXACT_MAIN} replace />;
};
