import styled from '@emotion/styled';

const Header = styled.header`
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  gap: 7px;
  color: ${({ theme }) => theme.header.color};
  margin: 10px 3vw;
`;

export default Header;
