import { useState } from 'react';
import { useMutation, useQueryClient } from 'react-query';
import { useNavigate } from 'react-router-dom';
import { useRecoilValue } from 'recoil';
import { client } from '../../apis/axios';
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
  const [time, setTime] = useState('');

  const isFilled = date && time.length;

  const mutateCreateReservation = useMutation(
    () =>
      client({
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
      retry: false,
    }
  );

  const setReservationDate = e => {
    setDate(e.target.value);
  };

  const sendReservation = async () => {
    await mutateCreateReservation.mutate();
  };

  return {
    isFilled,
    setReservationDate,
    sendReservation,
    date,
    yesterday,
    time,
    setTime,
  };
};

export default useCreateReservation;
