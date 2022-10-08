import { Coupon } from '../types/coupon';

interface User {
  id: number;
  name: string;
  imageUrl: string;
  email: string;
  accessToken?: number;
}

export const users: User[] = [
  {
    id: 1,
    name: '호호',
    email: 'hoho@gmail.com',
    imageUrl: 'https://i.pinimg.com/474x/da/2f/0f/da2f0fa7ba08867419e7f9e83b55d936.jpg',
  },
  {
    id: 2,
    name: '후니',
    email: 'hoho@gmail.com',
    imageUrl: 'https://i.pinimg.com/474x/1b/92/34/1b92345b60c29ee6f1e80f9904c7029e.jpg',
  },
  {
    id: 3,
    name: '비녀',
    email: 'hoho@gmail.com',
    imageUrl: 'https://i.pinimg.com/564x/c3/0b/58/c30b582f771d2854cfec9ce33e5155c7.jpg',
  },
  {
    id: 4,
    name: '라라',
    email: 'hoho@gmail.com',
    imageUrl: 'https://i.pinimg.com/474x/50/ef/5e/50ef5ed72977adc8bc615e9da4407e21.jpg',
  },
  {
    id: 5,
    name: '스컬',
    email: 'hoho@gmail.com',
    imageUrl: 'https://i.pinimg.com/474x/2c/69/db/2c69db2e20a48710da16803f89b02e57.jpg',
  },
  {
    id: 6,
    name: '후이',
    email: 'hoho@gmail.com',
    imageUrl: 'https://i.pinimg.com/474x/fd/37/3f/fd373fa6f93b4a8cca3aaf94f1dcda40.jpg',
  },
  {
    id: 7,
    name: '나인',
    email: 'hoho@gmail.com',
    imageUrl: 'https://i.pinimg.com/474x/dd/0f/84/dd0f844b653f8df5d8515f7bde6a47e9.jpg',
  },
  {
    id: 8,
    name: '마르코',
    email: 'hoho@gmail.com',
    imageUrl: 'https://i.pinimg.com/474x/e4/b5/99/e4b599b2c51956d0673a8f7f9134424e.jpg',
  },

  {
    id: 9,
    name: '빅터',
    email: 'hoho@gmail.com',
    imageUrl: 'https://i.pinimg.com/564x/48/b4/41/48b4411bc8f5b0619d7a1b426ff86e82.jpg',
  },
  {
    id: 10,
    name: '우연',
    email: 'hoho@gmail.com',
    imageUrl: 'https://i.pinimg.com/564x/48/b4/41/48b4411bc8f5b0619d7a1b426ff86e82.jpg',
  },
  {
    id: 11,
    name: '록바',
    email: 'hoho@gmail.com',
    imageUrl: 'https://i.pinimg.com/564x/48/b4/41/48b4411bc8f5b0619d7a1b426ff86e82.jpg',
  },
  {
    id: 12,
    name: '시지프',
    email: 'hoho@gmail.com',
    imageUrl: 'https://i.pinimg.com/564x/48/b4/41/48b4411bc8f5b0619d7a1b426ff86e82.jpg',
  },
  {
    id: 13,
    name: '동키콩',
    email: 'hoho@gmail.com',
    imageUrl: 'https://i.pinimg.com/564x/48/b4/41/48b4411bc8f5b0619d7a1b426ff86e82.jpg',
  },
  {
    id: 14,
    email: 'hoho@gmail.com',
    name: '앨버',
    imageUrl: 'https://i.pinimg.com/564x/48/b4/41/48b4411bc8f5b0619d7a1b426ff86e82.jpg',
  },
  {
    id: 15,
    email: 'hoho@gmail.com',
    name: '준찌',
    imageUrl: 'https://i.pinimg.com/564x/48/b4/41/48b4411bc8f5b0619d7a1b426ff86e82.jpg',
  },
];

export const dummyCoupons: Coupon[] = [
  {
    couponId: 1,
    sender: {
      ...users[0],
    },
    receiver: {
      ...users[1],
    },
    content: {
      couponType: 'coffee',
      title: '후니가 보내는 커피쿠폰',
      message: '고마워 호호~~',
    },
    status: 'not_used',
    createdDate: '2022-12-20',
  },
  {
    couponId: 2,
    sender: {
      ...users[1],
    },
    receiver: {
      ...users[0],
    },
    content: {
      couponType: 'meal',
      title: '호호가 보내는 식사쿠폰',
      message: '고마워 후니~~',
    },
    status: 'not_used',
    createdDate: '2022-12-20',
  },
  {
    couponId: 3,
    sender: {
      ...users[4],
    },
    receiver: {
      ...users[1],
    },
    content: {
      couponType: 'coffee',
      title: '숟갈이 보내는 미숟갈',
      message: '고마워 후니~~~',
    },
    status: 'reserving',
    createdDate: '2022-12-20',
  },
  {
    couponId: 4,
    sender: {
      ...users[3],
    },
    receiver: {
      ...users[1],
    },
    content: {
      couponType: 'meal',
      title: '라라가 보내는 식사쿠폰',
      message: '밥한끼 합시다',
    },
    status: 'reserved',
    createdDate: '2022-09-20',
  },
  // {
  //   couponId: 5, // Number (coupon history id)
  //   sender: {
  //     ...users[2],
  //   },
  //   receiver: {
  //     ...users[1],
  //   },
  //   content: {
  //     couponType: 'coffee',
  //     title: '비녀가 보내는 식사쿠폰',
  //     message: '커피 한잔 할래요',
  //   },
  //   status: 'reserved',
  // },
  // {
  //   couponId: 6, // Number (coupon history id)
  //   sender: {
  //     ...users[5],
  //   },
  //   receiver: {
  //     ...users[1],
  //   },
  //   content: {
  //     couponType: 'meal',
  //     title: '후이 후니 식사해요',
  //     message: '고마워 후니~~',
  //   },
  //   status: 'reserving',
  // },
  // {
  //   couponId: 7, // Number (coupon history id)
  //   sender: {
  //     ...users[6],
  //   },
  //   receiver: {
  //     ...users[1],
  //   },
  //   content: {
  //     couponType: 'meal',
  //     title: '도와줘서 고마워요 후니',
  //     message: '고마워 후니~~',
  //   },
  //   status: 'used',
  // },
  // {
  //   couponId: 8,
  //   sender: {
  //     ...users[7],
  //   },
  //   receiver: {
  //     ...users[1],
  //   },
  //   content: {
  //     couponType: 'coffee',
  //     title: '저녁식사 함께 해요',
  //     message: '하하하',
  //   },
  //   status: 'reserving',
  // },
  // {
  //   couponId: 9,
  //   sender: {
  //     ...users[8],
  //   },
  //   receiver: {
  //     ...users[6],
  //   },
  //   content: {
  //     couponType: 'meal',
  //     title: '커퓌 한좐?',
  //     message: '하하하',
  //   },
  //   status: 'used',
  // },
];
