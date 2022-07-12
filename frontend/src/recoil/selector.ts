import { selector } from 'recoil';
import { checkedUsersAtom } from './atom';

export const checkedUsersState = selector({
  key: 'checkedUsersState',
  get: ({ get }) => get(checkedUsersAtom),
});
