import styled from '@emotion/styled';
import SendIcon from '@mui/icons-material/Send';

import TabsNav from '../components/@shared/TabsNav';
import GridViewCoupons from '../components/Main/GridViewCoupons';
import useMain from '../hooks/Main/domain/useMain';

import ArrowRightIcon from '@mui/icons-material/ArrowRight';
import { useState } from 'react';
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

const Main = () => {
  const {
    setCurrentType,
    orderedCoupons,
    isLoading,
    error,
    currentType,
    sentOrReceived,
    setSentOrReceived,
  } = useMain();
  const { visible } = useModal();
  const [dropdownShow, setDropdownShow] = useState(false);

  if (isLoading) return <div>로딩중</div>;
  if (error) return <div>에러뜸</div>;

  return (
    <>
      <PageLayout>
        <Header>
          <S.UserProfile>
            <UserProfileButton />
          </S.UserProfile>
          <S.HeaderText
            onClick={() => {
              setDropdownShow(prev => !prev);
            }}
          >
            {sentOrReceived} 쿠폰함
            <ArrowRightIcon />
            <S.Dropdown show={dropdownShow}>
              <div
                onClick={() => {
                  setSentOrReceived(prev => (prev === '보낸' ? '받은' : '보낸'));
                }}
              >
                {sentOrReceived === '보낸' ? '받은' : '보낸'} 쿠폰함
              </div>
            </S.Dropdown>
          </S.HeaderText>
        </Header>
        <S.Body>
          <TabsNav
            onChangeTab={setCurrentType}
            currentTab={currentType}
            tabList={couponTypes}
            selectableTabs={couponTypeKeys}
          />
          {orderedCoupons?.length ? (
            <GridViewCoupons coupons={orderedCoupons} />
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

type DropdownProps = {
  show: boolean;
};

const S = {
  Body: styled.div`
    display: flex;
    flex-direction: column;
    gap: 15px;
    padding: 5px 3vw;
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
  UserProfile: styled.div`
    width: 100%;
    display: flex;
    justify-content: flex-end;
  `,
  Dropdown: styled.div<DropdownProps>`
    position: absolute;
    display: ${({ show }) => (show ? 'flex' : 'none')};
    top: -30%;
    left: 82%;
    width: 100%;
    justify-content: center;
    & > div {
      font-size: 14px;
      padding: 15px 10px;
      border-radius: 2px;
      display: flex;
      align-items: center;
      :hover {
        background-color: #ff6347;
      }
    }
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
