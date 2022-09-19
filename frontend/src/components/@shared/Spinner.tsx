import { css, keyframes } from '@emotion/react';
import styled from '@emotion/styled';

const Spinner = () => {
  return (
    <S.Layout>
      <S.Spinner>
        <S.Dot1 />
        <S.Dot2 />
      </S.Spinner>
    </S.Layout>
  );
};

export default Spinner;

const sk_rotate = keyframes`
  100% { 
    transform: rotate(360deg);  
    -webkit-transform: rotate(360deg) 
  }
`;
const sk_bounce = keyframes`
  0%, 100% { 
    transform: scale(0.0);
    -webkit-transform: scale(0.0);
  } 50% { 
    transform: scale(1.0);
    -webkit-transform: scale(1.0);
  }
`;

const DotCommonStyle = css`
  width: 60%;
  height: 60%;
  display: inline-block;
  position: absolute;
  top: 0;
  background-color: tomato;
  border-radius: 100%;

  -webkit-animation: ${sk_bounce} 2s infinite ease-in-out;
  animation: ${sk_bounce} 2s infinite ease-in-out;
`;

const S = {
  Layout: styled.div`
    width: 100vw;
    height: 100vh;
    display: flex;
    align-items: center;
    justify-content: center;
  `,
  Spinner: styled.div`
    margin: 100px auto;
    width: 40px;
    height: 40px;
    position: relative;
    text-align: center;

    -webkit-animation: ${sk_rotate} 2s infinite linear;
    animation: ${sk_rotate} 2s infinite linear;
  `,
  Dot1: styled.div`
    ${DotCommonStyle}
  `,
  Dot2: styled.div`
    ${DotCommonStyle}
    top: auto;
    bottom: 0;
    -webkit-animation-delay: -1s;
    animation-delay: -1s;
  `,
};
