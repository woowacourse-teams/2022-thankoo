import { rest } from 'msw';
import { BASE_URL } from '../constants';
import { users } from './dummyData';

export const selectReceiverPageHandlers = [
  rest.get(`${BASE_URL}/api/members`, (req, res, ctx) => {
    const accessToken = req.headers.get('authorization')?.split(' ')[1];

    const acceptMeUsers = users.filter(
      user => !user.accessToken || user?.accessToken !== Number(accessToken)
    );

    return res(ctx.status(200), ctx.json(acceptMeUsers));
  }),
];
