import { mainPageHandler } from './mainPageHandler';
import { requestedCouponsHanlders } from './requestedCouponsHandlers';
import { selectReceiverPageHandlers } from './selectReceiverPageHandlers';

export const handlers = [
  ...mainPageHandler,
  ...selectReceiverPageHandlers,
  ...requestedCouponsHanlders,
];
