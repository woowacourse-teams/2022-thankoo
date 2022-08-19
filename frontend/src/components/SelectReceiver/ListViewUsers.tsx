import styled from '@emotion/styled';
import { useRecoilValue } from 'recoil';
import { checkedUsersAtom } from '../../recoil/atom';
import { UserProfile } from '../../types';
import ListViewUser from './ListViewUser';

const ListViewUsers = ({
  users,
  onClickUser,
  isCheckedUser,
}: {
  users: UserProfile[];
  onClickUser: (user: UserProfile) => void;
  isCheckedUser: (user: UserProfile) => boolean;
}) => {
  const checkedUsers = useRecoilValue(checkedUsersAtom);

  return (
    <S.Container>
      {users?.map(user => (
        <ListViewUser
          key={user.id}
          user={user}
          onClickUser={onClickUser}
          isCheckedUser={isCheckedUser}
        />
      ))}
      {checkedUsers.length > 0 && (
        <div>
          <br />
          <br />
          <br />
        </div>
      )}
    </S.Container>
  );
};

export default ListViewUsers;

const S = {
  Container: styled.div`
    display: flex;
    flex-direction: column;
    gap: 20px;
    margin-top: 7px;
    overflow-y: auto;
    padding-left: 4px;
    height: fit-content;
    max-height: calc(100% - 7.8rem);

    &::-webkit-scrollbar {
      width: 2px;
      background-color: transparent;
    }
    &::-webkit-scrollbar-thumb {
      background-color: transparent;
      border-radius: 5px;
    }

    &:hover {
      overflow-y: auto;

      &::-webkit-scrollbar {
        width: 2px;
        background-color: transparent;
      }
      &::-webkit-scrollbar-thumb {
        background-color: ${({ theme }) => theme.page.color};
        border-radius: 5px;
      }
    }
  `,
};
