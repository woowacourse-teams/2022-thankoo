import styled from '@emotion/styled';
import useModal from '../../hooks/useModal';
import Button from '../@shared/Button';
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
        <Button onClick={close} color='secondaryLight'>
          취소
        </Button>
        <Button onClick={submit}>예약</Button>
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
    font-size: 2rem;
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
};
