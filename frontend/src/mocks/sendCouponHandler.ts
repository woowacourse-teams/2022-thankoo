import { rest } from 'msw';
import { API_PATH } from '../constants/api';

export const sendCouponHandler = [
  rest.post(`${API_PATH.SEND_COUPON}`, (req, res, ctx) => {
    return res(ctx.status(200));
  }),
];
