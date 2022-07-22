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
          <S.Label>직접 입력하기</S.Label>
          <input type='date' value={date} onChange={setReservationDate} min={yesterday} />
          <Time value={time} min='10:00:00' max='22:00:00' required onChange={setReservationTime} />
        </S.Area>
        {/* <S.Area>
          <S.Label>달력에서 고르기</S.Label>
          <S.Calander>달력이 들어갈 자리 입니다.</S.Calander>
        </S.Area> */}
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
    padding: 0 15px;
  `,
  Area: styled.div`
    display: flex;
    flex-direction: column;
    gap: 1rem;
  `,
  Label: styled.div`
    font-size: 21px;
    color: ${({ theme }) => theme.header.color};
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
