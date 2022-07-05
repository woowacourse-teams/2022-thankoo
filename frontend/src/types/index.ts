export interface Coupon {
  couponHistoryId: number;
  sender: UserProfile;
  receiver: UserProfile;
  content: CouponContent;
}

export interface UserProfile {
  id: number;
  name: String;
  socialNickname: String;
  imageUrl: string;
}

export interface CouponContent {
  couponType: CouponType;
  title: string;
  message: string;
}

export const couponTypes = { entire: '전체', coffee: '커피', meal: '식사' };
export const couponTypesList = Object.keys(couponTypes);
export type CouponType = typeof couponTypesList[number];
