import { rest } from 'msw';
import { BASE_URL } from '../constants';
import { dummyCoupons } from './dummyData';

export const requestedCouponsHanlders = [
  rest.get(`${BASE_URL}/api/reservations`, (req, res, ctx) => {
    return res(ctx.status(200), ctx.json(dummyCoupons.splice(4)));
  }),
];
