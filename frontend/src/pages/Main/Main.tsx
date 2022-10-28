import styled from '@emotion/styled';
import SendIcon from '@mui/icons-material/Send';

import TabsNav from '../../components/@shared/TabsNav';

import { css } from '@emotion/react';
import { Suspense } from 'react';
import { Link } from 'react-router-dom';
import Spinner from '../../components/@shared/Spinner';
import CustomErrorBoundary from '../../errors/CustomErrorBoundary';
import ErrorFallBack from '../../errors/ErrorFallBack';
import useQRCoupon from '../../hooks/useQRCoupon';
import HeaderText from '../../layout/HeaderText';
import MainPageLayout from '../../layout/MainPageLayout';
import { couponTypeKeys, couponTypes } from '../../types/coupon';
import { palette } from './../../styles/ThemeProvider';
import GridViewCoupons from './components/GridViewCoupons';
import useMain from './hooks/useMain';

const sentOrReceivedArray = ['received', 'sent'];

const Main = () => {
  const {
    currentType,
    sentOrReceived,
    showUsedCouponsWith,
    setCurrentType,
    setSentOrReceived,
    setShowUsedCouponsWith,
  } = useMain();
  useQRCoupon();

  return (
    <MainPageLayout>
      <S.CouponStatusNavWrapper>
        <S.SliderDiv length={2} current={sentOrReceivedArray.indexOf(sentOrReceived)} />
        <S.CouponStatusNav
          onClick={() => {
            setSentOrReceived('received');
          }}
          selected={sentOrReceived === 'received'}
        >
          <S.HeaderText>받은 쿠폰함</S.HeaderText>
        </S.CouponStatusNav>
        <S.CouponStatusNav
          onClick={() => {
            setSentOrReceived('sent');
          }}
          selected={sentOrReceived === 'sent'}
        >
          <S.HeaderText>보낸 쿠폰함</S.HeaderText>
        </S.CouponStatusNav>
      </S.CouponStatusNavWrapper>
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
              checked={showUsedCouponsWith}
              onChange={() => {
                setShowUsedCouponsWith(prev => !prev);
              }}
            />
            <S.UsedCouponCheckboxLabel htmlFor='used_coupon' id='used_coupon'>
              모든 쿠폰
            </S.UsedCouponCheckboxLabel>
          </S.UsedCouponToggleForm>
        </S.TabsNavWrapper>
        <CustomErrorBoundary fallbackComponent={ErrorFallBack}>
          <Suspense fallback={<Spinner />}>
            <GridViewCoupons
              currentType={currentType}
              sentOrReceived={sentOrReceived}
              showUsedCouponsWith={showUsedCouponsWith}
            />
          </Suspense>
        </CustomErrorBoundary>
      </S.Body>
    </MainPageLayout>
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
    overflow: auto;
    height: 100%;
  `,
  CouponStatusNavWrapper: styled.div`
    position: relative;
    display: flex;
    justify-content: space-around;
    cursor: pointer;
  `,
  SliderDiv: styled.div<SliderDivProps>`
    width: ${({ length }) => `${100 / length}%`};
    height: 103%;
    border-bottom: ${palette.WHITE} solid 2px;
    position: absolute;
    left: 0;
    top: 0;

    transition: all ease-in-out 0.1s;
    left: ${({ current, length }) => `${(100 / length) * current}%`};
  `,
  CouponStatusNav: styled.div<CouponStatusNavProps>`
    width: 100%;
    display: flex;
    justify-content: center;
    padding: 1rem;
    background-color: #232323;
    ${({ selected }) =>
      !selected
        ? css`
            color: #8e8e8e;
          `
        : css`
            color: ${palette.WHITE};
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
    margin-bottom: 2.5rem;
  `,
  UsedCouponToggleForm: styled.form`
    display: flex;
    align-items: center;
  `,
  UsedCouponCheckbox: styled.input`
    margin: 0 10px 0 0;
    accent-color: tomato;
  `,
  UsedCouponCheckboxLabel: styled.label`
    font-size: 12px;
    color: ${palette.WHITE};
  `,
  SelectReceiverButton: styled(Link)``,
  SendIcon: styled(SendIcon)`
    position: absolute;
    bottom: 8rem;
    right: 4rem;
    fill: ${({ theme }) => theme.button.abled.color};
    padding: 1rem;
    border-radius: 50%;
    transform: rotate(-45deg) scale(1.4);
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
