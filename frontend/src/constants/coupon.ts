import { palette } from './../styles/ThemeProvider';
import { BASE_URL } from './api';

export const RAND_COLORS = [
  { bg: '#FAC8BF', color: palette.BLACK },
  { bg: '#C0E4D9', color: palette.BLACK },
  { bg: 'rgb(1, 160, 230)', color: palette.WHITE },
  { bg: 'rgb(240, 110, 200)', color: palette.WHITE },
  { bg: '#FDE893', color: palette.BLACK },
  { bg: 'rgb(99, 51, 198)', color: palette.WHITE },
  { bg: '#E3C2F1', color: palette.BLACK },
  { bg: '#FFF970', color: palette.BLACK },
];

export const COUPON_IMAGE = {
  coffee: `${BASE_URL}/coupon-image/coffee_coupon.svg`,
  meal: `${BASE_URL}/coupon-image/meal_coupon.svg`,
};

export const COUPON_STATUS_BUTTON = {
  received: {
    not_used: [{ text: '예약 하기', bg: 'tomato', disabled: false }],
    reserving: [{ text: '예약 취소', bg: 'tomato', disabled: false }],
    reserved: [{ text: '사용 완료', bg: 'tomato', disabled: false }],
    used: '이미 사용된 쿠폰입니다',
    expired: '만료된 쿠폰입니다',
  },
  sent: {
    not_used: [
      {
        text: '상대가 아직 예약하지 않았습니다.',
        disabled: true,
        bg: '#838383',
      },
    ],
    reserving: [
      {
        text: '승인',
        disabled: false,
        bg: 'tomato',
        status: 'reserving',
      },
      {
        text: '거절',
        disabled: false,
        bg: '#838383',
      },
    ],
    reserved: [{ text: '사용 완료', disabled: false, bg: 'tomato' }],
    used: [{ text: '이미 사용된 쿠폰입니다', disabled: true, bg: '#838383' }],
    expired: [{ text: '만료된 쿠폰입니다', disabled: true, bg: '#838383' }],
  },
};

export const COUPON_STATUS_STRAP_TEXT = {
  not_used: '예약 하기',
  reserving: '예약 중',
  reserved: '예약 완료',
  used: '이미 사용된 쿠폰입니다',
  immediately_used: '이미 사용된 쿠폰입니다',
  expired: '만료된 쿠폰입니다',
};

export const COUPON_TITLE_MAX_LENGTH = 20;
export const COUPON_MESSEGE_MAX_LENGTH = 100;

export const initialCouponState = {
  couponId: 0,
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
