import styled from '@emotion/styled';
import AccessAlarmsIcon from '@mui/icons-material/AccessAlarms';
import ArrowForwardIosIcon from '@mui/icons-material/ArrowForwardIos';
import CalendarMonthIcon from '@mui/icons-material/CalendarMonth';
import { Link } from 'react-router-dom';
import LongButton from '../../components/@shared/LongButton';
import ModalWrapper from '../../components/@shared/Modal/ModalWrapper';
import Time from '../../components/@shared/Time';
import ConfirmReservationModal from './components/ConfirmReservationModal';
import Header from '../../layout/Header';
import HeaderText from '../../layout/HeaderText';
import PageLayout from '../../layout/PageLayout';
import ArrowBackButton from '../../components/@shared/ArrowBackButton';
import { ROUTE_PATH } from '../../constants/routes';
import useCreateReservation from './hooks/useCreateReservation';

const CreateReservation = () => {
  const {
    isFilled,
    setReservationDate,
    date,
    time,
    setTime,
    today,
    couponDetail,
    createReservation,
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
          <S.Label>
            <S.CalendarIcon />
            날짜 입력
          </S.Label>
          <S.DateInput
            type='date'
            value={date}
            onChange={setReservationDate}
            min={today}
            max={`${new Date().getFullYear()}-12-31`}
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
      <ModalWrapper
        isDisabled={!time || !date}
        modal={
          <ConfirmReservationModal
            receiver={couponDetail?.coupon.sender.name}
            date={date}
            time={time}
            submit={createReservation}
          />
        }
      >
        <LongButton isDisabled={!isFilled}>
          약속 신청하기
          <ArrowForwardIosIcon />
        </LongButton>
      </ModalWrapper>
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
    height: calc(80% - 5.5rem - 5%);
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
};

export default CreateReservation;
