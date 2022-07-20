export const BASE_URL = 'http://54.180.102.68:8080';

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
