import styled from '@emotion/styled';
import { Link } from 'react-router-dom';
import defaultUser from '../assets/images/default_user.jpeg';
import ArrowBackButton from '../components/@shared/ArrowBackButton';
import Header from '../components/@shared/Header';
import HeaderText from '../components/@shared/HeaderText';
import Input from '../components/@shared/Input';
import PageLayout from '../components/@shared/PageLayout';
import ProfileUserImage from '../components/Profile/ProfileUserImage';
import SignOutButton from '../components/Profile/SignOutButton';
import useUserProfile from '../hooks/Profile/useUserProfile';
import { USER_NICKNAME_MAX_LENGTH } from './../constants/users';

const UserProfile = () => {
  const { profile, isNameEdit, name, handleClickModifyNameButton, exchangeCount, setName } =
    useUserProfile();

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
      <S.Body>
        <ProfileUserImage src={profile ? `${profile?.imageUrl}` : defaultUser} />
        <S.UserInfoBox>
          <S.UserInfoItem>
            <S.Bold>이름</S.Bold>
            {isNameEdit ? (
              <Input
                type='text'
                value={name}
                setValue={setName}
                placeholder='이름을 입력해주세요'
                onKeyDown={e => {
                  if (e.code === 'Enter') {
                    handleClickModifyNameButton();
                  }
                }}
                maxLength={USER_NICKNAME_MAX_LENGTH}
              />
            ) : (
              <span>{name}</span>
            )}
            <S.ModifyNameButton onClick={handleClickModifyNameButton}>
              {isNameEdit ? '저장' : '수정'}
            </S.ModifyNameButton>
          </S.UserInfoItem>
          <S.UserInfoItem>
            <S.Bold>이메일</S.Bold>
            <span>{profile?.email}</span>
          </S.UserInfoItem>
        </S.UserInfoBox>
        <S.UserCouponInfo>
          <S.UserCouponInfoItem>
            <span>보낸 쿠폰 수</span>
            <span>{exchangeCount.sentCount}</span>
          </S.UserCouponInfoItem>
          <S.UserCouponInfoItem>
            <span>받은 쿠폰 수</span>
            <span>{exchangeCount.receivedCount}</span>
          </S.UserCouponInfoItem>
        </S.UserCouponInfo>
      </S.Body>
    </PageLayout>
  );
};

export default UserProfile;

const S = {
  Body: styled.div`
    display: flex;
    flex-direction: column;
    height: 50%;
    margin: 0 3vw;

    span {
      font-size: 1.6rem;
    }
  `,
  UserInfoBox: styled.div`
    display: flex;
    flex-direction: column;
    padding-bottom: 1.5rem;
    gap: 1rem;
    margin: 3rem 0 2rem;
    border-bottom: ${({ theme }) => `1px solid ${theme.primary}`};
  `,
  UserInfoItem: styled.div`
    display: grid;
    grid-template-columns: 30% 50% 20%;
    align-items: center;
    color: white;
    height: 40px;
  `,
  Bold: styled.span`
    font-weight: 700;
    font-size: 1.6rem;
  `,
  ModifyNameButton: styled.button`
    background-color: transparent;
    font-size: 1.6rem;
    border: none;
    font-weight: 600;
    color: ${({ theme }) => theme.primary};
    text-align: right;
  `,
  UserCouponInfo: styled.div`
    display: flex;
    flex-direction: column;
    align-items: space-between;
    gap: 2rem;
  `,
  UserCouponInfoItem: styled.div`
    display: flex;
    justify-content: space-between;
    color: white;
  `,
  SubHeader: styled.div`
    display: flex;
    justify-content: space-between;
    width: 100%;
  `,
};
