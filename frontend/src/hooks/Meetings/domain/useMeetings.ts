import { useEffect, useState } from 'react';
import { Meeting } from '../../../types';
import { useGetMeetings } from '../queries/useGetMeetings';

const useMeetings = () => {
  const { data: meetings, isLoading, isError, isSuccess } = useGetMeetings();
  const [diffWithNearestDate, setDiffWithNearestDate] = useState<number>(0);
  const [meeting, setMeeting] = useState<Meeting[]>([]);

  meetings?.sort(
    (m1, m2) =>
      Number(new Date(m1.time?.meetingTime as string)) -
      Number(new Date(m2.time?.meetingTime as string))
  );

  useEffect(() => {
    if (meetings) {
    
      setDiffWithNearestDate(
        Math.floor(
          (Number(new Date(meetings[0]?.time?.meetingTime.replaceAll('-','/'))) - Number(new Date())) /
            (1000 * 60 * 60 * 24)
        )
      );
      setMeeting(meetings);
    }
  }, [isSuccess]);

  const isTodayMeetingExist = diffWithNearestDate === 0;

  return { meeting, meetings, isLoading, isError, isTodayMeetingExist, diffWithNearestDate };
};

export default useMeetings;
