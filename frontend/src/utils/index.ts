export const sorted = (arr, sortFunc) => {
  if (!arr) {
    return arr;
  }
  const duplicatedArr = JSON.parse(JSON.stringify(arr));
  duplicatedArr.sort(sortFunc);

  return duplicatedArr;
};
