import { CouponStatus } from './coupon';
import { MeetingTime } from './meeting';

export type Reservation = {
  reservationId: number;
  status: CouponStatus;
  time: MeetingTime | null;
};
