import { ReactNode } from 'react';
import { atom } from 'recoil';
import { UserProfile } from '../types';

export const checkedUsersAtom = atom<UserProfile[]>({
  key: 'checkedUsersAtom',
  default: [],
});

export const themeAtom = atom({
  key: 'themeAtom',
  default: 'dark',
});

export const modalVisibleAtom = atom({
  key: 'modalVisibleAtom',
  default: false,
});

export const modalContentAtom = atom<any>({
  key: 'modalContentAtom',
  default: null,
});
