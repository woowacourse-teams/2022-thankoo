import styled from '@emotion/styled';
import TabsNav from '../components/@shared/TabsNav';
import GridViewCoupons from '../components/Main/GridViewCoupons';
import useMain from '../hooks/Main/useMain';

import Header from '../components/@shared/Header';
import HeaderText from '../components/@shared/HeaderText';
import Modal from '../components/@shared/Modal';
import PageLayout from '../components/@shared/PageLayout';
import useModal from '../hooks/useModal';
import { couponTypeKeys, couponTypes } from '../types';
import BottomNavBar from './../components/@shared/BottomNavBar';

const Main = () => {
  const { setCurrentType, couponsByType, isLoading, error, currentType } = useMain();
  const { visible } = useModal();

  if (isLoading) return <>로딩중</>;
  if (error) return <>에러뜸</>;

  return (
    <>
      <PageLayout>
        <Header>
          <HeaderText>쿠폰함</HeaderText>
        </Header>
        <S.Body>
          <TabsNav
            onChangeTab={setCurrentType}
            currentTab={currentType}
            tabList={couponTypes}
            selectableTabs={couponTypeKeys}
          />
          {couponsByType && <GridViewCoupons coupons={couponsByType} />}
        </S.Body>

        {visible && <Modal />}
      </PageLayout>
      <BottomNavBar />
    </>
  );
};

const S = {
  Body: styled.div`
    display: flex;
    flex-direction: column;
    gap: 1rem;
    padding: 15px;
  `,
};

export default Main;
