import styled from '@emotion/styled';
import IconEmptyList from '../LogoEmptyList';
import { palette } from './../../../styles/ThemeProvider';

const EmptyContent = () => {
  return (
    <S.Container>
      <IconEmptyList />
      <p>비어있습니다</p>
    </S.Container>
  );
};

export default EmptyContent;

const S = {
  Container: styled.section`
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    color: ${palette.WHITE};
    opacity: 0.8;
    gap: 15px;
    height: 60vh;
    opacity: 0.8;
    gap: 10px;
  `,
};
