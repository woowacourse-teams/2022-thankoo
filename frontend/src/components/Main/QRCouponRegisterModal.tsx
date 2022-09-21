import styled from '@emotion/styled';
import { useMutation } from 'react-query';
import { client } from '../../apis/axios';
import { API_PATH } from '../../constants/api';
import { modalMountTime, modalUnMountTime } from '../../constants/modal';
import useModal from '../../hooks/useModal';
import { QRCouponResponse } from '../../hooks/useQRCoupon';
import { onMountToCenterModal, unMountCenterToButtomModal } from '../../styles/Animation';
import { FlexColumn } from '../../styles/mixIn';
import { couponTypes } from '../../types';

const QRCouponRegisterModal = ({
  QRCode,
  serialCode,
}: {
  QRCode: QRCouponResponse;
  serialCode: string;
}) => {
  const { modalContentRef, close } = useModal();
  const { mutate: postQRSerial } = useMutation(() =>
    client({ method: 'post', url: API_PATH.POST_QR_SERIAL, data: { serialCode } })
  );

  return (
    <S.Container ref={modalContentRef}>
      <S.Modal>
        <S.HeaderText>쿠폰을 등록하시겠습니까?</S.HeaderText>
        <S.ContentWrapper>
          <S.TitleText>보낸 사람</S.TitleText>
          <S.ContentText>{QRCode.senderName}</S.ContentText>
        </S.ContentWrapper>
        <S.ContentWrapper>
          <S.TitleText>쿠폰 종류</S.TitleText>
          <S.ContentText>{couponTypes[QRCode.couponType]}</S.ContentText>
        </S.ContentWrapper>
        <S.ButtonWrapper>
          <S.Button
            onClick={() => {
              postQRSerial();
            }}
            primary
          >
            등록
          </S.Button>
          <S.Button onClick={close}>취소</S.Button>
        </S.ButtonWrapper>
      </S.Modal>
    </S.Container>
  );
};

export default QRCouponRegisterModal;

type ButtonProps = {
  primary?: boolean;
};

const S = {
  Container: styled.section`
    position: fixed;
    top: 50%;
    left: 50%;
    z-index: 1000;
    transform: translate(-50%, -50%);
    width: 28rem;
    display: flex;
    align-items: center;
    justify-content: center;
    color: white;

    border-radius: 5px;
    background-color: #232323;
    padding: 2rem;

    &.onMount {
      animation: ${onMountToCenterModal} ${`${modalMountTime}ms`} ease-in-out;
    }
    &.unMount {
      animation: ${unMountCenterToButtomModal} ${`${modalUnMountTime}ms`} ease-in-out;
    }
  `,
  Modal: styled.div`
    ${FlexColumn}
    width: 100%;
    height: 100%;
    border-radius: 5px;
    background-color: #232323;
    padding: 1rem;
    justify-content: space-around;
    gap: 30px;
  `,
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
  ContentWrapper: styled.div`
    display: flex;
    flex-direction: column;
    gap: 5px;
  `,
  HeaderText: styled.span`
    font-size: 2rem;
  `,
  TitleText: styled.span`
    font-size: medium;
    color: #838383;
  `,
  ContentText: styled.span`
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
  Button: styled.button<ButtonProps>`
    width: 100%;
    border: none;
    border-radius: 4px;
    color: white;
    padding: 0.7rem 0;
    font-size: 1.5rem;

    background-color: ${({ theme, primary }) => (primary ? theme.primary : '#4a4a4a')};
  `,
};
