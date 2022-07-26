import { useState } from 'react';
import { useMutation, useQueryClient } from 'react-query';
import { useNavigate } from 'react-router-dom';
import { useRecoilValue } from 'recoil';
import { requestInstance } from '../../api';
import { API_PATH } from '../../constants/api';
import { targetCouponAtom } from '../../recoil/atom';
import useOnSuccess from './../useOnSuccess';

const yesterday = new Date().toISOString().split('T')[0];

const useCreateReservation = () => {
  const navigate = useNavigate();
  const { successNavigate } = useOnSuccess();
  const couponId = useRecoilValue(targetCouponAtom);
  const queryClient = useQueryClient();
  const [date, setDate] = useState(yesterday);
  const [time, setTime] = useState('10:00');

  const isFilled = date;

  const mutateCreateReservation = useMutation(
    () =>
      requestInstance({
        method: 'post',
        url: `${API_PATH.RESERVATIONS}`,
        data: {
          couponId,
          startAt: `${date} ${time}:00`,
        },
      }),
    {
      onSuccess: () => {
        successNavigate('/');
        queryClient.invalidateQueries('coupon');
      },
    }
  );

  const setReservationDate = e => {
    setDate(e.target.value);
  };

  const sendReservation = async () => {
    await mutateCreateReservation.mutate();
  };

  const setReservationTime = e => {
    setTime(e.target.value);
  };

  return {
    isFilled,
    setReservationDate,
    sendReservation,
    date,
    yesterday,
    time,
    setReservationTime,
  };
};

export default useCreateReservation;
