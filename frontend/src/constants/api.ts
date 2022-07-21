const BACK_SERVER = 'http://54.180.102.68';
const DEV_SERVER = 'http://localhost:3000';

export const BASE_URL = process.env.NODE_ENV === 'development' ? DEV_SERVER : BACK_SERVER;

const API_PREFIX = `${BASE_URL}/api`;

export const API_PATH = {
  RECEIVED_COUPONS_NOT_USED: `${API_PREFIX}/coupons/received?status=not-used`,
  RECEIVED_COUPONS_USED: `${API_PREFIX}/coupons/received?status=used`,
  SENT_COUPONS: `${API_PREFIX}/coupons/sent`,
  SEND_COUPON: `${API_PREFIX}/coupons/send`,
  MEMBERS: `${API_PREFIX}/members`,
  PROFILE: `${API_PREFIX}/members/me`,
  RESERVATIONS: `${API_PREFIX}/reservations`,
  SIGN_IN: userCode => `${API_PREFIX}/sign-in?code=${userCode}`,
};
