import ArrowBackButton from '../commons/Header/ArrowBackButton';
import styled from '@emotion/styled';
import useSelectReceiver from '../hooks/SelectReceiver/useSelectReceiver';
import UserSearchInput from '../commons/SelectReceiver/UserSearchInput';
import ListViewUsers from '../commons/SelectReceiver/ListViewUsers';
import CheckedUsers from '../commons/SelectReceiver/CheckedUsers';
import ArrowForwardIosIcon from '@mui/icons-material/ArrowForwardIos';
import { Link } from 'react-router-dom';

const SelectReceiver = () => {
  const { users, isLoading, error, checkedUsers, toggleUser, uncheckUser, isCheckedUser } =
    useSelectReceiver();

  return (
    <S.Container>
      <S.Header>
        <Link to='/'>
          <ArrowBackButton />
        </Link>
        <S.HeaderText>누구한테 보낼까요?</S.HeaderText>
      </S.Header>
      <S.Body>
        {checkedUsers && <CheckedUsers checkedUsers={checkedUsers} onClickDelete={uncheckUser} />}
        <UserSearchInput />
        {users && (
          <ListViewUsers users={users} isCheckedUser={isCheckedUser} onClickUser={toggleUser} />
        )}
      </S.Body>
      <S.LongButton>
        <Link to='/enter-coupon'>다 고르셨나요?</Link>
        <ArrowForwardIosIcon />
      </S.LongButton>
    </S.Container>
  );
};

const S = {
  Container: styled.div`
    display: flex;
    flex-direction: column;
    padding: 5px;
  `,
  Header: styled.header`
    display: flex;
    flex-direction: column;
    align-items: flex-start;
    gap: 15px;
    color: white;
    margin: 10px 0 0 2vw;
  `,
  HeaderText: styled.p`
    font-size: 26px;
    margin-left: calc(1vw + 6px);
  `,
  Body: styled.div`
    display: flex;
    flex-direction: column;
    gap: 1rem;
    padding: 15px 3vw;
    color: white;
    height: 70vh;
  `,
  UsersImages: styled.div`
    display: flex;
    gap: 10px;

    overflow: auto hidden;

    ::-webkit-scrollbar {
      overflow-x: hidden;
      background-color: transparent;
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
  `,
  UserName: styled.span`
    font-size: 15px;
  `,
  LongButton: styled.button`
    background-color: #8e8e8e;
    border: none;
    color: white;
    border-radius: 30px;
    font-size: 20px;
    margin: 0 3vw;
    padding: 10px 20px;
    display: flex;
    justify-content: space-between;
    align-items: center;
  `,
};

export default SelectReceiver;
