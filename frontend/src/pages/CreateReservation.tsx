import { css } from '@emotion/react';
import styled from '@emotion/styled';
import ArrowForwardIosIcon from '@mui/icons-material/ArrowForwardIos';
import ArrowBackButton from './../components/@shared/ArrowBackButton';
import Header from './../components/@shared/Header';
import HeaderText from './../components/@shared/HeaderText';
import PageLayout from './../components/@shared/PageLayout';
import useCreateReservation from './../hooks/CreateReservation/useCreateReservation';

const CreateReservation = () => {
  const { isFilled, setReservationDate, sendReservation } = useCreateReservation();

  return (
    <PageLayout>
      <Header>
        <ArrowBackButton />
        <HeaderText>언제 만날까요?</HeaderText>
      </Header>
      <S.Body>
        <S.Area>
          <S.Label>직접 입력하기</S.Label>
          <input type='date' onChange={setReservationDate} />
        </S.Area>
        <S.Area>
          <S.Label>달력에서 고르기</S.Label>
        </S.Area>
      </S.Body>
      <S.LongButton disabled={!isFilled} onClick={sendReservation}>
        약속 신청하기
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
    padding: 15px;
  `,
  Area: styled.div`
    display: flex;
    flex-direction: column;
    gap: 1rem;
  `,
  Label: styled.div`
    font-size: 16px;
    color: ${({ theme }) => theme.header.color};
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
};

export default CreateReservation;
