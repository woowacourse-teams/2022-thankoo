import styled from '@emotion/styled';
import { modalUnMountTime } from '../../constants/modal';
import useModal from '../../hooks/useModal';
import { UserProfile } from '../../types';
import { modalMountTime } from './../../constants/modal';

const ConfirmCouponContentModal = ({
  submit,
  message,
  title,
  receivers,
}: {
  submit: () => void;
  message: string;
  title: string;
  receivers: UserProfile[];
}) => {
  const { visible, close, modalContentRef } = useModal();

  return (
    <S.Container show={visible} ref={modalContentRef}>
      <S.Wrapper>
        <S.ConfirmHeaderText>쿠폰 정보를 확인해주세요</S.ConfirmHeaderText>
        <S.ConfirmContentWrapper>
          <S.ConfirmTitleText>쿠폰 수신인</S.ConfirmTitleText>
          <S.ReceiversWrapper>
            {receivers.map((receiver, idx) => (
              <S.UserWrapper key={`${receiver}-${idx}`}>
                <S.UserImage src={receiver.imageUrl} />
                <span>{receiver.name}</span>
              </S.UserWrapper>
            ))}
          </S.ReceiversWrapper>
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
          <S.Button onClick={submit} primary>
            전송
          </S.Button>
          <S.Button onClick={close}>취소</S.Button>
        </S.ButtonWrapper>
      </S.Wrapper>
    </S.Container>
  );
};

export default ConfirmCouponContentModal;

type ConfirmCouponContentModalProps = {
  show: boolean;
};
type ButtonProps = {
  primary?: boolean;
};

const S = {
  Container: styled.div<ConfirmCouponContentModalProps>`
    position: fixed;
    bottom: 0;
    left: 50%;
    transform: translateX(-50%);
    max-width: 680px;
    width: 100%;
    height: 70vh;
    background-color: #272727;
    border-radius: 7% 7% 0 0;
    display: flex;
    z-index: 10000;
    color: white;

    //onMount animation
    @keyframes myonmount {
      0% {
        bottom: -100%;
      }
      40% {
        bottom: -60%;
      }
      100% {
        bottom: 0;
      }
    }
    &.onMount {
      animation: myonmount ${`${modalMountTime}ms`} ease-in-out;
    }

    //unMount animation
    @keyframes myunmount {
      0% {
        bottom: 0%;
      }
      100% {
        bottom: -100%;
      }
    }
    &.unMount {
      animation: myunmount ${`${modalUnMountTime}ms`} ease-in-out;
    }
  `,
  Wrapper: styled.div`
    display: flex;
    padding: 3rem 2rem;
    flex-direction: column;
    width: 100%;
    justify-content: space-between;
    box-sizing: border-box;
  `,
  ReceiversWrapper: styled.div`
    display: flex;
    justify-content: center;
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
