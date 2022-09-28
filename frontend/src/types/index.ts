import { YYYY, YYYYMMDD } from 'thankoo-utils-type';

export type CouponStatus = 'expired' | 'used' | 'reserving' | 'reserved' | 'not_used';

export interface Coupon {
  couponId: number;
  sender: UserProfile;
  receiver: UserProfile;
  content: CouponContent;
  status: CouponStatus;
  createdDate: YYYYMMDD;
}
export interface CouponDetail {
  coupon: Coupon;
  meeting: Meeting | null;
  reservation: Reservation | null;
}

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
}

export type Reservation = {
  reservationId: number;
  status: CouponStatus;
  time: MeetingTime | null;
};
export interface UserProfile {
  id: number;
  name: string;
  email: string;
  imageUrl: string;
}

export interface CouponContent {
  couponType: CouponType;
  title: string;
  message: string;
}

export interface ErrorType {
  errorCode: string;
  message: string;
}

export const initialCouponState = {
  couponId: 0,
  sender: {
    id: 0,
    name: '',
    email: '',
    imageUrl: '',
  },
  receiver: {
    id: 0,
    name: '',
    email: '',
    imageUrl: '',
  },
  content: {
    couponType: '',
    title: '',
    message: '',
  },
};

export const couponTypes = { entire: '전체', coffee: '커피', meal: '식사' };
export const couponTypeValues = Object.values(couponTypes);
export const couponTypeKeys = Object.keys(couponTypes);
export type CouponType = keyof typeof couponTypes;
