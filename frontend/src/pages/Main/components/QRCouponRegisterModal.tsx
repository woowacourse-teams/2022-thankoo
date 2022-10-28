import styled from '@emotion/styled';
import { AxiosError } from 'axios';
import { useMutation, useQueryClient } from 'react-query';
import { useNavigate } from 'react-router-dom';
import { client } from '../../../apis/axios';
import Button from '../../../components/@shared/Button/Button';
import { API_PATH } from '../../../constants/api';
import { modalMountTime, modalUnMountTime } from '../../../constants/modal';
import { ROUTE_PATH } from '../../../constants/routes';
import { COUPON_QUERY_KEY } from '../../../hooks/@queries/coupon';
import useModal from '../../../hooks/useModal';
import { QRCouponResponse } from '../../../hooks/useQRCoupon';
import useToast from '../../../hooks/useToast';
import { onMountToCenterModal, unMountCenterToButtomModal } from '../../../styles/Animation';
import { FlexColumn } from '../../../styles/mixIn';
import { ErrorType } from '../../../types/api';
import { couponTypes } from '../../../types/coupon';
import { palette } from './../../../styles/ThemeProvider';

const QRCouponRegisterModal = ({
  QRCode,
  serialCode,
}: {
  QRCode: QRCouponResponse;
  serialCode: string;
}) => {
  const { modalContentRef, closeModal } = useModal();
  const { insertToastItem } = useToast();
  const navigate = useNavigate();
  const queryClient = useQueryClient();

  const { mutate: postQRSerial } = useMutation(
    () =>
      client({
        method: 'post',
        url: API_PATH.POST_QR_SERIAL,
        data: { serialCode },
      }),
    {
      onMutate: () => {
        closeModal();
      },
      onSuccess: () => {
        localStorage.removeItem('query');
        insertToastItem('등록이 완료됐습니다!');
        queryClient.invalidateQueries([COUPON_QUERY_KEY.coupon]);
        navigate(ROUTE_PATH.EXACT_MAIN, { replace: true });
      },
      onError: (error: AxiosError<ErrorType>) => {
        localStorage.removeItem('query');
        alert(error.response?.data.message);
        queryClient.invalidateQueries([COUPON_QUERY_KEY.coupon]);
        navigate(ROUTE_PATH.EXACT_MAIN, { replace: true });
      },
      retry: false,
    }
  );

  return (
    <S.Container ref={modalContentRef}>
      <S.Modal>
        <S.HeaderText>쿠폰을 등록하시겠습니까?</S.HeaderText>
        <S.ContentWrapper>
          <S.TitleText>그룹</S.TitleText>
          <S.ContentText>{QRCode.organizationName}</S.ContentText>
        </S.ContentWrapper>
        <S.ContentWrapper>
          <S.TitleText>보낸 사람</S.TitleText>
          <S.ContentText>{QRCode.senderName}</S.ContentText>
        </S.ContentWrapper>
        <S.ContentWrapper>
          <S.TitleText>쿠폰 종류</S.TitleText>
          <S.ContentText>{couponTypes[QRCode.couponType.toLocaleLowerCase()]}</S.ContentText>
        </S.ContentWrapper>
        <S.ButtonWrapper>
          <Button
            color='secondaryLight'
            onClick={() => {
              closeModal();
              localStorage.removeItem('query');
              navigate(ROUTE_PATH.EXACT_MAIN, { replace: true });
            }}
          >
            취소
          </Button>
          <Button
            onClick={() => {
              postQRSerial();
            }}
          >
            등록
          </Button>
        </S.ButtonWrapper>
      </S.Modal>
    </S.Container>
  );
};

export default QRCouponRegisterModal;

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
    color: ${palette.WHITE};

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
};
