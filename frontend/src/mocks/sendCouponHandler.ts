import { rest } from 'msw';
import { API_PATH } from '../constants/api';
import { users } from './dummyData';

export const sendCouponHandler = [
  rest.get(`${API_PATH.PROFILE}`, (req, res, ctx) => {
    const tempUserProfile = users[1];

    return res(ctx.status(200), ctx.json(tempUserProfile));
  }),
];
