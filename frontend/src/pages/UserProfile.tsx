import styled from '@emotion/styled';
import { Suspense } from 'react';
import { Link } from 'react-router-dom';
import ArrowBackButton from '../components/@shared/ArrowBackButton';
import SignOutButton from '../components/Profile/SignOutButton';
import CustomErrorBoundary from '../errors/CustomErrorBoundary';
import Header from '../layout/Header';
import HeaderText from '../layout/HeaderText';
import PageLayout from '../layout/PageLayout';
import Spinner from './../components/@shared/Spinner';
import ProfileInfo from './../components/Profile/ProfileInfo';
import ErrorFallBack from './../errors/ErrorFallBack';

const UserProfile = () => {
  return (
    <PageLayout>
      <Header>
        <Link to='/'>
          <ArrowBackButton />
        </Link>
        <S.SubHeader>
          <HeaderText>내 정보</HeaderText>
          <SignOutButton />
        </S.SubHeader>
      </Header>
      <CustomErrorBoundary fallbackComponent={ErrorFallBack}>
        <Suspense fallback={<Spinner />}>
          <ProfileInfo />
        </Suspense>
      </CustomErrorBoundary>
    </PageLayout>
  );
};

export default UserProfile;

const S = {
  SubHeader: styled.div`
    display: flex;
    justify-content: space-between;
    width: 100%;
  `,
};
