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
