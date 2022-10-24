import { useGetMeetings } from '../../../hooks/@queries/meeting';

const useMeetings = () => {
  const { data: meetings } = useGetMeetings();

  const meetingDateAnnouncement = meetings?.length
    ? meetings[0].isMeetingToday
      ? '오늘 예정된 약속이 있습니다'
      : `${meetings[0].dDay}일 뒤 약속이 있습니다`
    : '예정된 약속이 없습니다.';

  return { meetings, meetingDateAnnouncement };
};

export default useMeetings;
