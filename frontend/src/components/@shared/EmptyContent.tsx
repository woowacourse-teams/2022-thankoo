import styled from '@emotion/styled';

const COMMENTS = [
  '비어 있습니다 1',
  '비어 있습니다 2',
  '비어 있습니다 3',
  '비어 있습니다 4',
  '비어 있습니다 5',
  '비어 있습니다 6',
];

const EmptyContent = content => {
  return <S.Container>{COMMENTS[Math.round(Math.random() * 5)]}</S.Container>;
};

export default EmptyContent;

const S = {
  Container: styled.section`
    display: flex;
    align-items: center;
    justify-content: center;
    color: white;
    height: 60vh;
  `,
};
