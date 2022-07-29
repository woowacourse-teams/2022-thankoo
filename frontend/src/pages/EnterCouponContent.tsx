import { css } from '@emotion/react';
import styled from '@emotion/styled';
import ArrowForwardIosIcon from '@mui/icons-material/ArrowForwardIos';
import { Link } from 'react-router-dom';
import ArrowBackButton from '../components/@shared/ArrowBackButton';
import TabsNav from '../components/@shared/TabsNav';
import GridViewCoupon from '../components/Main/GridViewCoupon';
import useEnterCouponContent from '../hooks/EnterCouponContent/useEnterCouponContent';
import { couponTypeKeys } from '../types';

import Header from '../components/@shared/Header';
import HeaderText from '../components/@shared/HeaderText';
import PageLayout from '../components/@shared/PageLayout';
import { couponTypes } from '../types/index';

const couponTypesWithoutEntire = couponTypeKeys.filter(type => type !== 'entire');

const EnterCouponContent = () => {
  const {
    couponType,
    setCouponType,
    title,
    message,
    setTitle,
    setMessage,
    isFilled,
    sendCoupon,
    checkedUsers,
    currentCoupon,
  } = useEnterCouponContent();

  return (
    <PageLayout>
      <Header>
        <Link to='/select-receiver'>
          <ArrowBackButton />
        </Link>
        <HeaderText>어떤 쿠폰을 보낼까요?</HeaderText>
      </Header>
      <S.Body>
        <TabsNav
          onChangeTab={setCouponType}
          currentTab={couponType}
          tabList={couponTypes}
          selectableTabs={couponTypesWithoutEntire}
        />
        <S.CouponBox>
          <GridViewCoupon coupon={currentCoupon} />
        </S.CouponBox>
        <S.Form>
          <S.TitleInput
            onChange={e => setTitle(e.target.value)}
            value={title}
            type='text'
            placeholder='제목을 입력해주세요'
          />
          <S.MessageTextarea
            onChange={e => setMessage(e.target.value)}
            value={message}
            maxLength={100}
            placeholder='메세지를 작성해보세요'
          ></S.MessageTextarea>
        </S.Form>
      </S.Body>
      <S.LongButton
        onClick={() => {
          sendCoupon.mutate();
        }}
        disabled={!isFilled}
      >
        {checkedUsers.length}명에게 쿠폰 전송하기
        <ArrowForwardIosIcon />
      </S.LongButton>
    </PageLayout>
  );
};

const S = {
  Body: styled.div`
    display: flex;
    flex-direction: column;
    gap: 2rem;
    padding: 15px 3vw;
    height: 70vh;
    justify-content: center;
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
      color: ${({ theme }) => theme.input.placeholder};
    }
    &:focus {
      outline: ${({ theme }) => `3px solid ${theme.primary}`};
    }
  `,
  LongButton: styled.button`
    border: none;
    border-radius: 30px;
    font-size: 18px;
    margin: 0 3vw;
    padding: 10px 20px;
    display: flex;
    justify-content: space-between;
    align-items: center;
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
  `,
  CouponBox: styled.div`
    margin: 0 auto;
  `,
};

export default EnterCouponContent;
