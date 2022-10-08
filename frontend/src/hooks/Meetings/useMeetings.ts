import { useState } from 'react';
import { Meeting } from '../../types';
import { MeetingsResponse, useGetMeetings } from '../@queries/meeting';

const dayDifferenceFromToday = meetingDay => {
  const today = new Date();

  const dayForCal =
    `${meetingDay.getFullYear()}` +
    `${String(meetingDay.getMonth()).padStart(2, '0')}` +
    `${String(meetingDay.getDate()).padStart(2, '0')}`;
  const todayForCal =
    `${today.getFullYear()}` +
    `${String(today.getMonth()).padStart(2, '0')}` +
    `${String(today.getDate()).padStart(2, '0')}`;

  return Number(dayForCal) - Number(todayForCal);
};

const useMeetings = () => {
  const [nearestMeetingDate, setNearestMeetingDate] = useState<number>(0);
  const { data: meetings } = useGetMeetings({
    onSuccess: (meetings: Meeting[]) => {
      if (!!meetings.length) {
        const meetingDay = new Date(
          meetings[0]?.time?.meetingTime.replaceAll('-', '/').split(' ')[0]
        );

        setNearestMeetingDate(dayDifferenceFromToday(meetingDay));
      }
    },
  });

  const todayFormattedDateString = new Date().toJSON().split('T')[0];

  meetings?.sort(
    (m1, m2) =>
      Number(new Date(m1.time?.meetingTime.replaceAll('-', '/'))) -
      Number(new Date(m2.time?.meetingTime.replaceAll('-', '/')))
  );

  const isTodayMeetingExist = meetings?.length
    ? meetings[0].time.meetingTime.split(' ')[0] === todayFormattedDateString
    : false;

  return { meetings, isTodayMeetingExist, nearestMeetingDate };
};

export default useMeetings;
