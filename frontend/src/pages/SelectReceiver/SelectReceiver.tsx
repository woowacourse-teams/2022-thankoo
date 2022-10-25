import { css } from '@emotion/react';
import styled from '@emotion/styled';
import ArrowForwardIosIcon from '@mui/icons-material/ArrowForwardIos';
import { Link } from 'react-router-dom';
import CheckedUsers from './components/CheckedUsers';

import { Suspense } from 'react';
import LongButton from '../../components/@shared/LongButton';
import { ROUTE_PATH } from '../../constants/routes';
import HeaderText from '../../layout/HeaderText';
import MainPageLayout from '../../layout/MainPageLayout';
import Spinner from '../../components/@shared/Spinner';
import CustomErrorBoundary from '../../errors/CustomErrorBoundary';
import ErrorFallBack from '../../errors/ErrorFallBack';
import useSelectReceiver from './hooks/useSelectReceiver';
import UserSearchInput from './components/UserSearchInput';
import ListViewUsers from './components/ListViewUsers';

const SelectReceiver = () => {
  const { checkedUsers, toggleUser, uncheckUser, isCheckedUser, keyword, setKeyword } =
    useSelectReceiver();

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
          <CustomErrorBoundary fallbackComponent={ErrorFallBack}>
            <Suspense fallback={<Spinner />}>
              <ListViewUsers
                searchKeyword={keyword}
                isCheckedUser={isCheckedUser}
                onClickUser={toggleUser}
              />
            </Suspense>
          </CustomErrorBoundary>
          <S.SendButtonBox>
            <S.ButtonLink to={checkedUsers.length ? `${ROUTE_PATH.ENTER_COUPON_CONTENT}` : '#'}>
              <LongButton isDisabled={!checkedUsers.length}>
                다 고르셨나요?
                <ArrowForwardIosIcon />
              </LongButton>
            </S.ButtonLink>
          </S.SendButtonBox>
        </S.Section>
      </S.Body>
    </MainPageLayout>
  );
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
  ButtonLink: styled(Link)`
    width: 100%;
    cursor: unset;
  `,
};

export default SelectReceiver;
