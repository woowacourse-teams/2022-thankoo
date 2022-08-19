import styled from '@emotion/styled';
import { useState } from 'react';
import { ProfileIconList } from '../../constants/profileIcon';
import { modalMountTime, modalUnMountTime } from './../../constants/modal';
import useModal from './../../hooks/useModal';
import ProfileIcon from './../@shared/ProfileIcon';

const SelectProfileImgModal = ({ patchImageMutation }) => {
  const [selected, setSelected] = useState('');
  const { visible, close, modalContentRef } = useModal();

  return (
    <S.Container show={visible} ref={modalContentRef}>
      <S.Wrapper>
        <S.ConfirmHeaderText>원하는 프로필 이미지를 선택해 주세요</S.ConfirmHeaderText>
        <S.ProfileContainer>
          {ProfileIconList.map((imageUrl, idx) => (
            <S.IconWrapper
              key={idx}
              onClick={() => {
                setSelected(imageUrl);
              }}
            >
              <ProfileIcon src={imageUrl} size={'80px'} />
              <S.SelectIndicator isSelected={imageUrl === selected} />
            </S.IconWrapper>
          ))}
        </S.ProfileContainer>
        <S.ButtonWrapper>
          <S.Button
            onClick={() => {
              patchImageMutation(selected.split('/')[2]);
              close();
            }}
            primary
          >
            변경하기
          </S.Button>
          <S.Button onClick={close}>취소</S.Button>
        </S.ButtonWrapper>
      </S.Wrapper>
    </S.Container>
  );
};

export default SelectProfileImgModal;
type ConfirmCouponContentModalProps = {
  show: boolean;
};
type ButtonProps = {
  primary?: boolean;
};
type IconWrapperProp = {
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
  ProfileContainer: styled.div`
    display: grid;
    margin: 4rem 0;
    grid-template-columns: repeat(4, 1fr);
    grid-template-rows: repeat(2, 10rem);
  `,
  IconWrapper: styled.div`
    display: flex;
    justify-content: center;

    @media screen and (max-width: 440px) {
      transform: scale(0.4);
      margin: 0 -24px;
    }
    @media screen and (min-width: 440px) {
      transform: scale(0.6);
      margin: 20px 0;
    }
    @media screen and (min-width: 670px) {
      transform: scale(0.9);
      margin: 20px;
    }
  `,
  SelectIndicator: styled.div<IconWrapperProp>`
    position: absolute;
    bottom: -4rem;
    width: 10rem;
    height: 1rem;
    background-color: ${({ isSelected }) => (isSelected ? 'white' : 'transparent')};
    border-radius: 16px;

    transition: all ease-in;
    transition-duration: 0.2s;
    -webkit-transition-duration: 0.2s;
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
    font-size: 1.7rem;
    background-color: ${({ theme, primary }) => (primary ? theme.primary : '#4a4a4a')};
  `,
};
