import { authPageHandler } from './authPageHandler';
import { mainPageHandler } from './mainPageHandler';
import { selectReceiverPageHandlers } from './selectReceiverPageHandlers';

export const handlers = [...mainPageHandler, ...selectReceiverPageHandlers, ...authPageHandler];
