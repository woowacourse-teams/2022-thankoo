export const RAND_COLORS = [
  { bg: '#FAC8BF', color: 'black' },
  { bg: '#C0E4D9', color: 'black' },
  { bg: 'rgb(1, 160, 230)', color: 'white' },
  { bg: 'rgb(240, 110, 200)', color: 'white' },
  { bg: '#FDE893', color: 'black' },
  { bg: 'rgb(99, 51, 198)', color: 'white' },
  { bg: '#E3C2F1', color: 'black' },
  { bg: '#FFF970', color: 'black' },
];

export const COUPON_IMAGE = {
  coffee:
    'https://user-images.githubusercontent.com/41886825/177711095-cb6ff72d-d017-4c13-9a70-918f89eb6aaa.png',
  meal: 'https://user-images.githubusercontent.com/41886825/177712393-fd41d832-bb76-44c3-a041-f619e9de9272.png',
};

export const COUPON_STATUS_BUTTON = {
  받은: {
    not_used: [{ text: '예약 하기', bg: 'tomato', disabled: false }],
    reserving: [{ text: '예약 취소', bg: 'tomato', disabled: false }],
    reserved: [{ text: '사용 완료', bg: 'tomato', disabled: false }],
    used: '이미 사용된 쿠폰입니다',
    expired: '만료된 쿠폰입니다',
  },
  보낸: {
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
  expired: '만료된 쿠폰입니다',
};

export const COUPON_TITLE_MAX_LENGTH = 20;
export const COUPON_MESSEGE_MAX_LENGTH = 100;
