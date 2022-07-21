import { rest } from 'msw';
import { API_PATH } from '../constants/api';

export const signInHandler = [
  rest.get(`${API_PATH.SIGN_IN('')}`, (req, res, ctx) => {
    return res(ctx.status(200), ctx.json({ accessToken: 'testToken1234' }));
  }),
];
