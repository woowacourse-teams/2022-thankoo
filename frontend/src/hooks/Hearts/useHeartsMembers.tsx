import { useGetHearts } from '../@queries/hearts';
import { useGetMembers } from '../@queries/members';
import { usePostHeartMutation } from './../@queries/hearts';
import useFilterMatchedUser from './../useFilterMatchedUser';

const useHeartsMembers = (searchKeyword: string) => {
  const { data: members } = useGetMembers(); //userList 전체 받아오기
  const { data: heartHistory } = useGetHearts(); //
  const { mutate: postHeart } = usePostHeartMutation();

  const matchedUsers = useFilterMatchedUser(searchKeyword, members);
  const sentMembers = heartHistory?.sent!;
  const receivedMembers = heartHistory?.received!;

  return { matchedUsers, sentMembers, receivedMembers, postHeart };
};

export default useHeartsMembers;
