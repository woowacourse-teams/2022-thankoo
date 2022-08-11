import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { useRecoilState, useRecoilValue } from 'recoil';
import { ROUTE_PATH } from '../../constants/routes';
import { onSuccessContentAtom, targetCouponAtom } from '../../recoil/atom';
import { useCreateReservationMutation } from '../queries/reservation';
import { useGetCouponDetail } from '../Main/queries/couponDetail';
import useOnSuccess from '../useOnSuccess';
import CreateReservationSuccess from '../../components/CreateReservation/CreateReservationSuccess';
import useToast from '../useToast';

const yesterday = new Date().toISOString().split('T')[0];

const useCreateReservation = () => {
  const navigate = useNavigate();
  const { insertToastItem, closeToastItem } = useToast();
  const { successNavigate } = useOnSuccess();
  const couponId = useRecoilValue(targetCouponAtom);
  const [date, setDate] = useState(yesterday);
  const [time, setTime] = useState('');
  const isFilled = date && time.length;
  const [content, setContent] = useRecoilState(onSuccessContentAtom);

  const { data: couponDetail } = useGetCouponDetail(couponId, {
    onError: () => {
      navigate(ROUTE_PATH.EXACT_MAIN);
    },
  });

  if (couponDetail?.reservation) {
    navigate(ROUTE_PATH.EXACT_MAIN);
  }

  const { mutate: createReservation } = useCreateReservationMutation(
    { couponId, date, time },
    {
      onSuccess: () => {
        successNavigate(ROUTE_PATH.EXACT_MAIN);
        setContent(() =>
          CreateReservationSuccess({ date, receiver: couponDetail?.coupon.receiver.name, time })
        );
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
