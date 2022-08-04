import styled from '@emotion/styled';
import TabsNav from '../components/@shared/TabsNav';
import GridViewCoupons from '../components/Main/GridViewCoupons';
import useMain from '../hooks/Main/domain/useMain';

import ArrowRightIcon from '@mui/icons-material/ArrowRight';
import Header from '../components/@shared/Header';
import HeaderText from '../components/@shared/HeaderText';
import Modal from '../components/@shared/Modal';
import PageLayout from '../components/@shared/PageLayout';
import useModal from '../hooks/useModal';
import { couponTypeKeys, couponTypes } from '../types';
import BottomNavBar from './../components/@shared/BottomNavBar';
import EmptyContent from '../components/@shared/EmptyContent';
import { useState } from 'react';

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
  if (error) return <>에러뜸</>;

  return (
    <>
      <PageLayout>
        <Header>
          <div style={{ padding: '15px 0' }}></div>
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
                  setSentOrReceived('보낸');
                }}
              >
                보낸 쿠폰함
              </div>
              <hr
                style={{
                  backgroundColor: '#838383',
                  margin: '10px 0',
                  border: '0.5px solid #838383',
                }}
              />
              <div
                onClick={() => {
                  setSentOrReceived('받은');
                }}
              >
                받은 쿠폰함
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
          {orderedCoupons?.length ? <GridViewCoupons coupons={orderedCoupons} /> : <EmptyContent />}
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
    gap: 1rem;
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
  Dropdown: styled.div<DropdownProps>`
    position: absolute;
    display: ${({ show }) => (show ? 'flex' : 'none')};
    top: -40%;
    left: 100%;
    width: 138%;
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
};

export default Main;
