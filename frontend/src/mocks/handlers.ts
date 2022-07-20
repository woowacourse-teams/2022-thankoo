import { createReservationHandler } from './createReservationHandler';
import { mainPageHandler } from './mainPageHandler';
import { reservationsHanlders } from './reservationsHandlers';
import { selectReceiverPageHandlers } from './selectReceiverPageHandlers';
import { sendCouponHandler } from './sendCouponHandler';
import { signInHandler } from './signInHandler';

export const handlers = [
  ...signInHandler,
  ...mainPageHandler,
  ...selectReceiverPageHandlers,
  ...sendCouponHandler,
  ...reservationsHanlders,
  ...createReservationHandler,
];
