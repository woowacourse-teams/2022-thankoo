import { YYYYMMDD } from 'thankoo-utils-type';

export const engDayTo요일 = {
  Mon: '월',
  Tue: '화',
  Wed: '수',
  Thu: '목',
  Fri: '금',
  Sat: '토',
  Sun: '일',
};

export const isExpiredDate = (time): boolean => {
  const date = new Date();
  const now = date.toLocaleDateString() + ' ' + date.toLocaleTimeString().split(' ')[1];

  return getTimeDifference(now, time) < 0;
};

export const getTimeDifference = (from, to): number =>
  Number(new Date(from)) - Number(new Date(to));

export const getDayDifference = (from, to) =>
  Math.floor((Number(new Date(from)) - Number(new Date(to))) / (1000 * 60 * 60 * 24));

export const serverDateFormmater = serverDate => {
  const day = engDayTo요일[new Date(serverDate.split(' ')[0]).toString().slice(0, 3)];
  const date: YYYYMMDD = serverDate.split(' ')[0];
  const time = serverDate.split(' ')[1].slice(0, 5);

  return { day, date, time };
};

export const krLocaleDateFormatter = localeDate => {
  const [year, month, date] = localeDate
    .split('.')
    .map(string => string.trim())
    .map(string => string.padStart(2, '0'));

  return {
    year,
    month,
    date,
    fullDate: `${year}-${month}-${date}` as YYYYMMDD,
  };
};
