import { css } from '@emotion/react';
import styled from '@emotion/styled';
import EventAvailableIcon from '@mui/icons-material/EventAvailable';
import { useMemo } from 'react';
import { Link, useLocation } from 'react-router-dom';
import { ROUTE_PATH } from '../../constants/routes';
import { useGetMeetings } from '../../hooks/@queries/meeting';

const MeetingPageButton = () => {
  const location = useLocation();
  const { data: meetings } = useGetMeetings();
  const todayMeetingCount = useMemo(() => {
    if (!!meetings?.length) {
      return meetings.filter(meeting => meeting.isMeetingToday).length;
    }

    return 0;
  }, [meetings?.length]);

  return (
    <S.Link to={ROUTE_PATH.MEETINGS}>
      <S.ButtonWrapper active={location.pathname === ROUTE_PATH.MEETINGS}>
        <S.IconWrapper>
          <S.Icon />
        </S.IconWrapper>
        <p>약속</p>
        {todayMeetingCount !== 0 && <S.Count>{todayMeetingCount}</S.Count>}
      </S.ButtonWrapper>
    </S.Link>
  );
};

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
    position: relative;

    opacity: 0.5;

    ${({ active }) =>
      active &&
      css`
        opacity: 1;
      `};
  `,
  Icon: styled(EventAvailableIcon)`
    border-radius: 50%;
    padding: 0.5rem;

    transition: all ease-in;
    transition-duration: 0.2s;
    -webkit-transition-duration: 0.2s;
  `,
  IconWrapper: styled.div`
    transform: scale(1.5);
    margin-bottom: 0.5rem;
  `,
  Count: styled.span`
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

export default MeetingPageButton;
