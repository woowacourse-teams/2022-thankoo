const BACK_SERVER = 'http://54.180.102.68';
const DEV_SERVER = 'http://localhost:3000';

export const BASE_URL = process.env.NODE_ENV === 'development' ? DEV_SERVER : BACK_SERVER;

export const API_PATH = {
  RECEIVED_COUPONS_NOT_USED: `/api/coupons/received?status=not-used`,
  RECEIVED_COUPONS_USED: `/api/coupons/received?status=used`,
  SENT_COUPONS: `/api/coupons/sent`,
  SEND_COUPON: `/api/coupons/send`,
  MEMBERS: `/api/members`,
  PROFILE: `/api/members/me`,
  RESERVATIONS: `/api/reservations`,
  RESERVATIONS_SENT: `/api/reservations/sent`,
  RESERVATIONS_RECEIVED: `/api/reservations/received`,
  SIGN_IN: userCode => `/api/sign-in?code=${userCode}`,
};
