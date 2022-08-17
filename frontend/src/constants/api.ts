export const BASE_URL = process.env.API_URL;

export const API_PATH = {
  RECEIVED_COUPONS_NOT_USED: `/api/coupons/received?status=not-used`,
  RECEIVED_COUPONS_USED: `/api/coupons/received?status=used`,
  SENT_COUPONS: `/api/coupons/sent`,
  SEND_COUPON: `/api/coupons/send`,
  MEMBERS: `/api/members`,
  PROFILE: `/api/members/me`,
  EDIT_PROFILE: `/api/members/me/profile-image`,
  EDIT_NAME: `/api/members/me/name`,
  RESERVATIONS: `/api/reservations`,
  RESERVATIONS_SENT: `/api/reservations/sent`,
  RESERVATIONS_RECEIVED: `/api/reservations/received`,
  SIGN_IN: userCode => `/api/sign-in?code=${userCode}`,
  SIGN_UP: '/api/sign-up',
  COUPON_DETAIL: '/api/coupons',
  GET_COUPON_DETAIL: couponId => `/api/coupons/${couponId}`,
  MEETINGS: '/api/meetings',
  CANCEL_RESERVATION: reservationId => `/api/reservations/${reservationId}/cancel`,
  COMPLETE_MEETING: meetingId => `/api/meetings/${meetingId}`,
  GET_COUPONS_EXCHANGE_COUNT: `/api/coupons/count`,
};
