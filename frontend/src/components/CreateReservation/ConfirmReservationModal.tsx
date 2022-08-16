import { css } from '@emotion/react';
import styled from '@emotion/styled';
import useModal from '../../hooks/useModal';

const ConfirmReservationModal = ({ submit, time, date, receiver }) => {
  const { visible, close } = useModal();

  return (
    <S.Container show={visible}>
      <S.Wrapper>
        <S.ConfirmHeaderText>예약 정보를 확인해주세요</S.ConfirmHeaderText>
        <S.ConfirmContentWrapper>
          <S.ConfirmTitleText>예약 수신인</S.ConfirmTitleText>
          <S.ConfirmContentText>{receiver}</S.ConfirmContentText>
        </S.ConfirmContentWrapper>
        <S.SpaceBetween>
          <S.ConfirmContentWrapper>
            <S.ConfirmTitleText>날짜</S.ConfirmTitleText>
            <S.ConfirmContentText>{date}</S.ConfirmContentText>
          </S.ConfirmContentWrapper>
          <S.ConfirmContentWrapper>
            <S.ConfirmTitleText>시간</S.ConfirmTitleText>
            <S.ConfirmContentText>{time}</S.ConfirmContentText>
          </S.ConfirmContentWrapper>
        </S.SpaceBetween>
        <S.ButtonWrapper>
          <S.Button onClick={submit} primary>
            예약
          </S.Button>
          <S.Button onClick={close}>취소</S.Button>
        </S.ButtonWrapper>
      </S.Wrapper>
    </S.Container>
  );
};

export default ConfirmReservationModal;

type ConfirmReservationModalProps = {
  show: boolean;
};
type ButtonProps = {
  primary?: boolean;
};

const S = {
  Container: styled.div<ConfirmReservationModalProps>`
    position: fixed;
    bottom: -100%;
    left: 50%;
    transform: translateX(-50%);
    max-width: 680px;
    width: 100%;
    height: 55vh;
    background-color: #272727;
    border-radius: 7% 7% 0 0;
    display: flex;
    z-index: 10000;
    color: white;

    transition: all ease-in-out 0.5s;
    ${({ show }) =>
      show &&
      css`
        bottom: 0;
      `};
  `,
  Wrapper: styled.div`
    display: flex;
    padding: 3rem 2rem;
    /* gap: 30px; */
    flex-direction: column;
    width: 100%;
    justify-content: space-between;
  `,
  ConfirmContentWrapper: styled.div`
    display: flex;
    flex-direction: column;
    gap: 5px;
  `,
  ConfirmHeaderText: styled.span`
    font-size: larger;
  `,
  ConfirmTitleText: styled.span`
    font-size: medium;
    color: #838383;
  `,
  ConfirmContentText: styled.span`
    font-size: large;
    word-break: break-all;
    line-height: 25px;
  `,
  SpaceBetween: styled.div`
    display: flex;
    justify-content: space-between;
  `,
  ButtonWrapper: styled.div`
    width: 100%;
    display: flex;
    gap: 5px;
  `,
  Button: styled.button<ButtonProps>`
    width: 100%;
    border: none;
    border-radius: 4px;
    color: white;
    padding: 0.7rem 0;

    background-color: ${({ theme, primary }) => (primary ? theme.primary : '#4a4a4a')};
  `,
};
