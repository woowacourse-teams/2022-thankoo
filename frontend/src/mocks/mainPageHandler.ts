import { rest } from 'msw';
import { dummyCoupons } from './dummyData';

const BASE_URL = 'http://localhost:3000';

export const mainPageHandler = [
  rest.get(`${BASE_URL}/api/members/me/received-coupons`, (req, res, ctx) => {
    return res(ctx.status(200), ctx.json(dummyCoupons));
  }),
];
