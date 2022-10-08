import { css } from '@emotion/react';
import styled from '@emotion/styled';
import ConfirmationNumberIcon from '@mui/icons-material/ConfirmationNumber';

import { Link, useLocation } from 'react-router-dom';
import { ROUTE_PATH } from '../../constants/routes';

const SendCouponPageButton = () => {
  const { pathname } = useLocation();

  return (
    <S.Link to={ROUTE_PATH.SELECT_RECEIVER}>
      <S.ButtonWrapper active={pathname === ROUTE_PATH.SELECT_RECEIVER}>
        <S.IconWrapper>
          <S.Icon />
        </S.IconWrapper>
        <S.ButtonText>보내기</S.ButtonText>
      </S.ButtonWrapper>
    </S.Link>
  );
};
export default SendCouponPageButton;

type ButtonProps = {
  active: boolean;
};

const S = {
  Link: styled(Link)`
    margin-top: 1px;
    line-height: 8px;
    p {
      font-size: 12px;
    }
  `,
  ButtonWrapper: styled.div<ButtonProps>`
    position: relative;
    opacity: 0.5;
    ${({ active }) =>
      active &&
      css`
        opacity: 1;
      `};
  `,
  Icon: styled(ConfirmationNumberIcon)`
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
  ReceivedReservationCount: styled.span`
    position: absolute;
    top: -7px;
    right: -7px;
    display: flex;
    align-items: center;
    justify-content: center;
    width: 1.3em;
    height: 1.3em;
    border-radius: 50%;
    background-color: ${({ theme }) => theme.primary};
    font-size: 1em;
  `,
  ButtonText: styled.p`
    word-break: keep-all;
  `,
};
