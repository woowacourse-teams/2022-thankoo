import { atom } from 'recoil';
import { UserProfile } from '../types';

//checked users
export const checkedUsersAtom = atom<UserProfile[]>({
  key: 'checkedUsersAtom',
  default: [],
});

//theme
export const themeAtom = atom({
  key: 'themeAtom',
  default: 'dark',
});

//modal
export const modalVisibleAtom = atom({
  key: 'modalVisibleAtom',
  default: false,
});

export const modalContentAtom = atom<any>({
  key: 'modalContentAtom',
  default: null,
});

export const targetCouponAtom = atom<any>({
  key: 'targetCouponAtom',
  default: null,
});

type SentOrReceivedNavType = '받은' | '보낸';
export const sentOrReceivedAtom = atom<SentOrReceivedNavType>({
  key: 'sentOrReceived',
  default: '받은',
});

//toast
export const toastVisibleAtom = atom({
  key: 'toastVisibleAtom',
  default: false,
});

interface toastItem {
  key: number;
  comment: string;
}

export const toastStackAtom = atom<toastItem[]>({
  //modify
  key: 'toastStackAtom',
  default: [],
});

// SuccessPage 내부 컨텐츠
export interface SuccessContentType {
  page: string;
  props: any;
}

export const onSuccessContentAtom = atom<SuccessContentType>({
  key: 'onSuccessContentAtom',
  default: {
    page: '',
    props: {},
  },
});

// 예약 Nav
type ReservationNavType = 'received' | 'sent';
export const ReservationNavAtom = atom<ReservationNavType>({
  key: 'reservationNavAtom',
  default: 'received',
});
