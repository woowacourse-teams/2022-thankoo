import { useState } from 'react';
import { Meeting } from '../../types/meeting';
import { dayDifferenceFromToday } from '../../utils/date';
import { useGetMeetings } from '../@queries/meeting';

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
