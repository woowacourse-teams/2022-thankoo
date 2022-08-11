import styled from '@emotion/styled';
import IconEmptyList from '../LogoEmptyList';

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
    color: white;
    opacity: 0.8;
    gap: 15px;
    height: 60vh;
    opacity: 0.8;
    gap: 10px;
  `,
};
