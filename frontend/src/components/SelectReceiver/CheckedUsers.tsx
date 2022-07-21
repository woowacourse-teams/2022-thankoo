import styled from '@emotion/styled';
import { UserProfile } from '../../types';

const CheckedUsers = ({
  checkedUsers,
  onClickDelete,
}: {
  checkedUsers: UserProfile[];
  onClickDelete: (user: UserProfile) => void;
}) => {
  return (
    <S.Container>
      {checkedUsers?.map(user => (
        <S.User
          key={user.id}
          onClick={() => {
            onClickDelete(user);
          }}
        >
          <S.UserImage src={user.imageUrl} />
          <S.UserName>{user.name}</S.UserName>
        </S.User>
      ))}
    </S.Container>
  );
};

const S = {
  Container: styled.div`
    display: flex;
    gap: 10px;
    padding-top: 3px;
    overflow: scroll hidden;

    ::-webkit-scrollbar {
      display: none;
    }
  `,
  UserImage: styled.img`
    width: 2.5rem;
    height: 2.5rem;
    border-radius: 50%;
    object-fit: cover;
  `,
  User: styled.div`
    display: flex;
    flex-direction: column;
    gap: 5px;
    align-items: center;
    cursor: pointer;
  `,
  UserName: styled.span`
    font-size: 15px;
    color: ${({ theme }) => theme.page.color};
  `,
};
export default CheckedUsers;
