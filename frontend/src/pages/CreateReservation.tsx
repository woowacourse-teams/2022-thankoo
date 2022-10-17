import styled from '@emotion/styled';
import AccessAlarmsIcon from '@mui/icons-material/AccessAlarms';
import ArrowForwardIosIcon from '@mui/icons-material/ArrowForwardIos';
import CalendarMonthIcon from '@mui/icons-material/CalendarMonth';
import { Link } from 'react-router-dom';
import Time from '../components/@shared/Time';
import ConfirmReservationModal from '../components/CreateReservation/ConfirmReservationModal';
import useModal from '../hooks/useModal';
import ArrowBackButton from './../components/@shared/ArrowBackButton';
import Header from '../layout/Header';
import PageLayout from '../layout/PageLayout';
import { ROUTE_PATH } from './../constants/routes';
import useCreateReservation from './../hooks/CreateReservation/useCreateReservation';
import HeaderText from '../layout/HeaderText';
import LongButton from '../components/@shared/LongButton';

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
      <LongButton
        isDisabled={!isFilled}
        onClick={() => {
          show();
          setModalContent(
            <ConfirmReservationModal
              receiver={couponDetail?.coupon.sender.name}
              date={date}
              time={time}
              submit={createReservation}
            />
          );
        }}
      >
        약속 신청하기
        <ArrowForwardIosIcon />
      </LongButton>
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
