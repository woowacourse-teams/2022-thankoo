import { rest } from 'msw';
import { users } from './dummyData';

const BASE_URL = 'http://localhost:3000';

export const selectReceiverPageHandlers = [
  rest.get(`${BASE_URL}/api/members`, (req, res, ctx) => {
    return res(ctx.status(200), ctx.json(users));
  }),
];
