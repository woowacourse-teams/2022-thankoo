import { CouponType } from './coupon';
import { UserProfile } from './user';

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
