import ArrowBackButton from '../commons/Header/ArrowBackButton';
import styled from '@emotion/styled';
import ArrowForwardIosIcon from '@mui/icons-material/ArrowForwardIos';
import CouponTypesNav from '../commons/Main/CouponTypesNav';
import useEnterCouponContent from '../hooks/EnterCouponContent/useEnterCouponContent';
import { Coupon, couponTypeKeys, initialCouponState } from '../types';
import { Link, useNavigate } from 'react-router-dom';
import { useRecoilValue } from 'recoil';
import { authAtom, checkedUsersAtom } from '../recoil/atom';
import GridViewCoupon from '../commons/Main/GridViewCoupon';
import { useEffect, useState } from 'react';
import { css } from '@emotion/react';
import axios from 'axios';
import { BASE_URL } from '../constants';

const couponTypesWithoutEntire = couponTypeKeys.filter(type => type !== 'entire');

const EnterCouponContent = () => {
  const { couponType, setCouponType } = useEnterCouponContent();
  const checkedUsers = useRecoilValue(checkedUsersAtom);
  const auth = useRecoilValue(authAtom);

  const [title, setTitle] = useState('');
  const [message, setMessage] = useState('');

  //TODO 컴포넌트 분리
  const [currentCoupon, setCurrentCoupon] = useState<Coupon>({
    ...initialCouponState,
    sender: {
      ...initialCouponState.sender,
      name: auth.name,
      id: auth.memberId,
    },
    content: {
      ...initialCouponState.content,
      couponType,
    },
  });

  useEffect(() => {
    setCurrentCoupon(prev => ({
      ...prev,
      content: {
        couponType,
        title,
        message,
      },
    }));
  }, [couponType, title, message]);

  const isFilled = !!title && !!message;

  const navigate = useNavigate();

  const sendCoupon = async () => {
    const { status, statusText } = await axios({
      method: 'POST',
      url: `${BASE_URL}/api/coupons/send`,
      headers: { Authorization: `Bearer ${auth.accessToken}` },
      data: {
        receiverId: checkedUsers[0].id,
        content: {
          ...currentCoupon.content,
        },
      },
    });

    navigate('/');
  };

  return (
    <S.Container>
      <S.Header>
        <Link to='/select-receiver'>
          <ArrowBackButton />
        </Link>
        <S.HeaderText>어떤 쿠폰을 보낼까요?</S.HeaderText>
      </S.Header>
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
