import { css } from '@emotion/react';
import styled from '@emotion/styled';
import HomeIcon from '@mui/icons-material/Home';
import { Link, useLocation } from 'react-router-dom';
import { ROUTE_PATH } from '../../constants/routes';

const CouponsPageButton = () => {
  const location = useLocation();

  return (
    <S.Link to={ROUTE_PATH.EXACT_MAIN}>
      <S.ButtonWrapper active={location.pathname === ROUTE_PATH.EXACT_MAIN}>
        <S.IconWrapper>
          <S.Icon />
        </S.IconWrapper>
        <p>홈</p>
      </S.ButtonWrapper>
    </S.Link>
  );
};

export default CouponsPageButton;

type ButtonStyleProps = {
  active: boolean;
};

const S = {
  Link: styled(Link)`
    line-height: 8px;
    p {
      font-size: 12px;
    }
  `,
  ButtonWrapper: styled.div<ButtonStyleProps>`
    opacity: 0.5;
    ${({ active }) =>
      active &&
      css`
        opacity: 1;
      `};
  `,
  Icon: styled(HomeIcon)`
    border-radius: 50%;
    padding: 0.5rem;

    transition: all ease-in;
    transition-duration: 0.2s;
    -webkit-transition-duration: 0.2s;
  `,
  IconWrapper: styled.div`
    transform: scale(1.6);
    margin-bottom: 0.5rem;
  `,
};
