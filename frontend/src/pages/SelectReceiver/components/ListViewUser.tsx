import styled from '@emotion/styled';
import CheckIcon from '@mui/icons-material/Check';
import { BASE_URL } from '../../../constants/api';
import { UserProfile } from '../../../types/user';
import { palette } from './../../../styles/ThemeProvider';

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
      <S.UserImage src={`${BASE_URL}${user.imageUrl}`} />
      <S.UserName>{user.name}</S.UserName>
      <S.UserSubName>{user.email}</S.UserSubName>

      <S.CheckBox isChecked={isCheckedUser(user)}>
        <S.CheckboxIcon />
      </S.CheckBox>
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
    width: 3.2rem;
    height: 3.2rem;
    border-radius: 50%;
    object-fit: cover;
  `,
  UserName: styled.div`
    grid-area: un;
    font-size: 1.5rem;
    color: ${({ theme }) => theme.page.color};
  `,
  UserSubName: styled.div`
    grid-area: sn;
    font-size: 1.1rem;
    color: ${({ theme }) => theme.page.subColor};
  `,
  CheckBox: styled.div<CheckBoxProp>`
    grid-area: cb;
    justify-self: end;
    margin-right: 5px;
    display: ${({ isChecked }) => (isChecked ? 'flex' : 'none')};
    align-items: center;
  `,
  CheckboxIcon: styled(CheckIcon)`
    border-radius: 50%;
    background-color: ${({ theme }) => theme.primary};
    border: 1px solid ${palette.BLACK};
    fill: ${palette.WHITE};
    padding: 1px;
  `,
};
