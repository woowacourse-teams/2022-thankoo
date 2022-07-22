import axios from 'axios';
import { useState } from 'react';
import { useMutation } from 'react-query';
import { useNavigate } from 'react-router-dom';
import { API_PATH } from '../../constants/api';

const yesterday = new Date().toISOString().split('T')[0];

const useCreateReservation = () => {
  const navigate = useNavigate();
  const [date, setDate] = useState(yesterday);
  const [time, setTime] = useState('10:00');

  const accessToken = localStorage.getItem('token');
  const isFilled = date;

  const { mutate } = useMutation((startAt: string) =>
    axios({
      method: 'post',
      url: `${API_PATH.RESERVATIONS}`,
      headers: { Authorization: `Bearer ${accessToken}` },
      data: {
        couponId: '1',
        startAt,
      },
    })
  );

  const setReservationDate = e => {
    setDate(e.target.value);
  };

  const sendReservation = async () => {
    await mutate(`${date} ${time}:00`);

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
