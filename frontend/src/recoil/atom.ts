import { atom } from 'recoil';
import { UserProfile } from '../types';

export const checkedUsersAtom = atom<UserProfile[]>({
  key: 'checkedUsersAtom',
  default: [],
});

export const authAtom = atom({
  key: 'authAtom',
  default: { accessToken: '', memberId: 0, name: '' },
});
