export const BASE_URL = 'http://54.180.102.68:8080';

const API_PREFIX = `${BASE_URL}/api`;

export const API_PATH = {
  RECEIVED_COUPONS: `${API_PREFIX}/coupons/received`,
  MEMBERS: `${API_PREFIX}/members`,
  RESERVATIONS: `${API_PREFIX}/reservations`,
  SEND_COUPON: `${API_PREFIX}/coupons/send`,
  SIGN_IN: userCode => `${API_PREFIX}/sign-in?code=${userCode}`,
};
