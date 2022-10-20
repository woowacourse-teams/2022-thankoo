import { useGetMembers } from '../@queries/members';
import useFilterMatchedUser from './../useFilterMatchedUser';

const useListViewUsers = (searchKeyword: string) => {
  const { data: members } = useGetMembers();

  const matchedUsers = useFilterMatchedUser(searchKeyword, members);
  matchedUsers.sort(function (a, b) {
    return a['name'].localeCompare(b['name']);
  });

  return { matchedUsers };
};

export default useListViewUsers;
