import styled from '@emotion/styled';
import { useMemo } from 'react';

type TimeTable = {
  time: string;
  isPassed: boolean;
};

const getCurrentTimeFormatYYMMDDHM = () => {
  const nowYear = new Date().getFullYear();
  const nowMonth = new Date().getMonth() + 1;
  const nowDate = new Date().getDate();
  const nowHour = new Date().getHours();
  const nowMin = new Date().getMinutes();

  return `${nowYear}-${nowMonth}-${nowDate} ${nowHour}:${nowMin}`;
};

const timeTableGenerator = (startHour, endHour, selectedDate) => {
  const timeTable: TimeTable[] = [];

  for (let i = startHour; i < endHour + 1; i += 1) {
    const scale = 2;
    for (let j = 0; j < scale; j += 1) {
      if (i === endHour) {
        break;
      }

      const time = `${String(i).padStart(2, '0')}:${String((j * 60) / scale).padStart(2, '0')}`;

      const isPassed =
        Number(new Date(getCurrentTimeFormatYYMMDDHM())) >
        Number(new Date(`${selectedDate} ${time}`));

      timeTable.push({ time, isPassed });
    }
  }

  return timeTable;
};

const Time = ({ selectedTime, setSelectedTime, selectedDate }) => {
  const dayTimeTable = useMemo(() => timeTableGenerator(10, 12, selectedDate), [selectedDate]);
  const nightTimeTable = useMemo(() => timeTableGenerator(12, 20, selectedDate), [selectedDate]);

  const isSelectedDayToday = new Date(selectedDate).toDateString() === new Date().toDateString();

  return (
    <S.Container>
      <S.Gap>
        <S.TimeLabel>오전</S.TimeLabel>
        <S.TimeTable>
          {dayTimeTable.map(time => (
            <S.Time
              onClick={() => {
                if (time.isPassed) {
                  return;
                }
                setSelectedTime(time.time);
              }}
              isSelected={time.time === selectedTime}
              isPassed={time.isPassed}
            >
              {time.time}
            </S.Time>
          ))}
        </S.TimeTable>
      </S.Gap>
      <S.Gap>
        <S.TimeLabel>오후</S.TimeLabel>
        <S.TimeTable>
          {nightTimeTable.map(time => (
            <S.Time
              onClick={() => {
                if (time.isPassed) {
                  return;
                }
                setSelectedTime(time.time);
              }}
              isSelected={time.time === selectedTime}
              isPassed={time.isPassed}
            >
              {time.time}
            </S.Time>
          ))}
        </S.TimeTable>
      </S.Gap>
    </S.Container>
  );
};

export default Time;

type TimeProps = {
  isPassed: boolean;
  isSelected: boolean;
};

const S = {
  Container: styled.div`
    display: flex;
    flex-flow: column;
    gap: 15px;
    overflow: auto;
  `,
  Gap: styled.div`
    display: flex;
    flex-flow: column;
    gap: 12px;
  `,
  TimeTable: styled.div`
    display: grid;
    grid-gap: 5px;
    grid-template-columns: repeat(4, 1fr);
  `,
  TimeLabel: styled.span`
    color: white;
    font-size: 18px;
  `,
  Time: styled.button<TimeProps>`
    border: none;
    display: flex;
    align-items: center;
    justify-content: center;
    padding: 15px 20px;
    background-color: ${({ isPassed, isSelected, theme }) =>
      isPassed ? '#4a4a4a3c' : isSelected ? theme.primary : '#4a4a4a'};
    border-radius: 4px;
    color: ${({ isPassed }) => (isPassed ? '#8e8e8e' : 'white')};
    cursor: ${({ isPassed }) => (isPassed ? 'default' : 'pointer')};
    :hover {
      ${({ theme, isPassed, isSelected }) =>
        isSelected ? theme.primary : !isPassed && `background-color: #ff6347c4`}
    }
  `,
};
