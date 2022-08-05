import { useState } from 'react';
import { useMutation, useQueryClient } from 'react-query';
import { useRecoilValue } from 'recoil';
import { client } from '../../apis/axios';
import { API_PATH } from '../../constants/api';
import { targetCouponAtom } from '../../recoil/atom';
import useToast from '../useToast';
import useOnSuccess from './../useOnSuccess';

const yesterday = new Date().toISOString().split('T')[0];

const useCreateReservation = () => {
  const { visible, show, close } = useToast();
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
      onError: () => {
        show('예약이 불가능한 날짜입니다.');
      },
    }
  );

  const setReservationDate = e => {
    console.log(e.target.value);
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
