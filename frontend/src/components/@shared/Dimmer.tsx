import styled from '@emotion/styled';

type DimmerProps = {
  show: boolean;
  onClick: () => void;
};

const Dimmer = ({ show, onClick }: DimmerProps) => {
  return <Container onClick={onClick} show={show} />;
};

export default Dimmer;

type DimmerStyleProps = {
  show: boolean;
};

const Container = styled.div<DimmerStyleProps>`
  display: ${({ show }) => (show ? 'block' : 'none')};
  position: fixed;
  width: 100%;
  height: 100%;
  top: 0;
  left: 0;
  background-color: #00000082;
  z-index: 100;
`;
