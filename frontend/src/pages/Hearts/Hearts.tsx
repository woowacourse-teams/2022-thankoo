import styled from '@emotion/styled';
import { Suspense, useState } from 'react';
import CustomErrorBoundary from '../../errors/CustomErrorBoundary';
import ErrorFallBack from '../../errors/ErrorFallBack';
import HeaderText from '../../layout/HeaderText';
import MainPageLayout from '../../layout/MainPageLayout';
import Spinner from '../../components/@shared/Spinner';
import UserSearchInput from '../SelectReceiver/components/UserSearchInput';
import Members from './components/Members';

const Hearts = () => {
  const [keyword, setKeyword] = useState('');

  return (
    <MainPageLayout>
      <S.Header>상대를 콕 찔러볼까요?</S.Header>
      <S.Body>
        <S.InputWrapper>
          <UserSearchInput value={keyword} setKeyword={setKeyword} />
        </S.InputWrapper>
        <CustomErrorBoundary fallbackComponent={ErrorFallBack}>
          <Suspense fallback={<Spinner />}>
            <Members searchKeyword={keyword} />
          </Suspense>
        </CustomErrorBoundary>
      </S.Body>
    </MainPageLayout>
  );
};

const S = {
  Header: styled(HeaderText)`
    color: white;
  `,
  Body: styled.div`
    height: calc(80%);
  `,
  InputWrapper: styled.div`
    margin-bottom: 1.5rem;
  `,
};

export default Hearts;
