import { CouponType } from './coupon';
import { UserProfile } from './user';
// 2022-10-08 17:00:00

export type MeetingTime = {
  meetingTime: string;
  timeZone: string;
};

export interface Meeting {
  meetingId: number;
  couponType: CouponType;
  time: MeetingTime;
  members: UserProfile[];
  memberName: string;
  isMeetingToday: boolean;
}
