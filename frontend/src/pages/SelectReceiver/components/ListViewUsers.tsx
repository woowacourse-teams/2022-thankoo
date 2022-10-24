import styled from '@emotion/styled';
import { UserProfile } from '../../../types/user';
import useListViewUsers from '../hook/useListViewUsers';
import ListViewUser from './ListViewUser';

const ListViewUsers = ({
  onClickUser,
  isCheckedUser,
  searchKeyword,
}: {
  onClickUser: (user: UserProfile) => void;
  isCheckedUser: (user: UserProfile) => boolean;
  searchKeyword: string;
}) => {
  const { matchedUsers } = useListViewUsers(searchKeyword);

  return (
    <S.Container>
      {matchedUsers.map(user => (
        <ListViewUser
          key={user.id}
          user={user}
          onClickUser={onClickUser}
          isCheckedUser={isCheckedUser}
        />
      ))}
    </S.Container>
  );
};

export default ListViewUsers;

const S = {
  Container: styled.div`
    display: flex;
    flex-direction: column;
    gap: 20px;
    margin: 7px 0;
    overflow-y: auto;
    padding-left: 4px;

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
