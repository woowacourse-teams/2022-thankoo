import { YYYYMMDD } from 'thankoo-utils-type';
import { Meeting } from './meeting';
import { Reservation } from './reservation';
import { UserProfile } from './user';

export type CouponStatus =
  | 'expired'
  | 'used'
  | 'reserving'
  | 'reserved'
  | 'not_used'
  | 'immediately_used';

export type Coupon = {
  couponId: number;
  sender: UserProfile;
  receiver: UserProfile;
  content: CouponContent;
  status: CouponStatus;
  createdDate: YYYYMMDD;
  modifiedDateTime: string;
};
export type CouponDetail = {
  coupon: Coupon;
  meeting: Meeting | null;
  reservation: Reservation | null;
};

export type CouponContent = {
  couponType: CouponTransmitableType;
  title: string;
  message: string;
};

export type CouponTransmitStatus = 'received' | 'sent';

export const couponTypes = { entire: '전체', coffee: '커피', meal: '식사' } as const;
export const couponTypeValues = Object.values(couponTypes);
export const couponTypeKeys = Object.keys(couponTypes);
export type CouponType = keyof typeof couponTypes;
export type CouponTypeValue = typeof couponTypes[CouponType];
export type CouponTransmitableType = Exclude<CouponType, 'entire'>;

export type UserCanSeeCoupons = 'reserving' | 'reserved' | 'not_used';
export type UserCantSeeCoupons = Exclude<CouponStatus, UserCanSeeCoupons>;
export type CouponStatusPriority = { [T in UserCanSeeCoupons]: number };
