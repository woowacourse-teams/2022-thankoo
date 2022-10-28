import styled from '@emotion/styled';
import { SetStateAction, useMemo } from 'react';
import { palette } from './../../styles/ThemeProvider';

type TimeTable = {
  time: string;
  isPassed: boolean;
};

type TimeProps = {
  selectedTime: string;
  setSelectedTime: React.Dispatch<SetStateAction<string>>;
  selectedDate: string;
};

const getCurrentTimeFormatYYMMDDHM = () => {
  const today = new Date();
  const nowYear = today.getFullYear();
  const nowMonth = String(today.getMonth() + 1).padStart(2, '0');
  const nowDate = String(today.getDate()).padStart(2, '0');
  const nowHour = String(today.getHours()).padStart(2, '0');
  const nowMin = String(today.getMinutes()).padStart(2, '0');
  const nowSec = String(today.getSeconds()).padStart(2, '0');

  return `${nowYear}/${nowMonth}/${nowDate} ${nowHour}:${nowMin}:${nowSec}`;
};

const timeTableGenerator = (startHour: number, endHour: number, selectedDate: string) => {
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

const Time = ({ selectedTime, setSelectedTime, selectedDate }: TimeProps) => {
  const dayTimeTable = useMemo(() => timeTableGenerator(10, 12, selectedDate), [selectedDate]);
  const nightTimeTable = useMemo(() => timeTableGenerator(12, 20, selectedDate), [selectedDate]);

  return (
    <S.Container>
      <S.Gap>
        <S.TimeLabel>오전</S.TimeLabel>
        <S.TimeTable>
          {dayTimeTable.map((time, idx) => (
            <S.Time
              key={time.time + idx}
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
          {nightTimeTable.map((time, idx) => (
            <S.Time
              key={time.time + idx}
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

type TimeStyleProps = {
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
    color: ${palette.WHITE};
    font-size: 18px;
  `,
  Time: styled.button<TimeStyleProps>`
    border: none;
    display: flex;
    align-items: center;
    justify-content: center;
    padding: 15px 20px;
    background-color: ${({ isPassed, isSelected, theme }) =>
      isPassed ? '#4a4a4a3c' : isSelected ? theme.primary : '#4a4a4a'};
    border-radius: 4px;
    color: ${({ isPassed }) => (isPassed ? '#8e8e8e' : palette.WHITE)};
    cursor: ${({ isPassed }) => (isPassed ? 'default' : 'pointer')};
    :hover {
      ${({ theme, isPassed, isSelected }) =>
        isSelected ? theme.primary : !isPassed && `background-color: #ff6347c4`}
    }
  `,
};
