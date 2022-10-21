import styled from '@emotion/styled';
import Button from './../components/@shared/Button';
import { ROUTE_PATH } from './../constants/routes';

const ErrorFallBack = () => {
  const onClick = () => {
    window.location.href = ROUTE_PATH.EXACT_MAIN;
  };

  return (
    <S.Wrapper>
      <p>예기치 못한 오류가 발생했습니다.</p>
      <Button onClick={onClick}>새로고침 하기</Button>
    </S.Wrapper>
  );
};

export default ErrorFallBack;

const S = {
  Wrapper: styled.div`
    display: flex;
    flex-direction: column;
    justify-content: space-between;
    gap: 3rem;
    font-size: 2rem;
    text-shadow: -1px 0 #000, 0 1px #000, 1px 0 #000, 0 -1px #000;
    color: white;
  `,
};
