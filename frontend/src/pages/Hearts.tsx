import styled from '@emotion/styled';
import { Link } from 'react-router-dom';
import ArrowBackButton from './../components/@shared/ArrowBackButton';
import Header from './../components/@shared/Header';
import HeaderText from './../components/@shared/HeaderText';
import PageLayout from './../components/@shared/PageLayout';

const Hearts = () => {
  return (
    <PageLayout>
      <Header>
        <Link to='/'>
          <ArrowBackButton />
        </Link>
        <HeaderText>상대방을 찔러보세요</HeaderText>
      </Header>
      <S.Body></S.Body>
    </PageLayout>
  );
};

const S = {
  Body: styled.div`
    display: block;
    overflow: hidden;
    flex-direction: column;
    gap: 1rem;
    padding: 15px 3vw;
    height: 82vh;
  `,
};

export default Hearts;
