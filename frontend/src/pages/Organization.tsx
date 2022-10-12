import styled from '@emotion/styled';
import PageLayout from '../layout/PageLayout';
import BirdLogoWhite from '../components/@shared/LogoWhite';
import { css } from '@emotion/react';
import { ORGANIZATIONS } from '../routes/Router';
import Button from '../components/@shared/Button';

const Organization = () => {
  const organization = ORGANIZATIONS[0];

  return (
    <S.Container>
      <S.Inner>
        <S.Header>
          <BirdLogoWhite size={'60px'} />
          <S.HeaderText>Thankoo</S.HeaderText>
        </S.Header>
        <S.Body>
          <S.Description>
            <S.OrganiztionDescription>
              <S.Organization>{organization.organizationName}</S.Organization>의 멤버가 되어보세요
            </S.OrganiztionDescription>
            <S.ServiceDescription>
              땡쿠는 멤버들 간 쿠폰을 주고 받을 수 있는 앱입니다.
            </S.ServiceDescription>
          </S.Description>
          <div style={{ display: 'flex', flexFlow: 'column' }}>
            <span>현재 멤버 : 97명</span>
            <span>후이 님, 호호 님 외 95명의 다른 사용자가 이미 참여했습니다.</span>
          </div>
        </S.Body>
        <S.JoinSection>
          <Button>{organization.organizationName}에 참여하기</Button>
        </S.JoinSection>
      </S.Inner>
    </S.Container>
  );
};
export default Organization;

const S = {
  Container: styled(PageLayout)`
    height: 100%;
    padding: 0 1rem;
    gap: 3rem;
  `,
  Inner: styled.main`
    height: 100%;
    display: flex;
    flex-direction: column;
    padding: 5rem 0;
    gap: 3rem;
  `,
  Header: styled.header`
    height: 20%;

    display: flex;
    justify-content: center;
    align-items: center;
    gap: 1rem;
  `,
  HeaderText: styled.h1`
    font-size: 2.8rem;
    color: white;
    font-weight: bold;
  `,
  Body: styled.article`
    display: flex;
    flex-flow: column;
    align-items: center;
    color: white;
    text-align: center;
    gap: 6rem;
    font-size: 1.5rem;
  `,
  Description: styled.section`
    display: flex;
    flex-flow: column;
    gap: 1.5rem;
  `,
  OrganiztionDescription: styled.span`
    font-size: 4rem;
    font-weight: bold;
    text-align: center;
    word-break: keep-all;
  `,
  Organization: styled.span`
    /* color: white; */
  `,
  ServiceDescription: styled.span`
    font-size: 1.8rem;
    word-break: keep-all;
  `,
  JoinSection: styled.section`
    display: grid;
    place-items: center;
    width: 100%;
  `,
};
