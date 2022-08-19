import styled from '@emotion/styled';
import CloseIcon from '@mui/icons-material/Close';

const CloseButton = ({ onClick, color }) => {
  return (
    <S.CloseModalButton onClick={onClick}>
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
    height: 15px;
    fill: ${({ color }) => color};
  `,
};
