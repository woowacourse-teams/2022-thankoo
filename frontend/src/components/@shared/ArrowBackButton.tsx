import styled from '@emotion/styled';
import ArrowBackIosIcon from '@mui/icons-material/ArrowBackIos';

const ArrowBackButton = () => {
  return (
    <BackwardButton>
      <ArrowBackIcon />
    </BackwardButton>
  );
};

const BackwardButton = styled.button`
  background-color: transparent;
  border: none;
  color: ${({ theme }) => theme.page.color};
  padding: 0;
`;

const ArrowBackIcon = styled(ArrowBackIosIcon)`
  width: 2rem;
  height: 2rem;
`;

export default ArrowBackButton;
