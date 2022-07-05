import { Coupon } from '../types';

export const dummyCoupons: Coupon[] = [
  {
    couponHistoryId: 1, // Number (coupon history id)
    sender: {
      id: 2, // Number
      name: '후니', // String
      socialNickname: 'jayjaehunchoi', // String
      imageUrl: 'https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/96.png', // String
    },
    receiver: {
      id: 1,
      name: '호호',
      socialNickname: '호호산타',
      imageUrl: 'https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/96.png',
    },
    content: {
      couponType: 'coffee',
      title: '후니가 보내는 커피쿠폰',
      message: '고마워 호호~~',
    },
  },
  {
    couponHistoryId: 2, // Number (coupon history id)
    sender: {
      id: 2, // Number
      name: '호호', // String
      socialNickname: 'hohoho', // String
      imageUrl: 'https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/96.png', // String
    },
    receiver: {
      id: 1,
      name: '후니',
      socialNickname: '후니오빠',
      imageUrl: 'https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/96.png',
    },
    content: {
      couponType: 'meal',
      title: '호호가 보내는 식사쿠폰',
      message: '고마워 후니~~',
    },
  },
  {
    couponHistoryId: 3, // Number (coupon history id)
    sender: {
      id: 3, // Number
      name: '스컬', // String
      socialNickname: 'skrrr', // String
      imageUrl: 'https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/96.png', // String
    },
    receiver: {
      id: 1,
      name: '후니',
      socialNickname: '후니오빠',
      imageUrl: 'https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/96.png',
    },
    content: {
      couponType: 'coffee',
      title: '숟갈이 보내는 미숟갈',
      message: '고마워 후니~~~',
    },
  },
  {
    couponHistoryId: 4, // Number (coupon history id)
    sender: {
      id: 2, // Number
      name: '라라', // String
      socialNickname: 'lalaa', // String
      imageUrl: 'https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/96.png', // String
    },
    receiver: {
      id: 1,
      name: '후니',
      socialNickname: '후니오빠',
      imageUrl: 'https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/96.png',
    },
    content: {
      couponType: 'meal',
      title: '라라가 보내는 식사쿠폰',
      message: '밥한끼 합시다',
    },
  },
  {
    couponHistoryId: 5, // Number (coupon history id)
    sender: {
      id: 6, // Number
      name: '비녀', // String
      socialNickname: 'binyeo', // String
      imageUrl: 'https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/96.png', // String
    },
    receiver: {
      id: 1,
      name: '후니',
      socialNickname: '후니오빠',
      imageUrl: 'https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/96.png',
    },
    content: {
      couponType: 'coffee',
      title: '비녀가 보내는 식사쿠폰',
      message: '커피 한잔 할래요',
    },
  },
  {
    couponHistoryId: 6, // Number (coupon history id)
    sender: {
      id: 7, // Number
      name: '후이', // String
      socialNickname: 'hui', // String
      imageUrl: 'https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/96.png', // String
    },
    receiver: {
      id: 1,
      name: '후니',
      socialNickname: '후니오빠',
      imageUrl: 'https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/96.png',
    },
    content: {
      couponType: 'meal',
      title: '후이 후니 식사해요',
      message: '고마워 후니~~',
    },
  },
  {
    couponHistoryId: 7, // Number (coupon history id)
    sender: {
      id: 8, // Number
      name: '나인', // String
      socialNickname: 'hohoho', // String
      imageUrl: 'https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/96.png', // String
    },
    receiver: {
      id: 1,
      name: '후니',
      socialNickname: '후니오빠',
      imageUrl: 'https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/96.png',
    },
    content: {
      couponType: 'meal',
      title: '도와줘서 고마워요 후니',
      message: '고마워 후니~~',
    },
  },
];
