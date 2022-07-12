import { rest } from 'msw';
import { BASE_URL } from '../constants';
import { users } from './dummyData';

export const authPageHandler = [
  rest.get(`${BASE_URL}/api/sign-in`, (req, res, ctx) => {
    const newUser = req.url.searchParams.get('code') as string;
    users.push({
      id: users.length + 1,
      name: newUser,
      socialNickname: `${newUser}_social`,
      imageUrl: 'dummy.com',
      accessToken: 1,
    });

    return res(ctx.status(200), ctx.json({ accessToken: 1, memberId: 123 }));
  }),
];
