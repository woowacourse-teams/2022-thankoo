import { css } from '@emotion/react';
import styled from '@emotion/styled';
import { Link, useLocation } from 'react-router-dom';
import CouponIcon from '../../assets/images/coupons.svg';
import { ROUTE_PATH } from '../../constants/routes';

const CouponsPageButton = () => {
  const location = useLocation();

  return (
    <Link to={ROUTE_PATH.EXACT_MAIN}>
      <S.IconWrapper active={location.pathname === ROUTE_PATH.EXACT_MAIN}>
        <S.Icon src={CouponIcon} alt='coupon_page_butotn' />
      </S.IconWrapper>
    </Link>
  );
};

export default CouponsPageButton;

type ButtonProps = {
  active: boolean;
};

const S = {
  IconWrapper: styled.div<ButtonProps>`
    border-radius: 50%;
    width: 44px;
    height: 44px;
    transition: all ease-in-out 0.2s;
    ${({ active }) =>
      active &&
      css`
        background-color: tomato;
      `};
  `,
  Icon: styled.img`
    transform: scale(0.6);
    /* padding: 1rem; */
    fill: white;
    color: white;
  `,
};
