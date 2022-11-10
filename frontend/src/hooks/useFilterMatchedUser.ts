import { assemble, disassemble } from 'hangul-js';
import { useMemo } from 'react';
import { UserProfile } from '../types/user';

const findMatches = (keyword, users) =>
  users
    ?.filter(word => {
      const regex = new RegExp(keyword, 'gi');
      return word.name.match(regex);
    })
    .map(user => ({ ...user, name: assemble(user.name) }));

const useFilterMatchedUser = (keyword, users: UserProfile[]): UserProfile[] => {
  const parsedKeyword = disassemble(keyword).join('');
  const parsedNameUsers = useMemo(
    () =>
      users?.map(user => ({
        ...user,
        name: disassemble(user.name as string).join(''),
      })),
    [users?.length]
  );
  const matchedUsers = findMatches(parsedKeyword, parsedNameUsers);

  return matchedUsers;
};

export default useFilterMatchedUser;
