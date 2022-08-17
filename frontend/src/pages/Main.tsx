import styled from '@emotion/styled';
import SendIcon from '@mui/icons-material/Send';

import TabsNav from '../components/@shared/TabsNav';
import GridViewCoupons from '../components/Main/GridViewCoupons';
import useMain from '../hooks/Main/domain/useMain';

import { Link } from 'react-router-dom';
import Header from '../components/@shared/Header';
import HeaderText from '../components/@shared/HeaderText';
import Modal from '../components/@shared/Modal';
import PageLayout from '../components/@shared/PageLayout';
import UserProfileButton from '../components/@shared/UserProfileButton';
import BottomNavBar from '../components/PageButton/BottomNavBar';
import { ROUTE_PATH } from '../constants/routes';
import useModal from '../hooks/useModal';
import { couponTypeKeys, couponTypes } from '../types';
import NoReceivedCoupon from './../components/@shared/noContent/NoReceivedCoupon';
import NoSendCoupon from './../components/@shared/noContent/NoSendCoupon';
import { css } from '@emotion/react';

const sentOrReceivedArray = ['받은', '보낸'];

const Main = () => {
  const {
    setCurrentType,
    coupons,
    isLoading,
    error,
    currentType,
    sentOrReceived,
    setSentOrReceived,
    showAllCouponsToggle,
    setShowAllCouponsToggle,
  } = useMain();

  const { visible } = useModal();

  if (isLoading) return <div>로딩중</div>;
  if (error) return <div>에러뜸</div>;

  return (
    <>
      <PageLayout>
        <Header>
          <S.UserProfile>
            <UserProfileButton />
          </S.UserProfile>
          <S.CouponStatusNavWrapper>
            <S.SliderDiv length={2} current={sentOrReceivedArray.indexOf(sentOrReceived)} />
            <S.CouponStatusNav
              onClick={() => {
                setSentOrReceived('받은');
              }}
              selected={sentOrReceived === '받은'}
            >
              <S.HeaderText>받은 쿠폰함</S.HeaderText>
            </S.CouponStatusNav>
            <S.CouponStatusNav
              onClick={() => {
                setSentOrReceived('보낸');
              }}
              selected={sentOrReceived === '보낸'}
            >
              <S.HeaderText>보낸 쿠폰함</S.HeaderText>
            </S.CouponStatusNav>
          </S.CouponStatusNavWrapper>
        </Header>
        <S.Body>
          <S.TabsNavWrapper>
            <TabsNav
              onChangeTab={setCurrentType}
              currentTab={currentType}
              tabList={couponTypes}
              selectableTabs={couponTypeKeys}
            />
            <S.UsedCouponToggleForm>
              <S.UsedCouponCheckbox
                type='checkbox'
                id='used_coupon'
                checked={showAllCouponsToggle}
                onChange={() => {
                  setShowAllCouponsToggle(prev => !prev);
                }}
              />
              <S.UsedCouponCheckboxLabel htmlFor='used_coupon' id='used_coupon'>
                모든 쿠폰
              </S.UsedCouponCheckboxLabel>
            </S.UsedCouponToggleForm>
          </S.TabsNavWrapper>
          {coupons?.length ? (
            <GridViewCoupons coupons={coupons} />
          ) : sentOrReceived === '보낸' ? (
            <NoSendCoupon />
          ) : (
            <NoReceivedCoupon />
          )}
          <S.SelectReceiverButton to={ROUTE_PATH.SELECT_RECEIVER}>
            <S.SendIcon />
          </S.SelectReceiverButton>
        </S.Body>

        {visible && <Modal />}
      </PageLayout>
      <BottomNavBar />
    </>
  );
};
type SliderDivProps = {
  length: number;
  current: number;
};

type CouponStatusNavProps = {
  selected: boolean;
};

const S = {
  Body: styled.div`
    display: flex;
    flex-direction: column;
    gap: 15px;
    padding: 5px 3vw;
  `,
  CouponStatusNavWrapper: styled.div`
    position: relative;
    display: flex;
    justify-content: space-around;
    width: 100%;
    cursor: pointer;
  `,
  SliderDiv: styled.div<SliderDivProps>`
    width: ${({ length }) => `${100 / length}%`};
    height: 103%;
    background-color: white;
    position: absolute;
    left: 0;
    top: 0;

    transition: all ease-in-out 0.1s;
    left: ${({ current, length }) => `${(100 / length) * current}%`};
  `,
  CouponStatusNav: styled.div<CouponStatusNavProps>`
    display: flex;
    justify-content: center;
    z-index: 1;
    width: 100%;
    padding: 1rem;
    background-color: #232323;
    ${({ selected }) =>
      !selected &&
      css`
        color: #8e8e8e;
      `};
  `,
  HeaderText: styled(HeaderText)`
    cursor: pointer;
    position: relative;
    //드래그 금지
    -webkit-touch-callout: none;
    -webkit-user-select: none;
    -khtml-user-select: none;
    -moz-user-select: none;
    -ms-user-select: none;
    user-select: none;
  `,
  TabsNavWrapper: styled.div`
    display: flex;
    justify-content: space-between;
  `,
  UserProfile: styled.div`
    width: 100%;
    display: flex;
    justify-content: flex-end;
  `,
  UsedCouponToggleForm: styled.form`
    display: flex;
    align-items: center;
  `,
  UsedCouponCheckbox: styled.input`
    margin: 0 10px 0 0;
  `,
  UsedCouponCheckboxLabel: styled.label`
    font-size: 12px;
    color: white;
  `,
  SelectReceiverButton: styled(Link)``,
  SendIcon: styled(SendIcon)`
    position: absolute;
    bottom: 100px;
    right: 20px;
    fill: ${({ theme }) => theme.button.abled.color};
    padding: 0.7rem;
    border-radius: 50%;
    transform: rotate(-45deg) scale(1);
    opacity: 0.9;
    cursor: pointer;
    transition: all ease-in-out 0.2s;
    background-color: #4a4a4a;
    z-index: 100;

    &:hover {
      background-color: ${({ theme }) => theme.button.active.background};
      fill: ${({ theme }) => theme.button.active.color};
      border-color: transparent;
      opacity: 1;
    }
  `,
};

export default Main;
