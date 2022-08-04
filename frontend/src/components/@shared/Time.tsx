import styled from '@emotion/styled';

const timeTableGenerator = (startHour, endHour) => {
  const timeTable: string[] = [];

  for (let i = startHour; i < endHour + 1; i += 1) {
    for (let j = 0; j < 2; j += 1) {
      if (i === endHour) {
        break;
      }
      timeTable.push(`${String(i).padStart(2, '0')}:${String(j * 30).padStart(2, '0')}`);
    }
  }

  return timeTable;
};

const Time = () => {
  const dayTimeTable = timeTableGenerator(10, 12);
  const nightTimeTable = timeTableGenerator(12, 20);

  return (
    <S.Container>
      <S.Gap>
        <S.TimeLabel>오전</S.TimeLabel>
        <S.TimeTable>
          {dayTimeTable.map(time => (
            <S.Time>{time}</S.Time>
          ))}
        </S.TimeTable>
      </S.Gap>
      <S.Gap>
        <S.TimeLabel>오후</S.TimeLabel>
        <S.TimeTable>
          {nightTimeTable.map(time => (
            <S.Time>{time}</S.Time>
          ))}
        </S.TimeTable>
      </S.Gap>
    </S.Container>
  );
};

export default Time;

const S = {
  Container: styled.div`
    display: flex;
    flex-flow: column;
    gap: 15px;
    overflow: hidden;
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
  Time: styled.div`
    display: flex;
    align-items: center;
    justify-content: center;
    padding: 15px 20px;
    background-color: #4a4a4a;
    border-radius: 4px;
    color: white;
    cursor: pointer;
    :hover {
      background-color: ${({ theme }) => theme.primary};
    }
  `,
};
