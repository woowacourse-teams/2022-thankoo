export const dayDifferenceFromToday = meetingDay => {
  const today = new Date();

  const dayForCal =
    `${meetingDay.getFullYear()}` +
    `${String(meetingDay.getMonth()).padStart(2, '0')}` +
    `${String(meetingDay.getDate()).padStart(2, '0')}`;
  const todayForCal =
    `${today.getFullYear()}` +
    `${String(today.getMonth()).padStart(2, '0')}` +
    `${String(today.getDate()).padStart(2, '0')}`;

  return Number(dayForCal) - Number(todayForCal);
};

export const engDayTo요일 = {
  Mon: '월',
  Tue: '화',
  Wed: '수',
  Thu: '목',
  Fri: '금',
  Sat: '토',
  Sun: '일',
};

export const dateFormmater = serverDate => {
  const day = engDayTo요일[new Date(serverDate.split(' ')[0]).toString().slice(0, 3)];
  const date = serverDate.split(' ')[0];
  const time = serverDate.split(' ')[1].slice(0, 5);

  return { day, date, time };
};
