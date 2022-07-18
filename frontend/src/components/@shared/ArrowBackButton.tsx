import styled from '@emotion/styled';
import ArrowBackIosIcon from '@mui/icons-material/ArrowBackIos';

const ArrowBackButton = () => {
  return (
    <BackwardButton>
      <ArrowBackIosIcon />
    </BackwardButton>
  );
};

const BackwardButton = styled.button`
  background-color: transparent;
  border: none;
  color: ${({ theme }) => theme.page.color};
  padding: 0;
`;

export default ArrowBackButton;
