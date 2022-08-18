import { css } from '@emotion/react';
import styled from '@emotion/styled';
import { Link } from 'react-router-dom';
import { useResetRecoilState } from 'recoil';
import { ROUTE_PATH } from '../../constants/routes';
import SuccessAnimation from '../@shared/SuccessAnimation';
import { onSuccessContentAtom } from './../../recoil/atom';

const CreateReservationSuccess = ({ receiver, date, time }) => {
  const pageReset = useResetRecoilState(onSuccessContentAtom);

  return (
    <S.Container>
      <S.Wrapper>
        <S.Header>예약이 완료됐습니다!</S.Header>
        <SuccessAnimation />
        <S.ContenstWrapper>
          <div
            css={css`
              ${SpaceBetween}
            `}
          >
            <S.Title>만날 사람</S.Title>
            <S.ContentText>{receiver}</S.ContentText>
          </div>
          <div
            css={css`
              ${SpaceBetween}
            `}
          >
            <S.Title>날짜</S.Title>
            <S.ContentText>{date}</S.ContentText>
          </div>
          <div
            css={css`
              ${SpaceBetween}
            `}
          >
            <S.Title>시간</S.Title>
            <S.ContentText>{time}</S.ContentText>
          </div>
        </S.ContenstWrapper>
        <S.ButtonWrapper>
          <S.StyledLink to={ROUTE_PATH.RESERVATIONS} onClick={pageReset}>
            <S.Button primary>예약 확인하기</S.Button>
          </S.StyledLink>
          <S.StyledLink to={ROUTE_PATH.EXACT_MAIN} onClick={pageReset}>
            <S.Button>홈으로</S.Button>
          </S.StyledLink>
        </S.ButtonWrapper>
      </S.Wrapper>
    </S.Container>
  );
};

export default CreateReservationSuccess;

type ButtonProps = {
  primary?: boolean;
};

const SpaceBetween = {
  display: 'flex',
  'justify-content': 'space-between',
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
    color: white;
    margin: 5rem auto;
    justify-content: space-between;
  `,
  Header: styled.span`
    font-size: x-large;
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
    font-size: larger;
  `,
  SpaceBetween: styled.div`
    display: flex;
    justify-content: space-between;
  `,
  ButtonWrapper: styled.div`
    ${SpaceBetween}
    gap:5px;
  `,
  Button: styled.button<ButtonProps>`
    border: none;
    border-radius: 4px;
    background-color: ${({ theme, primary }) => (primary ? theme.primary : '#4a4a4a')};
    padding: 0.7rem;
    color: white;
    width: 100%;
  `,
  StyledLink: styled(Link)`
    width: 100%;
  `,
};
