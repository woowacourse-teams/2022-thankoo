import styled from '@emotion/styled';
import { FlexCenter } from '../../../styles/mixIn';
import IconEmptyList from './../LogoEmptyList';

const NoReservation = () => {
  return (
    <S.Container>
      <S.Box>
        <S.IconWrapper>
          <IconEmptyList />
        </S.IconWrapper>
        <S.Comment>
          예약이 없네요👻
          <br />
          홈에서 예약을 잡을 수 있어요!
        </S.Comment>
      </S.Box>
    </S.Container>
  );
};

const S = {
  Container: styled.div`
    ${FlexCenter}
    flex: 1;
  `,
  Box: styled.div`
    ${FlexCenter}
    flex-direction: column;
    width: 280px;
    height: fit-content;
    border-radius: 10px;
    color: ${({ theme }) => theme.header.color};
    text-align: center;
    gap: 8px;
    padding: 30px 10px;
  `,
  IconWrapper: styled.div`
    height: 70px;
  `,
  Comment: styled.h3`
    font-size: 1.5rem;
    line-height: 30px;
  `,
};

export default NoReservation;
