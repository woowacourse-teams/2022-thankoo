import { useGetMembers } from '../@queries/members';
import useFilterMatchedUser from './../useFilterMatchedUser';

const useListViewUsers = (searchKeyword: string) => {
  const { data: members } = useGetMembers();

  const matchedUsers = useFilterMatchedUser(searchKeyword, members);

  return { matchedUsers };
};

export default useListViewUsers;
