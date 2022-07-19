import { rest } from 'msw';
import { BASE_URL } from '../constants/api';
import { dummyCoupons } from './dummyData';

export const mainPageHandler = [
  rest.get(`${BASE_URL}/api/coupons/received`, (req, res, ctx) => {
    return res(ctx.status(200), ctx.json(dummyCoupons));
  }),
];
