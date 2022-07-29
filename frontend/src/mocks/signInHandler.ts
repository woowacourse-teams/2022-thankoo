import { rest } from 'msw';
import { API_PATH } from '../constants/api';

export const signInHandler = [
  rest.get(API_PATH.SIGN_IN(''), (req, res, ctx) => {
    return res(
      ctx.status(200),
      ctx.json({
        accessToken: null,
        joined: false,
        email: 'skrr@gmail.com',
      })
    );
  }),
  rest.post(API_PATH.SIGN_UP, (req, res, ctx) => {
    return res(
      ctx.json({
        joined: true,
        accessToken: 'accessToken',
        memberId: 1,
        email: 'skrr@email.com',
      })
    );
  }),
];
