import styled from '@emotion/styled';

const Header = styled.header`
  height: 15%;
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  gap: 1.2rem;
  color: ${({ theme }) => theme.header.color};
  margin: 1rem 3vw;
`;

export default Header;
