import { useGetHearts } from '../@queries/hearts';
import { useGetMembers } from '../@queries/members';
import { usePostHeartMutation } from './../@queries/hearts';
import useFilterMatchedUser from './../useFilterMatchedUser';

const useHeartsMembers = (searchKeyword: string) => {
  const { data: members } = useGetMembers(); //userList 전체 받아오기
  const { data: heartHistory } = useGetHearts(); //
  const { mutate: postHeart } = usePostHeartMutation();

  const searchedUsers = useFilterMatchedUser(searchKeyword, members);
  const sentMembers = heartHistory?.sent!;
  const receivedMembers = heartHistory?.received!;

  const searchedUserWithState = searchedUsers?.map(user => {
    const canSend =
      !sentMembers?.some(sentHistory => sentHistory.receiverId === user.id) ||
      receivedMembers?.some(receivedHistory => receivedHistory.senderId === user.id);

    const count = sentMembers?.find(sentHistory => sentHistory.receiverId === user.id)?.count;
    const lastReceived =
      sentMembers?.find(sentHistory => sentHistory.receiverId === user.id)?.modifiedAt ||
      receivedMembers?.find(receiveHistory => receiveHistory.senderId === user.id)?.modifiedAt;
    const modifiedLastReceived = lastReceived?.split(' ')[0];
    const receivedUserCount = receivedMembers?.find(
      receiveHistory => receiveHistory.senderId === user.id
    )?.count;
    return {
      user: user,
      canSend: canSend,
      count: count,
      modifiedLastReceived: modifiedLastReceived,
      receivedUserCount: receivedUserCount,
    };
  });

  searchedUserWithState.sort((a, b) => b?.count - a?.count);

  return { searchedUserWithState, postHeart };
};

export default useHeartsMembers;
