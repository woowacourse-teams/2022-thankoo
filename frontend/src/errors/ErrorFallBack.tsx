import styled from '@emotion/styled';

const ErrorFallBack = () => {
  return <S.Wrapper>예기치 못한 에러가 발생했습니다!</S.Wrapper>;
};

export default ErrorFallBack;

const S = {
  Wrapper: styled.div`
    font-size: 2rem;
    color: white;
  `,
};
