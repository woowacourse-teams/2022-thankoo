import { createReservationHandler } from './createReservationHandler';
import { mainPageHandler } from './mainPageHandler';
import { reservationsHanlders } from './reservationsHandlers';
import { selectReceiverPageHandlers } from './selectReceiverPageHandlers';

export const handlers = [
  ...mainPageHandler,
  ...selectReceiverPageHandlers,
  ...reservationsHanlders,
  ...createReservationHandler,
];
