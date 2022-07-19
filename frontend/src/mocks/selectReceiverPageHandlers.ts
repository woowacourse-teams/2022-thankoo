import { rest } from 'msw';
import { API_PATH } from '../constants/api';
import { users } from './dummyData';

export const selectReceiverPageHandlers = [
  rest.get(`${API_PATH.MEMBERS}`, (req, res, ctx) => {
    const accessToken = req.headers.get('authorization')?.split(' ')[1];

    const acceptMeUsers = users.filter(
      user => !user.accessToken || user?.accessToken !== Number(accessToken)
    );

    return res(ctx.status(200), ctx.json(acceptMeUsers));
  }),
];
