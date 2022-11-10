import { assemble, disassemble } from 'hangul-js';
import { useMemo } from 'react';
import { UserProfile } from '../types/user';

type NameParsedUserProfile = {
  name: string[];
} & Pick<UserProfile, 'email' | 'id' | 'imageUrl'>;
const findMatches = (keyword: string, users: NameParsedUserProfile[]) =>
  users
    ?.filter(user => {
      const regex = new RegExp(keyword, 'gi');
      return user.name.join('').match(regex);
    })
    .map(user => ({ ...user, name: assemble(user.name) }));

const useFilterMatchedUser = (keyword: string, users: UserProfile[]): UserProfile[] => {
  const parsedKeyword = disassemble(keyword).join('');
  const parsedNameUsers = useMemo(
    () =>
      users?.map(user => ({
        ...user,
        name: disassemble(user.name),
      })),
    [users?.length]
  );
  const matchedUsers = findMatches(parsedKeyword, parsedNameUsers);

  return matchedUsers;
};

export default useFilterMatchedUser;
