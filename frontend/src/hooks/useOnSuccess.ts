import { useNavigate } from 'react-router-dom';
import { useRecoilState } from 'recoil';
import { onSuccessContentAtom } from '../recoil/atom';
import { ROUTE_PATH } from './../constants/routes';

const useOnSuccess = () => {
  const [_, setSuccessContent] = useRecoilState(onSuccessContentAtom);
  const navigate = useNavigate();

  const successNavigate = ({ page, props }) => {
    setSuccessContent({ page, props });
    navigate(ROUTE_PATH.ON_SUCCESS);
  };

  return { successNavigate };
};

export default useOnSuccess;
