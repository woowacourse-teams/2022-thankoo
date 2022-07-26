import { rest } from 'msw';
import { API_PATH } from '../constants/api';
import { users } from './dummyData';

export const profileHandler = [
  rest.get(`${API_PATH.PROFILE}`, (req, res, ctx) => {
    const tempUserProfile = users[0];

    return res(ctx.status(404), ctx.json({ errorCode: 1003 }));
  }),
  rest.put(`${API_PATH.PROFILE}`, (req, res, ctx) => {
    const { name } = req.body;
    users[0].name = name;

    return res(ctx.status(200), ctx.json(users[0]));
  }),
];
