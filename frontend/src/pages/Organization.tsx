import { css } from '@emotion/react';
import styled from '@emotion/styled';
import PageLayout from '../layout/PageLayout';
import BirdLogoWhite from '../components/@shared/LogoWhite';
import { FlexCenter } from '../styles/mixIn';

const Organization = () => {
  return (
    <PageLayout>
      <S.Header>
        <BirdLogoWhite size={'70px'} />
        <S.HeaderText>땡쿠</S.HeaderText>
      </S.Header>
      <div style={{}}>땡쿠에서 우테코 4기 팀에 참여</div>
    </PageLayout>
  );
};
export default Organization;

const S = {
  Container: styled(PageLayout)`
    align-items: center;
    justify-content: center;
    color: white;
  `,
  Header: styled.header`
    padding: 5rem 5rem 5rem 3rem;

    display: flex;
    justify-content: center;
    align-items: center;
    gap: 1rem;
  `,
  HeaderText: styled.h1`
    font-size: 3rem;
    color: white;
    font-weight: bold;
  `,
};
