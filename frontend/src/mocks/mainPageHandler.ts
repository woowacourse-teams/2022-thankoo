import { rest } from 'msw';
import { dummyCoupons } from './dummyData';

export const mainPageHandler = [
  rest.get('http://localhost:3000/api/members/me/received-coupons', (req, res, ctx) => {
    return res(ctx.status(200), ctx.json(dummyCoupons));
  }),
];
