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

export interface CouponContent {
  couponType: CouponType;
  title: string;
  message: string;
}

export type CouponTransmitStatus = 'received' | 'sent';

export const couponTypes = { entire: '전체', coffee: '커피', meal: '식사' };
export const couponTypeValues = Object.values(couponTypes);
export const couponTypeKeys = Object.keys(couponTypes);
export type CouponType = keyof typeof couponTypes;

type CouponDetailButtonBGColors = 'tomato' | '#838383' | '#5c5c5c';

export type CouponDetailButtonProps = {
  text: string;
  bg: CouponDetailButtonBGColors;
  disabled: boolean;
  onClick?: () => void;
};

type CouponDetailButtonPropsByCouponStatus = {
  [T in CouponStatus]: CouponDetailButtonProps[];
};

export type CouponDetailButton = {
  [T in CouponTransmitStatus]: CouponDetailButtonPropsByCouponStatus;
};

export type UserCanSeeCoupons = 'reserving' | 'reserved' | 'not_used';
export type UserCantSeeCoupons = Exclude<CouponStatus, UserCanSeeCoupons>;
export type CouponStatusPriority = { [T in UserCanSeeCoupons]: number };