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

export type CouponType = 'entire' | 'coffee' | 'meal' | 'custom';
