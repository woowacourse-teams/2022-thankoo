import { createReservationHandler } from './createReservationHandler';
import { mainPageHandler } from './mainPageHandler';
import { selectReceiverPageHandlers } from './selectReceiverPageHandlers';

export const handlers = [
  ...mainPageHandler,
  ...selectReceiverPageHandlers,
  ...createReservationHandler,
];
