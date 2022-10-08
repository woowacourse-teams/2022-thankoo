import styled from '@emotion/styled';
import { BASE_URL } from '../../constants/api';
import { UserProfile } from '../../types/user';

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
          <S.UserImage src={`${BASE_URL}${user.imageUrl}`} />
          <S.UserName>{user.name}</S.UserName>
        </S.User>
      ))}
    </S.Container>
  );
};

const S = {
  Container: styled.div`
    display: flex;
    height: 6rem;
    gap: 10px;
    padding-top: 3px;

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
