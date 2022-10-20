import { useGetMembers } from '../@queries/members';
import { useGetHearts, usePostHeartMutation } from './../@queries/hearts';
import useFilterMatchedUser from './../useFilterMatchedUser';

const useHeartsMembers = (searchKeyword: string) => {
  const { data: members } = useGetMembers();
  const { data: heartHistory } = useGetHearts();
  const { mutate: postHeart } = usePostHeartMutation();

  const searchedUsers = useFilterMatchedUser(searchKeyword, members);
  const sentMembers = heartHistory?.sent!;
  const receivedMembers = heartHistory?.received!;

  const searchedUserWithState = searchedUsers?.map(user => {
    const canSend =
      !sentMembers?.some(sentHistory => sentHistory.receiverId === user.id) ||
      receivedMembers?.some(receivedHistory => receivedHistory.senderId === user.id);

    const sentCount = sentMembers?.find(sentHistory => sentHistory.receiverId === user.id)?.count;
    const lastReceived =
      sentMembers?.find(sentHistory => sentHistory.receiverId === user.id)?.modifiedAt ||
      receivedMembers?.find(receiveHistory => receiveHistory.senderId === user.id)?.modifiedAt;
    const modifiedLastReceived = lastReceived?.split(' ')[0];
    const receivedUserCount = receivedMembers?.find(
      receiveHistory => receiveHistory.senderId === user.id
    )?.count;

    const count = sentCount || receivedUserCount || 0;

    return {
      user,
      sentCount,
      modifiedLastReceived,
      receivedUserCount,
      canSend,
      count,
    };
  });

  searchedUserWithState.sort((a, b) => b?.count - a?.count);

  return { searchedUserWithState, postHeart };
};

export default useHeartsMembers;
