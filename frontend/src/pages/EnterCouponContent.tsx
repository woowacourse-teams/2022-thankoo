import { css } from '@emotion/react';
import styled from '@emotion/styled';
import ArrowForwardIosIcon from '@mui/icons-material/ArrowForwardIos';
import { Link } from 'react-router-dom';
import CouponTypesNav from '../components/Main/CouponTypesNav';
import GridViewCoupon from '../components/Main/GridViewCoupon';
import ArrowBackButton from '../components/shared/ArrowBackButton';
import useEnterCouponContent from '../hooks/EnterCouponContent/useEnterCouponContent';
import { couponTypeKeys } from '../types';

import Header from '../components/shared/Header';
import HeaderText from '../components/shared/HeaderText';
import PageLayout from '../components/shared/PageLayout';

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
        <CouponTypesNav
          onChangeType={setCouponType}
          currentType={couponType}
          selectableCouponTypes={couponTypesWithoutEntire}
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
      <S.LongButton onClick={sendCoupon} disabled={!isFilled}>
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
    color: white;
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
    background-color: #8c888866;
    color: white;
    border-radius: 5px;
    padding: 10px;

    &::placeholder {
      color: #8e8e8e;
    }
    &:focus {
      outline: 3px solid #ff6450;
    }
  `,
  MessageTextarea: styled.textarea`
    resize: none;
    height: 10vh;
    border: none;
    padding: 5px;
    font-size: 18px;
    background-color: #8c888866;
    border-radius: 5px;
    padding: 10px;
    color: white;

    &::placeholder {
      color: #8e8e8e;
    }
    &:focus {
      outline: 3px solid #ff6450;
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
  `,
  CouponBox: styled.div`
    margin: 0 auto;
  `,
};

export default EnterCouponContent;
