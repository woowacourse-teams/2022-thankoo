import styled from '@emotion/styled';

type TimeTable = {
  time: string;
  isPassed: boolean;
};

const timeTableGenerator = (startHour, endHour) => {
  const timeTable: TimeTable[] = [];

  for (let i = startHour; i < endHour + 1; i += 1) {
    const scale = 2;
    for (let j = 0; j < scale; j += 1) {
      if (i === endHour) {
        break;
      }
      const time = `${String(i).padStart(2, '0')}:${String((j * 60) / scale).padStart(2, '0')}`;
      const isPassed =
        Number(new Date(`2022-01-01 ${new Date().getHours()}:${new Date().getMinutes()}`)) >
        Number(new Date(`2022-01-01 ${time}`));
      timeTable.push({ time, isPassed });
    }
  }

  return timeTable;
};

const Time = ({ selectedTime, setSelectedTime, selectedDate }) => {
  const dayTimeTable = timeTableGenerator(10, 12);
  const nightTimeTable = timeTableGenerator(12, 20);
  const isSelectedDayToday = new Date(selectedDate).toDateString() === new Date().toDateString();

  return (
    <S.Container>
      <S.Gap>
        <S.TimeLabel>오전</S.TimeLabel>
        <S.TimeTable>
          {dayTimeTable.map(time => (
            <S.Time
              disabled={time.isPassed && isSelectedDayToday}
              onClick={() => {
                if (time.isPassed && isSelectedDayToday) {
                  return;
                }
                setSelectedTime(time.time);
              }}
              isSelected={time.time === selectedTime}
              isPassed={time.isPassed && isSelectedDayToday}
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
                if (time.isPassed && isSelectedDayToday) {
                  return;
                }
                setSelectedTime(time.time);
              }}
              isSelected={time.time === selectedTime}
              isPassed={time.isPassed && isSelectedDayToday}
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
