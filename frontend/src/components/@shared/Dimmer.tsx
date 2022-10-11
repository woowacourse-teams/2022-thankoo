import styled from '@emotion/styled';
import useModal from '../../hooks/useModal';

const Dimmer = ({ show, onClick }) => {
  return <Container onClick={onClick} show={show} />;
};

export default Dimmer;

type DimmerProps = {
  show: boolean;
};

const Container = styled.div<DimmerProps>`
  display: ${({ show }) => (show ? 'block' : 'none')};
  position: fixed;
  width: 100%;
  height: 100%;
  top: 0;
  left: 0;
  background-color: #00000082;
  z-index: 100;
`;
