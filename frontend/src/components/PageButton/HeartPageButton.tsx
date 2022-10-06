import { css } from '@emotion/react';
import styled from '@emotion/styled';
import FavoriteIcon from '@mui/icons-material/Favorite';
import { Link, useLocation } from 'react-router-dom';
import { ROUTE_PATH } from '../../constants/routes';

const HeartPageButton = () => {
  const { pathname } = useLocation();

  return (
    <S.Link to={ROUTE_PATH.HEARTS}>
      <S.ButtonWrapper active={pathname === ROUTE_PATH.HEARTS}>
        <S.IconWrapper>
          <S.Icon />
        </S.IconWrapper>
        <p>콕</p>
      </S.ButtonWrapper>
    </S.Link>
  );
};
export default HeartPageButton;

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
        transform: scale(1.15);
      `};
  `,
  Icon: styled(FavoriteIcon)`
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
};
