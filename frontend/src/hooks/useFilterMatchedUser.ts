import { assemble, disassemble } from 'hangul-js';
import { useMemo } from 'react';

const findMatches = (keyword, users) =>
  users
    ?.filter(word => {
      const regex = new RegExp(keyword, 'gi');
      return word.name.match(regex);
    })
    .map(user => ({ ...user, name: assemble(user.name) }));

const useFilterMatchedUser = (keyword, users) => {
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
