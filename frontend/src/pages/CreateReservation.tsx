import { css } from '@emotion/react';
import styled from '@emotion/styled';
import ArrowForwardIosIcon from '@mui/icons-material/ArrowForwardIos';
import { Link } from 'react-router-dom';
import Time from '../components/@shared/Time';
import ArrowBackButton from './../components/@shared/ArrowBackButton';
import Header from './../components/@shared/Header';
import HeaderText from './../components/@shared/HeaderText';
import PageLayout from './../components/@shared/PageLayout';
import { ROUTE_PATH } from './../constants/routes';
import useCreateReservation from './../hooks/CreateReservation/useCreateReservation';
import AccessAlarmsIcon from '@mui/icons-material/AccessAlarms';

const CreateReservation = () => {
  const {
    isFilled,
    setReservationDate,
    sendReservation,
    yesterday,
    date,
    time,
    setReservationTime,
  } = useCreateReservation();

  return (
    <S.PageLayout>
      <Header>
        <Link to={`${ROUTE_PATH.EXACT_MAIN}`}>
          <ArrowBackButton />
        </Link>
        <HeaderText>언제 만날까요?</HeaderText>
      </Header>
      <S.Body>
        <S.Area>
          <S.Label>날짜 입력</S.Label>
          <input type='date' value={date} onChange={setReservationDate} min={yesterday} />
        </S.Area>
        <S.TimeArea>
          <S.TimeLabel>
            <AccessAlarmsIcon />
            시간 선택
          </S.TimeLabel>
          <Time />
        </S.TimeArea>
      </S.Body>
      <S.LongButton
        disabled={!isFilled}
        onClick={() => {
          sendReservation();
        }}
      >
        약속 신청하기
        <ArrowForwardIosIcon />
      </S.LongButton>
    </S.PageLayout>
  );
};

const S = {
  PageLayout: styled(PageLayout)`
    height: 100%;
  `,
  Body: styled.div`
    display: flex;
    flex-direction: column;
    justify-content: center;
    height: 70%;
    gap: 2rem;
    padding: 5px 3vw;
  `,
  Area: styled.div`
    display: flex;
    flex-direction: column;
    gap: 1rem;
  `,
  TimeArea: styled.div`
    display: flex;
    flex-direction: column;
    gap: 1rem;
    overflow: hidden;
  `,
  Label: styled.div`
    font-size: 21px;
    color: ${({ theme }) => theme.header.color};
  `,
  TimeLabel: styled.div`
    font-size: 21px;
    color: ${({ theme }) => theme.header.color};
    display: flex;
    align-items: center;
    gap: 7px;
  `,
  Calander: styled.div`
    height: 21rem;
    background-color: white;
  `,
  LongButton: styled.button`
    position: fixed;
    bottom: 5%;
    left: 50%;
    transform: translateX(-50%);
    border: none;
    border-radius: 30px;
    font-size: 18px;
    padding: 10px 20px;
    display: flex;
    width: 90%;
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
