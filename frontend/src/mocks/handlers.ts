import { createReservationHandler } from './createReservationHandler';
import { mainPageHandler } from './mainPageHandler';
import { profileHandler } from './profileHandlers';
import { reservationsHanlders } from './reservationsHandlers';
import { selectReceiverPageHandlers } from './selectReceiverPageHandlers';
import { signInHandler } from './signInHandler';

export const handlers = [
  ...signInHandler,
  ...mainPageHandler,
  ...selectReceiverPageHandlers,
  ...reservationsHanlders,
  ...createReservationHandler,
  ...profileHandler,
];
