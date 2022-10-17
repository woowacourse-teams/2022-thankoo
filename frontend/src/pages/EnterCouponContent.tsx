import { css } from '@emotion/react';
import styled from '@emotion/styled';
import ArrowForwardIosIcon from '@mui/icons-material/ArrowForwardIos';
import { Link } from 'react-router-dom';
import ArrowBackButton from '../components/@shared/ArrowBackButton';
import TabsNav from '../components/@shared/TabsNav';
import useEnterCouponContent from '../hooks/EnterCouponContent/useEnterCouponContent';

import CouponLayout from '../components/@shared/CouponLayout';
import Header from '../layout/Header';
import PageLayout from '../layout/PageLayout';
import ConfirmCouponContentModal from '../components/EnterCouponContent/ConfirmCouponContentModal';
import useModal from '../hooks/useModal';
import HeaderText from '../layout/HeaderText';
import { couponTypeKeys, couponTypes } from '../types/coupon';
import LongButton from '../components/@shared/LongButton';
import ModalButton from '../components/@shared/ModalButton';

const couponTypesWithoutEntire = couponTypeKeys.filter(type => type !== 'entire');

const EnterCouponContent = () => {
  const {
    couponType,
    setCouponType,
    title,
    message,
    isFilled,
    checkedUsers,
    currentUserId,
    currentUserName,
    sendCoupon,
    handleOnchangeMessage,
    handleOnchangeTitle,
  } = useEnterCouponContent();
  const { setModalContent, show } = useModal();

  return (
    <PageLayout>
      <S.Header>
        <Link to='/select-receiver'>
          <ArrowBackButton />
        </Link>
        <HeaderText>어떤 쿠폰을 보낼까요?</HeaderText>
      </S.Header>
      <S.Body>
        <TabsNav
          onChangeTab={setCouponType}
          currentTab={couponType}
          tabList={couponTypes}
          selectableTabs={couponTypesWithoutEntire}
        />
        <S.CouponBox>
          <CouponLayout
            couponType={couponType}
            id={currentUserId}
            name={currentUserName}
            title={title}
          />
        </S.CouponBox>
        <S.Form>
          <S.TitleInput
            onChange={handleOnchangeTitle}
            value={title}
            type='text'
            placeholder='제목을 입력해주세요'
          />
          <S.MessageTextarea
            onChange={handleOnchangeMessage}
            value={message}
            placeholder='메세지를 작성해보세요'
            maxLength={100}
          />
        </S.Form>
      </S.Body>
      <S.ButtonWrapper>
        <ModalButton
          inner={
            <ConfirmCouponContentModal
              title={title}
              message={message}
              receivers={checkedUsers}
              submit={sendCoupon}
              couponType={couponType}
            />
          }
        >
          <LongButton isDisabled={!isFilled}>
            {checkedUsers.length}명에게 쿠폰 전송하기
            <ArrowForwardIosIcon />
          </LongButton>
        </ModalButton>
      </S.ButtonWrapper>
    </PageLayout>
  );
};

const S = {
  Header: styled(Header)`
    height: 10%;
  `,
  Body: styled.div`
    display: flex;
    flex-direction: column;
    gap: 2rem;
    padding: 15px 3vw;
    height: 70vh;
    justify-content: space-evenly;
  `,
  Form: styled.form`
    display: flex;
    flex-direction: column;
    gap: 10px;
  `,
  TitleInput: styled.input`
    border: none;
    padding: 5px;
    font-size: 18px;
    background-color: ${({ theme }) => theme.input.background};
    color: ${({ theme }) => theme.input.color};
    border-radius: 5px;
    padding: 10px;

    &::placeholder {
      color: ${({ theme }) => theme.input.placeholder};
    }
    &:focus {
      outline: ${({ theme }) => `3px solid ${theme.primary}`};
    }
  `,
  MessageTextarea: styled.textarea`
    resize: none;
    height: 10vh;
    border: none;
    padding: 5px;
    font-size: 18px;
    background-color: ${({ theme }) => theme.input.background};
    color: ${({ theme }) => theme.input.color};
    border-radius: 5px;
    padding: 10px;

    &::placeholder {
      font-size: 18px;
      color: ${({ theme }) => theme.input.placeholder};
    }
    &:focus {
      outline: ${({ theme }) => `3px solid ${theme.primary}`};
    }
  `,
  CouponBox: styled.div`
    margin: 0 auto;
  `,
  ButtonWrapper: styled.div`
    margin: 0 1rem;
  `,
};

export default EnterCouponContent;
