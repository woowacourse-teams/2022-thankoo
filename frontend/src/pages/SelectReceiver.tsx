import ArrowBackButton from '../components/shared/ArrowBackButton';
import styled from '@emotion/styled';
import useSelectReceiver from '../hooks/SelectReceiver/useSelectReceiver';
import UserSearchInput from '../components/SelectReceiver/UserSearchInput';
import ListViewUsers from '../components/SelectReceiver/ListViewUsers';
import CheckedUsers from '../components/SelectReceiver/CheckedUsers';
import ArrowForwardIosIcon from '@mui/icons-material/ArrowForwardIos';
import { Link } from 'react-router-dom';
import { css } from '@emotion/react';

import PageLayout from '../components/shared/PageLayout';
import Header from '../components/shared/Header';
import HeaderText from '../components/shared/HeaderText';

const SelectReceiver = () => {
  const { users, isLoading, error, checkedUsers, toggleUser, uncheckUser, isCheckedUser } =
    useSelectReceiver();

  return (
    <PageLayout>
      <Header>
        <Link to='/'>
          <ArrowBackButton />
        </Link>
        <HeaderText>누구한테 보낼까요?</HeaderText>
      </Header>
      <S.Body>
        {checkedUsers.length !== 0 && (
          <CheckedUsers checkedUsers={checkedUsers} onClickDelete={uncheckUser} />
        )}
        <UserSearchInput />
        {users && (
          <ListViewUsers users={users} isCheckedUser={isCheckedUser} onClickUser={toggleUser} />
        )}
      </S.Body>

      <S.SendButtonBox>
        <S.LongButton
          to={checkedUsers.length ? '/enter-coupon' : '#'}
          disabled={!checkedUsers.length}
        >
          다 고르셨나요?
          <ArrowForwardIosIcon />
        </S.LongButton>
      </S.SendButtonBox>
    </PageLayout>
  );
};

type ButtonProps = {
  disabled: boolean;
};

const S = {
  Body: styled.div`
    display: flex;
    flex-direction: column;
    gap: 1rem;
    padding: 15px 3vw;
    color: white;
    height: 82vh;
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
  SendButtonBox: styled.div`
    width: 100%;
    display: flex;
    justify-content: center;
    align-items: center;
  `,
  LongButton: styled(Link)<ButtonProps>`
    position: fixed;
    bottom: 5%;
    width: 100%;
    max-width: 80vw;
    transition: all ease-in-out 0.1s;
    ${({ disabled }) =>
      disabled
        ? css`
            background-color: #838383;
            color: lightgray;
            cursor: not-allowed;
          `
        : css`
            background-color: #ff6450;
            color: white;
          `}
    border: none;
    border-radius: 30px;
    font-size: 18px;
    padding: 12px 20px;
    display: flex;
    justify-content: space-between;
    align-items: center;
  `,
};

export default SelectReceiver;
