import React from 'react';
import { useQuery } from 'react-query';
import { client } from '../../../apis/axios';
import { API_PATH } from '../../../constants/api';
import { Meeting } from '../../../types';

const useMeetings = () => {
  const {
    data: meetings,
    isLoading,
    isError,
  } = useQuery<Meeting[]>('meetings', async () => {
    const { data } = await client({ method: 'get', url: API_PATH.MEETINGS });

    return data;
  });

  meetings?.sort(
    (m1, m2) =>
      Number(new Date(m1.time?.meetingTime as string)) -
      Number(new Date(m2.time?.meetingTime as string))
  );

  const diffWithNearestDate = Math.floor(
    (Number(new Date(meetings?.[0]?.time?.meetingTime as string)) - Number(new Date())) /
      (1000 * 60 * 60 * 24)
  );

  const isTodayMeetingExist = diffWithNearestDate === 0;

  return { meetings, isLoading, isError, isTodayMeetingExist, diffWithNearestDate };
};

export default useMeetings;
