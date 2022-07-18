import styled from '@emotion/styled';
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
    padding-bottom: 20vh;

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
