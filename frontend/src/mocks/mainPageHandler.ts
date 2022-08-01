import { rest } from 'msw';
import { API_PATH } from '../constants/api';
import { dummyCoupons } from './dummyData';

export const mainPageHandler = [
  rest.get(`${API_PATH.RECEIVED_COUPONS_NOT_USED}`, (req, res, ctx) => {
    return res(ctx.status(200), ctx.json(dummyCoupons.filter(coupon => coupon.status !== 'used')));
  }),
  rest.get(`${API_PATH.RECEIVED_COUPONS_USED}`, (req, res, ctx) => {
    return res(ctx.status(200), ctx.json(dummyCoupons.filter(coupon => coupon.status === 'used')));
  }),
  rest.get(`${API_PATH.COUPON_DETAIL}/:couponId`, (req, res, ctx) => {
    const { couponId } = req.params;
    const targetCoupon = dummyCoupons.find(coupon => coupon.couponId === Number(couponId));

    return res(
      ctx.status(200),
      ctx.json({
        coupon: targetCoupon,
        time: {
          meetingTime: '2022-07-31T11:41:15.756059',
          timeZone: 'asia/seoul',
        },
      })
    );
  }),
];
