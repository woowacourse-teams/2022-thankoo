import styled from '@emotion/styled';
import CloseIcon from '@mui/icons-material/Close';

const CloseButton = ({ color }) => {
  return (
    <S.CloseModalButton>
      <S.CloseModalIcon color={color} />
    </S.CloseModalButton>
  );
};

export default CloseButton;

const S = {
  CloseModalButton: styled.button`
    background-color: transparent;
    border: none;
  `,
  CloseModalIcon: styled(CloseIcon)`
    fill: ${({ color }) => color};
  `,
};
