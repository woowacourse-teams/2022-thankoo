import { useMemo } from 'react';
import { YYYYMMDD } from 'thankoo-utils-type';
import { useGetHearts } from '../../../hooks/@queries/hearts';
import { useGetMembers } from '../../../hooks/@queries/members';
import { UserProfile } from '../../../types/user';
import { sorted } from '../../../utils';

import useFilterMatchedUser from '../../../hooks/useFilterMatchedUser';

export type Hearts = {
  canSend: boolean;
  modifiedLastReceived: YYYYMMDD;
  sentCount: number;
  user: UserProfile;
};

const useHeartsMembers = (searchKeyword: string) => {
  const { data: members } = useGetMembers();
  const { data: heartHistory } = useGetHearts();

  const searchedUsers = useFilterMatchedUser(searchKeyword, members || []);
  const sentMembers = heartHistory?.sent!;
  const receivedMembers = heartHistory?.received!;

  const searchedUserWithState = searchedUsers?.map(user => {
    const canSend =
      !sentMembers?.some(sentHistory => sentHistory.receiverId === user.id) ||
      receivedMembers?.some(receivedHistory => receivedHistory.senderId === user.id);

    const sentCount =
      sentMembers?.find(sentHistory => sentHistory.receiverId === user.id)?.count || 0;
    const receivedUserCount =
      receivedMembers?.find(receiveHistory => receiveHistory.senderId === user.id)?.count || 0;

    const lastReceived =
      sentMembers?.find(sentHistory => sentHistory.receiverId === user.id)?.modifiedAt ||
      receivedMembers?.find(receiveHistory => receiveHistory.senderId === user.id)?.modifiedAt;
    const modifiedLastReceived = lastReceived?.split(' ')[0];

    const count = Math.max(sentCount, receivedUserCount);

    return {
      user,
      sentCount: count,
      modifiedLastReceived,
      receivedUserCount,
      canSend,
      last: receivedUserCount - sentCount,
    };
  });

  const hearts: Hearts[] = useMemo(
    () =>
      sorted(searchedUserWithState, (a, b) => {
        if (b?.sentCount !== a?.sentCount) return b?.sentCount - a?.sentCount;
        else if (b?.sentCount === a?.sentCount) {
          return b?.last - a?.last;
        }
      }),
    [searchKeyword]
  );

  return { searchedUserWithState: hearts };
};

export default useHeartsMembers;
