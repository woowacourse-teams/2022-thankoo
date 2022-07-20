import axios from 'axios';
import { useState } from 'react';
import { useMutation } from 'react-query';
import { useRecoilValue } from 'recoil';
import { API_PATH } from '../../constants/api';
import { authAtom } from '../../recoil/atom';

const useCreateReservation = () => {
  const [date, setDate] = useState('');
  const { accessToken } = useRecoilValue(authAtom);
  const isFilled = date;

  const { mutate } = useMutation((Date: string) =>
    axios({
      method: 'post',
      url: `${API_PATH.RESERVATIONS}`,
      headers: { Authorization: `Bearer ${accessToken}` },
      data: {
        couponId: '1',
        startAt: Date,
      },
    })
  );

  const setReservationDate = e => {
    setDate(e.target.value + ` 00:00:00`);
  };

  const sendReservation = () => {
    mutate(date);
  };

  return { isFilled, setReservationDate, sendReservation };
};

export default useCreateReservation;
