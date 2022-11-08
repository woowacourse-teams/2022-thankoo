import styled from '@emotion/styled';
import Avatar from '../../../components/Avatar';
import { ListRow } from '../../../components/ListRow';
import { UserProfile } from '../../../types/user';
import useListViewUsers from '../hooks/useListViewUsers';
import CheckIcon from '@mui/icons-material/Check';

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
        <ListRow
          left={<Avatar src={user.imageUrl} alt={user.name} />}
          center={
            <ListRow.Text2Rows
              top={user.name}
              topProps={{ color: 'white', fontSize: '1.5rem' }}
              bottom={user.email}
              bottomProps={{ color: '#8e8e8e' }}
            />
          }
          right={
            <S.CheckBox isChecked={isCheckedUser(user)}>
              <S.CheckboxIcon />
            </S.CheckBox>
          }
          css={{ color: 'white', cursor: 'pointer' }}
          onClick={() => onClickUser(user)}
        />
      ))}
    </S.Container>
  );
};

export default ListViewUsers;

type CheckBoxProp = { isChecked: boolean };

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
  CheckBox: styled.div<CheckBoxProp>`
    justify-self: end;
    margin-right: 5px;
    display: ${({ isChecked }) => (isChecked ? 'flex' : 'none')};
    align-items: center;
  `,
  CheckboxIcon: styled(CheckIcon)`
    border-radius: 50%;
    background-color: ${({ theme }) => theme.primary};
    border: 1px solid black;
    fill: white;
    padding: 1px;
  `,
};
