import { css } from '@emotion/react';
import styled from '@emotion/styled';
import { Link, useLocation } from 'react-router-dom';
import CouponIcon from '../../assets/images/coupons.svg';
import { ROUTE_PATH } from '../../constants/routes';

const CouponsPageButton = () => {
  const location = useLocation();

  return (
    <S.Link to={ROUTE_PATH.EXACT_MAIN}>
      <S.ButtonWrapper active={location.pathname === ROUTE_PATH.EXACT_MAIN}>
        <S.IconWrapper active={location.pathname === ROUTE_PATH.EXACT_MAIN}>
          <S.Icon src={CouponIcon} alt='coupon_page_butotn' />
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
      `};
  `,
  IconWrapper: styled.div<ButtonProps>`
    border-radius: 50%;
    width: 44px;
    height: 44px;
    transition: all ease-in-out 0.2s;
    margin: auto;
    transform: scale(0.75);
  `,
  Icon: styled.img`
    /* padding: 1rem; */
    fill: white;
    color: white;
  `,
};
