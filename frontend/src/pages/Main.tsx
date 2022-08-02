import styled from '@emotion/styled';
import TabsNav from '../components/@shared/TabsNav';
import GridViewCoupons from '../components/Main/GridViewCoupons';
import useMain from '../hooks/Main/useMain';

import LoopIcon from '@mui/icons-material/Loop';
import Header from '../components/@shared/Header';
import HeaderText from '../components/@shared/HeaderText';
import Modal from '../components/@shared/Modal';
import PageLayout from '../components/@shared/PageLayout';
import useModal from '../hooks/useModal';
import { couponTypeKeys, couponTypes } from '../types';
import BottomNavBar from './../components/@shared/BottomNavBar';
import EmptyContent from '../components/@shared/EmptyContent';

const Main = () => {
  const {
    setCurrentType,
    orderedCoupons,
    isLoading,
    error,
    currentType,
    sentOrReceived,
    toggleSentOrReceived,
  } = useMain();
  const { visible } = useModal();

  if (isLoading) return <>로딩중</>;
  if (error) return <>에러뜸</>;

  return (
    <>
      <PageLayout>
        <Header>
          <div style={{ padding: '15px 0' }}></div>
          <HeaderText onClick={toggleSentOrReceived}>
            {sentOrReceived} 쿠폰함
            <S.LoopIcon />
          </HeaderText>
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

const S = {
  Body: styled.div`
    display: flex;
    flex-direction: column;
    gap: 1rem;
    padding: 5px 3vw;
  `,
  LoopIcon: styled(LoopIcon)`
    font-size: 1.3rem;
    margin-top: 4px;
  `,
};

export default Main;
