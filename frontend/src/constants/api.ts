export const BASE_URL = 'http://localhost:3000';
export const AUTH_URL = 'http://localhost:8080';

const API_PREFIX = `${BASE_URL}/api`;
const AUTH_PREFIX = `${AUTH_URL}/api`;

export const API_PATH = {
  RECEIVED_COUPONS: `${API_PREFIX}/coupons/received`,
  MEMBERS: `${API_PREFIX}/members`,
  RESERVATIONS: `${API_PREFIX}/reservations`,
  SEND_COUPON: `${API_PREFIX}/coupons/send`,
  SIGN_IN: userCode => `${AUTH_PREFIX}/sign-in?code=${userCode}`,
};
