export interface Coupon {
  couponHistoryId: number;
  sender: UserProfile;
  receiver: UserProfile;
  content: CouponContent;
}

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

export const initialCouponState = {
  couponHistoryId: 0,
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
export type CouponType = typeof couponTypeKeys[number];
