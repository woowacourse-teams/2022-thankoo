import styled from '@emotion/styled';
import useModal from '../../hooks/useModal';
import BottomSheetLayout from '../@shared/Modal/BottomSheetLayout';

const ConfirmReservationModal = ({ submit, time, date, receiver }) => {
  const { close, modalContentRef } = useModal();

  return (
    <BottomSheetLayout ref={modalContentRef}>
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
    </BottomSheetLayout>
  );
};

export default ConfirmReservationModal;

type ButtonProps = {
  primary?: boolean;
};

const S = {
  ConfirmContentWrapper: styled.div`
    display: flex;
    flex-direction: column;
    gap: 5px;
  `,
  ConfirmHeaderText: styled.span`
    font-size: 1.5rem;
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
    font-size: 1.5rem;
    padding: 1rem 0;

    background-color: ${({ theme, primary }) => (primary ? theme.primary : '#4a4a4a')};
  `,
};
