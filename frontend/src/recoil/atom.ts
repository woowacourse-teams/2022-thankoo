import { ReactNode } from 'react';
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
