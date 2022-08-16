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
import CalendarMonthIcon from '@mui/icons-material/CalendarMonth';
import useModal from '../hooks/useModal';
import ConfirmReservationModal from '../components/CreateReservation/ConfirmReservationModal';

const CreateReservation = () => {
  const {
    isFilled,
    setReservationDate,
    date,
    time,
    setTime,
    yesterday,
    couponDetail,
    createReservation,
  } = useCreateReservation();
  const { setModalContent, show, visible } = useModal();

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
          <S.Label>
            <S.CalendarIcon />
            날짜 입력
          </S.Label>
          <S.DateInput
            type='date'
            value={date}
            onChange={setReservationDate}
            min={yesterday}
            max={new Date().getFullYear()}
          />
        </S.Area>
        <S.TimeArea>
          <S.Label>
            <S.TimeIcon />
            시간 선택
          </S.Label>
          <Time setSelectedTime={setTime} selectedTime={time} selectedDate={date} />
        </S.TimeArea>
      </S.Body>
      <S.LongButton
        disabled={!isFilled}
        onClick={() => {
          show();
          setModalContent(
            <ConfirmReservationModal
              receiver={couponDetail?.coupon.receiver.name}
              date={date}
              time={time}
              submit={createReservation}
            />
          );
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
    padding: 15px 3vw;
  `,
  Area: styled.div`
    display: flex;
    flex-direction: column;
    gap: 1rem;
  `,
  CalendarIcon: styled(CalendarMonthIcon)`
    transform: scale(0.7);
  `,
  TimeIcon: styled(AccessAlarmsIcon)`
    transform: scale(0.7);
  `,
  DateInput: styled.input`
    width: 100%;
    font-size: 18px;
    padding: 10px 5px;
    border: none;
    background-color: #4a4a4a;
    border-radius: 4px;
    -webkit-appearance: none;
    outline: none;
    color: ${({ theme }) => theme.input.color};
    box-sizing: border-box;
    :disabled {
      color: #b4b4b4;
    }
    &:focus {
      outline: none;
    }
    &::placeholder {
      color: ${({ theme }) => theme.input.placeholder};
    }
  `,
  TimeArea: styled.div`
    display: flex;
    flex-direction: column;
    gap: 1rem;
    overflow: hidden;
  `,
  Label: styled.div`
    font-size: 18px;
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
