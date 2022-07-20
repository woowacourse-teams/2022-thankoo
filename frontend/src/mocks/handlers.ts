import { createReservationHandler } from './createReservationHandler';
import { mainPageHandler } from './mainPageHandler';
import { requestedCouponsHanlders } from './requestedCouponsHandlers';
import { selectReceiverPageHandlers } from './selectReceiverPageHandlers';
import { sendCouponHandler } from './sendCouponHandler';
import { signInHandler } from './signInHandler';

export const handlers = [
  ...signInHandler,
  ...mainPageHandler,
  ...selectReceiverPageHandlers,
  ...sendCouponHandler,
  ...requestedCouponsHanlders,
  ...createReservationHandler,
];
