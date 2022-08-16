import { css } from '@emotion/react';
import styled from '@emotion/styled';
import { useState } from 'react';
import useModal from './../../hooks/useModal';
import ProfileIcon from './../@shared/ProfileIcon';

const ProfileIconList = ['Corgi', 'Tiger', 'Dino', 'Mint', 'Otter', 'Panda', 'Skull', 'Pig'];

const SelectProfileImgModal = () => {
  const [selected, setSelected] = useState('');
  const { visible, close } = useModal();

  return (
    <S.Container show={visible}>
      <S.Wrapper>
        <S.ConfirmHeaderText>원하는 쿠폰 이미지를 선택해 주세요</S.ConfirmHeaderText>
        <S.ProfileContainer>
          {ProfileIconList.map((iconName, idx) => (
            <S.IconWrapper
              key={idx}
              isSelected={iconName === selected}
              onClick={() => {
                setSelected(iconName);
              }}
            >
              <ProfileIcon sort={iconName} size={'80px'} />
            </S.IconWrapper>
          ))}
        </S.ProfileContainer>
        <S.ButtonWrapper>
          <S.Button
            onClick={() => {
              //Todo UseMutation
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
    bottom: -100%;
    left: 50%;
    transform: translateX(-50%);
    max-width: 680px;
    width: 100%;
    height: 60vh;
    background-color: #272727;
    border-radius: 7% 7% 0 0;
    display: flex;
    z-index: 10000;
    color: white;

    ${({ show }) =>
      show &&
      css`
        bottom: 0;
      `};
  `,
  Wrapper: styled.div`
    display: flex;
    padding: 3rem 2rem;
    flex-direction: column;
    width: 100%;
    justify-content: space-between;
    box-sizing: border-box;
  `,
  ConfirmHeaderText: styled.span`
    font-size: larger;
    margin-bottom: 20px;
  `,
  ProfileContainer: styled.div`
    display: grid;
    grid-template-columns: repeat(4, 1fr);
  `,
  IconWrapper: styled.div<IconWrapperProp>`
    border-bottom: ${({ isSelected }) => (isSelected ? 'white' : 'transparent')} solid 10px;
    border-radius: 16px;
    transition: all ease-in;
    transition-duration: 0.2s;
    -webkit-transition-duration: 0.2s;

    padding-bottom: 15px;
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
