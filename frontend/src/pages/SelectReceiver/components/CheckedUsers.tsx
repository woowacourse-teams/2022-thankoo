import styled from '@emotion/styled';
import Avatar from '../../../components/Avatar';
import { UserProfile } from '../../../types/user';

const CheckedUsers = ({
  checkedUsers,
  onClickDelete,
}: {
  checkedUsers: UserProfile[];
  onClickDelete: (user: UserProfile) => void;
}) => {
  return (
    <S.Container hasCheckedUser={!!checkedUsers.length}>
      {checkedUsers?.map(user => (
        <S.User
          key={user.id}
          onClick={() => {
            onClickDelete(user);
          }}
        >
          <Avatar src={user.imageUrl} alt={user.name} />
          <S.UserName>{user.name}</S.UserName>
        </S.User>
      ))}
    </S.Container>
  );
};

type ContainerStyleProp = {
  hasCheckedUser: boolean;
};

const S = {
  Container: styled.div<ContainerStyleProp>`
    display: flex;
    height: ${({ hasCheckedUser }) => (hasCheckedUser ? '6rem' : 0)};
    gap: 15px;
    padding-top: 3px;
    overflow: scroll;
    transition: all ease-in-out 0.1s;

    ::-webkit-scrollbar {
      display: none;
    }
  `,
  UserImage: styled.img`
    width: 3.5rem;
    height: 3.5rem;
    border-radius: 50%;
    object-fit: cover;
  `,
  User: styled.div`
    display: flex;
    height: 100%;
    flex-direction: column;
    gap: 5px;
    align-items: center;
    cursor: pointer;
  `,
  UserName: styled.span`
    font-size: 1.5rem;
    color: ${({ theme }) => theme.page.color};
    word-break: keep-all;
  `,
};
export default CheckedUsers;
