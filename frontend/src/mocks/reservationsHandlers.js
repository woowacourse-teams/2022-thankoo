import { rest } from 'msw';
import { API_PATH } from '../constants/api';
import { dummyCoupons } from './dummyData';

const dummyReceivedReservations = [
  {
    reservationId: 1,
    memberName: '호호',
    couponType: 'coffee',
    meetingTime: '2022-07-23T12:40:41.617785',
  },
  {
    reservationId: 2,
    memberName: '숟갈',
    couponType: 'coffee',
    meetingTime: '2022-07-23T12:40:41.617808',
  },
  {
    reservationId: 3,
    memberName: '라라',
    couponType: 'meal',
    meetingTime: '2022-07-23T12:40:41.617813',
  },
];
const dummySentReservations = [
  {
    reservationId: 4,
    memberName: '후니의 식사쿠폰',
    couponType: 'meal',
    meetingTime: '2022-07-22T12:40:00.681091',
  },
  {
    reservationId: 5,
    memberName: '호호의 카누쿠폰',
    couponType: 'coffee',
    meetingTime: '2022-07-23T12:40:41.681112',
  },
  {
    reservationId: 6,
    memberName: '호호의 카누쿠폰',
    couponType: 'coffee',
    meetingTime: '2022-07-23T12:40:41.681116',
  },
];

export const reservationsHanlders = [
  rest.get(`${API_PATH.RESERVATIONS}`, (req, res, ctx) => {
    return res(ctx.status(200), ctx.json(dummyCoupons));
  }),
  rest.post(`${API_PATH.RESERVATIONS}`, (req, res, ctx) => {
    const { couponId, startAt } = req.body;

    return res(ctx.status(200));
  }),
  rest.get(`${API_PATH.RESERVATIONS_RECEIVED}`, (req, res, ctx) => {
    return res(ctx.status(200), ctx.json(dummyReceivedReservations));
  }),
  rest.get(`${API_PATH.RESERVATIONS_SENT}`, (req, res, ctx) => {
    return res(ctx.status(200), ctx.json(dummySentReservations));
  }),
  rest.put(`${API_PATH.RESERVATIONS}/:id`, (req, res, ctx) => {
    return res(ctx.status(200), ctx.json({}));
  }),
];
