import { rest } from 'msw';
import { API_PATH } from '../constants/api';

export const createReservationHandler = [
  rest.post(`${API_PATH.RESERVATIONS}`, (req, res, ctx) => {
    const { couponId, startAt } = req.body as { couponId: number; startAt: string };

    return res(ctx.status(200), ctx.json({ couponId: couponId, startAt: startAt }));
  }),
];
