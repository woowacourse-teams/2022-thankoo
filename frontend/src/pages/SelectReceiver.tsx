import { css } from '@emotion/react';
import styled from '@emotion/styled';
import ArrowForwardIosIcon from '@mui/icons-material/ArrowForwardIos';
import { Link } from 'react-router-dom';
import CheckedUsers from '../components/SelectReceiver/CheckedUsers';
import ListViewUsers from '../components/SelectReceiver/ListViewUsers';
import UserSearchInput from '../components/SelectReceiver/UserSearchInput';
import useSelectReceiver from '../hooks/SelectReceiver/useSelectReceiver';

import { ROUTE_PATH } from '../constants/routes';
import HeaderText from '../layout/HeaderText';
import MainPageLayout from '../layout/MainPageLayout';

const SelectReceiver = () => {
  const {
    members,
    isLoading,
    error,
    checkedUsers,
    toggleUser,
    uncheckUser,
    isCheckedUser,
    keyword,
    setKeyword,
    matchedUsers,
  } = useSelectReceiver();

  return (
    <MainPageLayout>
      <S.Header>누구한테 보낼까요?</S.Header>
      <S.Body>
        {checkedUsers.length !== 0 && (
          <CheckedUsers checkedUsers={checkedUsers} onClickDelete={uncheckUser} />
        )}
        <S.InputWrapper>
          <UserSearchInput value={keyword} setKeyword={setKeyword} />
        </S.InputWrapper>
        <S.Section isShowUser={!!checkedUsers?.length}>
          {members && (
            <ListViewUsers
              users={matchedUsers}
              isCheckedUser={isCheckedUser}
              onClickUser={toggleUser}
            />
          )}
          <S.SendButtonBox>
            <S.LongButton
              to={checkedUsers.length ? `${ROUTE_PATH.ENTER_COUPON_CONTENT}` : '#'}
              disabled={!checkedUsers.length}
            >
              다 고르셨나요?
              <ArrowForwardIosIcon />
            </S.LongButton>
          </S.SendButtonBox>
        </S.Section>
      </S.Body>
    </MainPageLayout>
  );
};

type ButtonProps = {
  disabled: boolean;
};

type SectionProps = {
  isShowUser: boolean;
};

const S = {
  Header: styled(HeaderText)`
    color: white;
  `,
  Body: styled.div`
    display: flex;
    flex-direction: column;
    padding: 0 0 15px;
    height: calc(100% - 6rem);
  `,
  InputWrapper: styled.div`
    margin-bottom: 15px;
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
    font-size: 1.3rem;
  `,
  SendButtonBox: styled.div`
    width: 100%;
    display: flex;
    justify-content: center;
    align-items: center;
  `,
  Section: styled.section<SectionProps>`
    display: flex;
    flex-flow: column;
    justify-content: space-between;
    ${({ isShowUser }) =>
      isShowUser
        ? css`
            height: calc(100% - 12.8rem);
          `
        : css`
            height: calc(100% - 7rem);
          `}
  `,
  LongButton: styled(Link)<ButtonProps>`
    bottom: 11%;
    width: 100%;
    max-width: 680px;
    transition: all ease-in-out 0.1s;
    ${({ disabled, theme }) =>
      disabled
        ? css`
            background-color: ${theme.button.disbaled.background};
            color: ${theme.button.disbaled.color};
            cursor: not-allowed;
          `
        : css`
            background-color: ${theme.button.active.background};
            color: ${theme.button.active.color};
          `}
    border: none;
    border-radius: 30px;
    font-size: 1.7rem;
    padding: 1rem 2rem;
    display: flex;
    justify-content: space-between;
    align-items: center;
  `,
};

export default SelectReceiver;
