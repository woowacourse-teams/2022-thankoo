import { useEffect, useState } from 'react';
import { Meeting } from '../../../types';
import { useGetMeetings } from '../queries/useGetMeetings';

const dayDifferenceFromToday = (meetingDay) =>{
  const today = new Date();

  const dayForCal = `${meetingDay.getFullYear()}` + `${String(meetingDay.getMonth()).padStart(2,'0')}` + `${String(meetingDay.getDay()).padStart(2,'0')}`;
  const todayForCal = `${today.getFullYear()}` + `${String(today.getMonth()).padStart(2,'0')}` + `${String(today.getDay()).padStart(2,'0')}`;
  
  return Number(dayForCal) - Number(todayForCal);
}

const useMeetings = () => {
  const { data: meetings, isLoading, isError, isSuccess } = useGetMeetings();
  const [diffWithNearestDate, setDiffWithNearestDate] = useState<number>(0);
  const [meeting, setMeeting] = useState<Meeting[]>([]);

  meetings?.sort(
    (m1, m2) =>
      Number(new Date(m1.time?.meetingTime.replaceAll('-','/'))) -
      Number(new Date(m2.time?.meetingTime.replaceAll('-','/'))));

  useEffect(() => {
    if (meetings) {
      const meetingDay = new Date(meetings[0]?.time?.meetingTime.replaceAll('-','/').split(' ')[0])
   
      setDiffWithNearestDate(
        dayDifferenceFromToday(meetingDay)
      );
    }
  }, [isSuccess]);

  const isTodayMeetingExist = diffWithNearestDate === 0;

  return { meetings, isLoading, isError, isTodayMeetingExist, diffWithNearestDate };
};

export default useMeetings;
