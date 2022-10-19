import styled from '@emotion/styled';

const ErrorFallBack = () => {
  return <S.Wrapper>에러발생!</S.Wrapper>;
};

export default ErrorFallBack;

const S = {
  Wrapper: styled.div`
    color: white;
  `,
};
