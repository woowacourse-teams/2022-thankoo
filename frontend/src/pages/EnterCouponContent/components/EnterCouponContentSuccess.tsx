import { css } from '@emotion/react';
import styled from '@emotion/styled';
import { useEffect } from 'react';
import { Link } from 'react-router-dom';
import { useRecoilState, useResetRecoilState } from 'recoil';
import Button from '../../../components/@shared/Button/Button';
import SuccessAnimation from '../../../components/@shared/SuccessAnimation';
import { BASE_URL } from '../../../constants/api';
import { ROUTE_PATH } from '../../../constants/routes';
import { checkedUsersAtom, onSuccessContentAtom, sentOrReceivedAtom } from '../../../recoil/atom';
import { FlexColumn, gap } from '../../../styles/mixIn';
import { couponTypes } from '../../../types/coupon';
import { palette } from './../../../styles/ThemeProvider';

const EnterCouponContentSuccess = ({ receivers, title, message, couponType }) => {
  const resetCheckedUsers = useResetRecoilState(checkedUsersAtom);
  const pageReset = useResetRecoilState(onSuccessContentAtom);
  const [_, setSentOrReceived] = useRecoilState(sentOrReceivedAtom);

  useEffect(() => {
    resetCheckedUsers();
  }, []);

  return (
    <S.Container>
      <S.Wrapper>
        <S.Header>쿠폰이 전송됐습니다!</S.Header>
        <SuccessAnimation />
        <S.ContenstWrapper>
          <S.Title>{receivers.length >= 1 ? '받는 사람들' : '받는 사람'}</S.Title>
          <S.ReceiversWrapper>
            {receivers.map(receiver => (
              <S.UserWrapper>
                <S.UserImage src={`${BASE_URL}${receiver.imageUrl}`} />
                <S.UserName>{receiver.name}</S.UserName>
              </S.UserWrapper>
            ))}
          </S.ReceiversWrapper>
          <div
            css={css`
              ${SpaceBetween}
            `}
          >
            <S.Title>쿠폰 종류</S.Title>
            <S.ContentText>{couponTypes[couponType]}</S.ContentText>
          </div>
          <div
            css={css`
              ${SpaceBetween}
            `}
          >
            <S.Title>제목</S.Title>
            <S.ContentText>{title}</S.ContentText>
          </div>
          <div
            css={css`
              ${SpaceBetween}
              ${FlexColumn}
              ${gap('1rem')}
            `}
          >
            <S.Title>메세지</S.Title>
            <S.ContentText>{message}</S.ContentText>
          </div>
        </S.ContenstWrapper>
        <S.ButtonWrapper>
          <S.StyledLink to={ROUTE_PATH.EXACT_MAIN} onClick={pageReset}>
            <Button
              onClick={() => {
                setSentOrReceived('sent');
              }}
            >
              쿠폰 확인하기
            </Button>
          </S.StyledLink>
        </S.ButtonWrapper>
      </S.Wrapper>
    </S.Container>
  );
};

export default EnterCouponContentSuccess;

const SpaceBetween = {
  display: 'flex',
  justifyContent: 'space-between',
};

const S = {
  Container: styled.div`
    width: 100%;
    height: 100%;
    display: flex;
    align-items: center;
    justify-content: center;
  `,
  Wrapper: styled.div`
    display: flex;
    flex-direction: column;
    width: 80vw;
    height: 70vh;
    color: ${palette.WHITE};
    margin: 5rem auto;
    justify-content: space-between;
  `,
  Header: styled.span`
    font-size: x-large;
  `,
  ReceiversWrapper: styled.div`
    display: flex;
    justify-content: center;
    gap: 15px;
    overflow-x: auto;
    overflow-y: hidden;
  `,
  UserWrapper: styled.div`
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    gap: 5px;
  `,
  UserImage: styled.img`
    width: 30px;
    height: 30px;
    object-fit: cover;
    border-radius: 50%;
  `,
  UserName: styled.span`
    font-size: 1.3rem;
  `,
  ContenstWrapper: styled.div`
    display: flex;
    flex-direction: column;
    gap: 10px;
  `,
  Title: styled.span`
    font-size: medium;
    color: #8e8e8e;
  `,
  ContentText: styled.span`
    font-size: 1.5rem;
  `,
  SpaceBetween: styled.div`
    display: flex;
    justify-content: space-between;
  `,
  ButtonWrapper: styled.div`
    ${SpaceBetween}
    gap:5px;
  `,
  StyledLink: styled(Link)`
    width: 100%;
  `,
};
