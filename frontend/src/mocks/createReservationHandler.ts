import { rest } from 'msw';
import { BASE_URL } from '../constants/api';

export const createReservationHandler = [
  rest.post(`${BASE_URL}/api/reservations`, (req, res, ctx) => {
    const { couponId, startAt } = req.body as { couponId: number; startAt: string };

    return res(ctx.status(200), ctx.json({ couponId: couponId, startAt: startAt }));
  }),
];
