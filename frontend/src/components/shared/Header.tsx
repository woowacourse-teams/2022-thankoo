import styled from '@emotion/styled';

const Header = styled.header`
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  gap: 15px;
  color: ${({ theme }) => theme.header.color};
  margin: 10px 0 0 2vw;
`;

export default Header;
