import { css } from '@emotion/react';
import styled from '@emotion/styled';
import CheckIcon from '@mui/icons-material/Check';
import { useState } from 'react';
import Button from '../../../components/@shared/Button/Button';
import Avatar from '../../../components/Avatar';
import { modalMountTime, modalUnMountTime } from '../../../constants/modal';
import { ProfileIconList } from '../../../constants/profileIcon';
import useModal from '../../../hooks/useModal';

const SelectProfileImgModal = ({ editUserProfileImage }) => {
  const [selected, setSelected] = useState('');
  const { visible, closeModal, modalContentRef } = useModal();

  return (
    <S.Container show={visible} ref={modalContentRef}>
      <S.Wrapper>
        <S.ConfirmHeaderText>원하는 프로필 이미지를 선택해 주세요</S.ConfirmHeaderText>
        <S.ProfileContainer>
          {ProfileIconList.map((imageUrl, idx) => (
            <S.AvatarWrapper
              key={idx}
              isSelected={imageUrl === selected}
              onClick={() => {
                setSelected(imageUrl);
              }}
            >
              <Avatar src={imageUrl} size={80} alt={`프로필 선택-${idx}`} />
            </S.AvatarWrapper>
          ))}
        </S.ProfileContainer>
        <S.ButtonWrapper>
          <Button color='secondaryLight' onClick={closeModal}>
            취소
          </Button>
          <Button
            onClick={() => {
              editUserProfileImage(selected);
              closeModal();
            }}
          >
            변경하기
          </Button>
        </S.ButtonWrapper>
      </S.Wrapper>
    </S.Container>
  );
};

export default SelectProfileImgModal;
type ConfirmCouponContentModalProps = {
  show: boolean;
};

type AvatarWrapperProp = {
  isSelected: boolean;
};

const S = {
  Container: styled.div<ConfirmCouponContentModalProps>`
    position: fixed;
    bottom: 0;
    left: 50%;
    transform: translateX(-50%);
    max-width: 680px;
    width: 100%;
    height: fit-content;
    max-height: 80%;
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
        bottom: -50%;
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
    padding: 4rem 2rem;
    flex-direction: column;
    width: 100%;
    justify-content: space-between;
    box-sizing: border-box;
  `,
  ConfirmHeaderText: styled.span`
    font-size: larger;
    text-align: center;
    font-size: 2rem;
  `,
  AvatarWrapper: styled.div<AvatarWrapperProp>`
    border-radius: 50%;
    cursor: pointer;
    width: 105px;
    height: 105px;
    display: flex;
    align-items: center;
    justify-content: center;
    ${({ isSelected }) =>
      isSelected &&
      css`
        background-color: #ff6347bd;
      `}

    transition:all ease-in-out 0.1s;

    @media screen and (max-width: 440px) {
      transform: scale(0.4);
      margin: 0 -24px;
    }
    @media screen and (min-width: 440px) {
      transform: scale(0.6);
      margin: 20px 0;
    }
    @media screen and (min-width: 670px) {
      transform: scale(0.8);
      margin: 20px 0;
    }
  `,
  ProfileContainer: styled.div`
    display: grid;
    grid-template-columns: repeat(4, 1fr);
    grid-template-rows: repeat(2, 10rem);

    place-items: center;

    @media screen and (max-width: 440px) {
      padding: 1rem 0;
    }
    @media screen and (min-width: 440px) {
      padding: 2rem 0;
    }
    @media screen and (min-width: 670px) {
      padding: 4rem 0;
      row-gap: 2rem;
    }
  `,
  CheckIcon: styled(CheckIcon)`
    width: 4rem;
    height: 4rem;
  `,
  ButtonWrapper: styled.div`
    width: 100%;
    display: flex;
    gap: 5px;
  `,
};
