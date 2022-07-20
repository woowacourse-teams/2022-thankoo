import { rest } from 'msw';
import { API_PATH } from '../constants/api';
import { dummyCoupons } from './dummyData';

export const reservationsHanlders = [
  rest.get(`${API_PATH.RESERVATIONS}`, (req, res, ctx) => {
    return res(ctx.status(200), ctx.json(dummyCoupons));
  }),
];
