import { AxiosError } from 'axios';
import { useMutation, useQuery } from 'react-query';
import { client } from '../../apis/axios';
import { API_PATH } from '../../constants/api';
import { CouponType, ErrorType, Meeting, MeetingTime, UserProfile } from '../../types';

export const MEETING_QUERY_KEYS = {
  meetings: 'meetings',
};

export type MeetingsResponse = {
  meetingId: number;
  couponType: CouponType;
  time: MeetingTime;
  members: UserProfile[];
  memberName: string;
  isMeetingToday: boolean;
};

export const useGetMeetings = (
  {
    onSuccess,
  }: {
    onSuccess: (meeting: Meeting[]) => void;
  } = { onSuccess: () => {} }
) =>
  useQuery<MeetingsResponse[]>(MEETING_QUERY_KEYS.meetings, () => getMeetingsRequest(), {
    onSuccess: meeting => {
      onSuccess?.(meeting);
    },
    select: (meetings: Meeting[]) => {
      const today = new Date().toISOString().split('T')[0];
      const isMeetingTodayAddedMeetings = meetings.map(meeting => ({
        ...meeting,
        isMeetingToday: meeting.time.meetingTime.split(' ')[0] === today,
      }));

      return isMeetingTodayAddedMeetings;
    },
  });

export const usePutCompleteMeeting = (
  meetingId,
  { onSuccess: handleSuccess, onError } = {
    onSuccess: () => {},
    onError: (error: AxiosError<ErrorType>) => {},
  }
) =>
  useMutation(() => putCompleteMeetingRequest(meetingId), {
    onSuccess: () => {
      handleSuccess();
    },
    onError: (error: AxiosError<ErrorType>) => {
      onError?.(error);
    },
  });

//   FETCHER

const getMeetingsRequest = async () => {
  const { data } = await client({ method: 'get', url: API_PATH.MEETINGS });

  return data;
};

const putCompleteMeetingRequest = meetingId =>
  client({ method: 'put', url: API_PATH.COMPLETE_MEETING(meetingId) });
