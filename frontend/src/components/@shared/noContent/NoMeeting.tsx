import styled from '@emotion/styled';
import { FlexCenter } from '../../../styles/mixIn';
import IconEmptyList from '../LogoEmptyList';

const NoMeeting = () => {
  return (
    <S.Container>
      <S.Box>
        <IconEmptyList />
        <S.Comment>
          예정된 약속이 없네요⭐
          <br />
          예약이 확정되면 약속이 됩니다.
        </S.Comment>
      </S.Box>
    </S.Container>
  );
};

const S = {
  Container: styled.div`
    position: absolute;
    left: 50%;
    top: 50%;
    transform: translate(-50%, -50%);
    opacity: 0.9;
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
  Comment: styled.h3`
    font-size: 1.5rem;
    line-height: 30px;
  `,
};

export default NoMeeting;
