import { css } from '@emotion/react';
import styled from '@emotion/styled';
import { Link, useLocation } from 'react-router-dom';
// import CouponIcon from '../../assets/images/coupons.svg';
import { ROUTE_PATH } from '../../constants/routes';
import ConfirmationNumberIcon from '@mui/icons-material/ConfirmationNumber';

const CouponsPageButton = () => {
  const location = useLocation();

  return (
    <S.Link to={ROUTE_PATH.EXACT_MAIN}>
      <S.ButtonWrapper active={location.pathname === ROUTE_PATH.EXACT_MAIN}>
        <S.IconWrapper>
          <S.Icon />
        </S.IconWrapper>
        <p>쿠폰</p>
      </S.ButtonWrapper>
    </S.Link>
  );
};

export default CouponsPageButton;

type ButtonProps = {
  active: boolean;
};

const S = {
  Link: styled(Link)`
    line-height: 8px;
    p {
      font-size: 12px;
    }
  `,
  ButtonWrapper: styled.div<ButtonProps>`
    opacity: 0.5;
    ${({ active }) =>
      active &&
      css`
        opacity: 1;
        transform: scale(1.15);
      `};
  `,
  Icon: styled(ConfirmationNumberIcon)`
    border-radius: 50%;
    padding: 0.5rem;

    transition: all ease-in;
    transition-duration: 0.2s;
    -webkit-transition-duration: 0.2s;

    &:active {
      background-color: ${({ theme }) => theme.button.active.background};
    }
  `,
  IconWrapper: styled.div`
    transform: scale(1.6);
    margin-bottom: 0.5rem;
  `,
};
