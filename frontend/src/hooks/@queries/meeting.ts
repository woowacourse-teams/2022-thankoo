import { AxiosError } from 'axios';
import { useMutation, useQuery } from 'react-query';
import { YYYYMMDD } from 'thankoo-utils-type';
import { client } from '../../apis/axios';
import { API_PATH } from '../../constants/api';
import { ErrorType } from '../../types/api';
import { CouponType } from '../../types/coupon';
import { Meeting, MeetingTime } from '../../types/meeting';
import { UserProfile } from '../../types/user';
import { sorted } from '../../utils';
import {
  getDayDifference,
  getTimeDifference,
  isExpiredDate,
  krLocaleDateFormatter,
  serverDateFormmater,
} from '../../utils/date';

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
  dDay: number;
  meetingDate: YYYYMMDD;
  meetingTime: string;
};

const { fullDate: today } = krLocaleDateFormatter(new Date().toLocaleDateString());

export const useGetMeetings = () =>
  useQuery<MeetingsResponse[]>(MEETING_QUERY_KEYS.meetings, () => getMeetingsRequest(), {
    select: (meetings: Meeting[]) => {
      const validMeetings = meetings
        .map(meeting => {
          const { date, time } = serverDateFormmater(meeting.time.meetingTime);

          return {
            ...meeting,
            meetingDate: date,
            meetingTime: time.slice(0, 5),
            isMeetingToday: date === today,
            dDay: getDayDifference(date, today),
          };
        })
        .filter(meeting => isExpiredDate(meeting.time.meetingTime));

      const sortedMeetings = sorted(validMeetings, (m1, m2) =>
        getTimeDifference(m1.time.meetingTime, m2.time.meetingTime)
      );

      return sortedMeetings;
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
