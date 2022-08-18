import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { useRecoilValue } from 'recoil';
import { ROUTE_PATH } from '../../constants/routes';
import { targetCouponAtom } from '../../recoil/atom';
import { usePostReservationMutation } from '../@queries/reservation';
import { useGetCouponDetail } from '../Main/queries/couponDetail';
import useModal from '../useModal';
import useOnSuccess from '../useOnSuccess';
import useToast from '../useToast';

const yesterday = new Date().toISOString().split('T')[0];

const useCreateReservation = () => {
  const navigate = useNavigate();
  const { insertToastItem } = useToast();
  const { successNavigate } = useOnSuccess();
  const couponId = useRecoilValue(targetCouponAtom);
  const [date, setDate] = useState(yesterday);
  const [time, setTime] = useState('');
  const isFilled = date && time.length;
  const { close } = useModal();

  const { data: couponDetail } = useGetCouponDetail(couponId, {
    onError: () => {
      navigate(ROUTE_PATH.EXACT_MAIN);
    },
  });

  if (couponDetail?.reservation) {
    navigate(ROUTE_PATH.EXACT_MAIN);
  }

  const { mutate: createReservation } = usePostReservationMutation(
    { couponId, date, time },
    {
      onSuccess: () => {
        successNavigate({
          page: ROUTE_PATH.CREATE_RESERVATION,
          props: {
            date,
            receiver: couponDetail?.coupon.sender.name,
            time,
          },
        });
        close();
      },
      onError: () => {
        insertToastItem('예약이 불가능한 날짜입니다.');
      },
    }
  );

  const setReservationDate = e => {
    setDate(e.target.value);
  };

  return {
    isFilled,
    setReservationDate,
    createReservation,
    date,
    yesterday,
    time,
    setTime,
    couponDetail,
  };
};

export default useCreateReservation;
