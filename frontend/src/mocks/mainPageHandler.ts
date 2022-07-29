import { rest } from 'msw';
import { API_PATH } from '../constants/api';
import { dummyCoupons } from './dummyData';

export const mainPageHandler = [
  rest.get(`${API_PATH.RECEIVED_COUPONS_NOT_USED}`, (req, res, ctx) => {
    return res(ctx.status(200), ctx.json([]));
  }),
];
