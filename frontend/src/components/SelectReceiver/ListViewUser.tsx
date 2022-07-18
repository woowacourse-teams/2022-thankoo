import styled from '@emotion/styled';
import CheckIcon from '@mui/icons-material/Check';
import { UserProfile } from '../../types';

const ListViewUser = ({
  user,
  onClickUser,
  isCheckedUser,
}: {
  user: UserProfile;
  onClickUser: (user: UserProfile) => void;
  isCheckedUser: (user: UserProfile) => boolean;
}) => {
  return (
    <S.Container
      onClick={() => {
        onClickUser(user);
      }}
    >
      <S.UserImage src={user.imageUrl} />
      <S.UserName>{user.name}</S.UserName>
      <S.UserSubName>{user.socialNickname}</S.UserSubName>

      <S.Checkbox isChecked={isCheckedUser(user)} />
    </S.Container>
  );
};

export default ListViewUser;

type CheckBoxProp = { isChecked: boolean };

const S = {
  Container: styled.div`
    display: grid;
    grid-template-areas:
      'ui un cb'
      'ui sn cb';
    grid-template-columns: 17% 63% 20%;
    gap: 2px 0;
    cursor: pointer;
  `,
  UserImage: styled.img`
    grid-area: ui;
    width: 2rem;
    height: 2rem;
    border-radius: 50%;
    object-fit: cover;
  `,
  UserName: styled.div`
    grid-area: un;
    font-size: 20px;
    color: ${({ theme }) => theme.page.color};
  `,
  UserSubName: styled.div`
    grid-area: sn;
    font-size: 15px;
    color: ${({ theme }) => theme.page.subColor};
  `,
  Checkbox: styled(CheckIcon)<CheckBoxProp>`
    grid-area: cb;
    justify-self: end;
    margin-right: 5px;
    border-radius: 50%;
    background-color: ${({ theme }) => theme.primary};
    border: 1px solid black;
    fill: white;
    padding: 1px;
    display: ${({ isChecked }) => (isChecked ? 'inline-block' : 'none')};
  `,
};
