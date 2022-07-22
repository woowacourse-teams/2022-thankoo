import axios from 'axios';
import { useState } from 'react';
import { useMutation } from 'react-query';
import { useNavigate } from 'react-router-dom';
import { useRecoilValue } from 'recoil';
import { API_PATH } from '../../constants/api';
import { targetCouponAtom } from '../../recoil/atom';

const yesterday = new Date().toISOString().split('T')[0];

const useCreateReservation = () => {
  const navigate = useNavigate();
  const couponId = useRecoilValue(targetCouponAtom);
  const [date, setDate] = useState(yesterday);
  const [time, setTime] = useState('10:00');

  const accessToken = localStorage.getItem('token');
  const isFilled = date;

  const { mutate } = useMutation(() =>
    axios({
      method: 'post',
      url: `${API_PATH.RESERVATIONS}`,
      headers: { Authorization: `Bearer ${accessToken}` },
      data: {
        couponId,
        startAt: `${date} ${time}:00`,
      },
    })
  );

  const setReservationDate = e => {
    setDate(e.target.value);
  };

  const sendReservation = async () => {
    await mutate();

    navigate('/');
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
