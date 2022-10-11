import styled from '@emotion/styled';
import useModal from '../../hooks/useModal';
import { CouponType, couponTypes } from '../../types/coupon';
import { UserProfile } from '../../types/user';
import Button from '../@shared/Button';
import BottomSheetLayout from '../@shared/Modal/BottomSheetLayout';
import { BASE_URL } from './../../constants/api';

const ConfirmCouponContentModal = ({
  submit,
  message,
  title,
  receivers,
  couponType,
}: {
  submit: () => void;
  message: string;
  title: string;
  receivers: UserProfile[];
  couponType: CouponType;
}) => {
  const { close, modalContentRef } = useModal();

  return (
    <BottomSheetLayout ref={modalContentRef}>
      <S.ConfirmHeaderText>쿠폰 정보를 확인해주세요</S.ConfirmHeaderText>
      <S.ConfirmContentWrapper>
        <S.ConfirmTitleText>쿠폰 수신인</S.ConfirmTitleText>
        <S.ReceiversWrapper>
          {receivers.map((receiver, idx) => (
            <S.UserWrapper key={`${receiver}-${idx}`}>
              <S.UserImage src={`${BASE_URL}${receiver.imageUrl}`} />
              <S.UserName>{receiver.name}</S.UserName>
            </S.UserWrapper>
          ))}
        </S.ReceiversWrapper>
      </S.ConfirmContentWrapper>
      <S.ConfirmContentWrapper>
        <S.ConfirmTitleText>쿠폰 종류</S.ConfirmTitleText>
        <S.ConfirmContentText>{couponTypes[couponType]}</S.ConfirmContentText>
      </S.ConfirmContentWrapper>
      <S.ConfirmContentWrapper>
        <S.ConfirmTitleText>제목</S.ConfirmTitleText>
        <S.ConfirmContentText>{title}</S.ConfirmContentText>
      </S.ConfirmContentWrapper>
      <S.ConfirmContentWrapper>
        <S.ConfirmTitleText>메세지</S.ConfirmTitleText>
        <S.ConfirmContentText>{message}</S.ConfirmContentText>
      </S.ConfirmContentWrapper>
      <S.ButtonWrapper>
        <Button color='secondary' onClick={close}>
          취소
        </Button>
        <Button onClick={submit}>전송</Button>
      </S.ButtonWrapper>
    </BottomSheetLayout>
  );
};

export default ConfirmCouponContentModal;

type ButtonProps = {
  primary?: boolean;
};

const S = {
  ReceiversWrapper: styled.div`
    display: flex;
    gap: 15px;
    overflow-x: auto;
    overflow-y: hidden;
  `,
  UserWrapper: styled.div`
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    gap: 5px;
  `,
  UserImage: styled.img`
    width: 30px;
    height: 30px;
    object-fit: cover;
    border-radius: 50%;
  `,
  UserName: styled.span`
    word-break: keep-all;
    font-size: 1.3rem;
  `,
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
    font-size: 1.5rem;
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
