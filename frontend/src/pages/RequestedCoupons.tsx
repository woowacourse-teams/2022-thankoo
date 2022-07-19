import styled from '@emotion/styled';
import { Link } from 'react-router-dom';
import ArrowBackButton from '../components/@shared/ArrowBackButton';
import Header from '../components/@shared/Header';
import HeaderText from '../components/@shared/HeaderText';
import PageLayout from '../components/@shared/PageLayout';
import TabsNav from '../components/@shared/TabsNav';
import ListViewCoupon from '../components/RequestedCoupons/ListViewCoupon';
import useRequestedCoupons from '../hooks/RequestedCoupons/useRequestedCoupons';

const RequestedCoupons = () => {
  const {
    clickedCoupons,
    requestedCoupons,
    orderBy,
    setClickedCoupons,
    setOrderBy,
    orderByList,
    orderByObject,
  } = useRequestedCoupons();

  return (
    <PageLayout>
      <Header>
        <Link to='/select-receiver'>
          <ArrowBackButton />
        </Link>
        <HeaderText>요청된 쿠폰함</HeaderText>
      </Header>
      <S.Body>
        <TabsNav
          selectableTabs={orderByList}
          currentTab={orderBy}
          tabList={orderByObject}
          onChangeTab={setOrderBy}
        />
        <S.ListView>
          {requestedCoupons?.map(coupon => (
            <ListViewCoupon
              coupon={coupon}
              onClickCoupon={() => {
                setClickedCoupons(prev => [...prev, coupon.couponHistoryId]);
              }}
              isClickedCoupon={clickedCoupons.includes(coupon.couponHistoryId)}
            />
          ))}
        </S.ListView>
      </S.Body>
    </PageLayout>
  );
};

export default RequestedCoupons;

const S = {
  Body: styled.section`
    padding: 3rem 0;
    color: white;
  `,
  ListView: styled.div`
    display: flex;
    flex-direction: column;
    gap: 5px;
    padding: 10px 0;
  `,
};
