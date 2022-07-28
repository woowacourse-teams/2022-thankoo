import styled from '@emotion/styled';
import { useState } from 'react';
import { useQuery, useQueryClient } from 'react-query';
import { useNavigate } from 'react-router-dom';
import { requestInstance } from '../apis/axios';
import Header from '../components/@shared/Header';
import HeaderText from '../components/@shared/HeaderText';
import Input from '../components/@shared/Input';
import PageLayout from '../components/@shared/PageLayout';
import { ROUTE_PATH } from '../constants/routes';
import { saveAuth } from '../utils/auth';

const EnterNickname = () => {
  //useQueryClinet로 email, nickname, token
  const queryClient = useQueryClient();
  const navigate = useNavigate();
  const [nickname, setNickname] = useState('');
  // const { email, memberId } = queryClient.getQueryData('token') as any;

  // const onclick = () => {
  //   //받아온걸 바탕으로 api 요청을 보냄
  //   const data = useQuery(
  //     '닉네임입력',
  //     () => {
  //       const { data } = requestInstance({
  //         method: 'post',
  //       });

  //       return data;
  //     },
  //     {
  //       onSuccess: ({ accessToken }) => {
  //         saveAuth(accessToken);
  //         navigate(ROUTE_PATH.EXACT_MAIN); //redirect to exactMain
  //       },
  //     }
  //   );
  // };

  return (
    <S.PageLayout>
      <Header>
        <HeaderText>회원가입을 완료해주세요!</HeaderText>
      </Header>
      <S.Body>
        <S.Form>
          <S.FlexColumn>
            <S.Label htmlFor='email'>이메일</S.Label>
            <S.Email>hoho@gmail.com</S.Email>
          </S.FlexColumn>
          <S.FlexColumn>
            <S.Label htmlFor='nickname'>닉네임</S.Label>
            <Input
              id='nickname'
              value={nickname}
              setValue={setNickname}
              placeholder='닉네임을 입력해주세요'
            />
          </S.FlexColumn>
          <S.Button>회원가입 완료</S.Button>
        </S.Form>
      </S.Body>
    </S.PageLayout>
  );
};

export default EnterNickname;

const S = {
  PageLayout: styled(PageLayout)`
    padding: 10rem 0;
    gap: 50px;
  `,
  Body: styled.section`
    padding: 1rem;
    color: white;
  `,
  Form: styled.form`
    display: flex;
    flex-flow: column;
    gap: 15px;
  `,
  FlexColumn: styled.div`
    display: flex;
    flex-flow: column;
    gap: 7px;
  `,
  Email: styled.span`
    color: #8e8e8e;
    font-size: 18px;
  `,
  Label: styled.label``,
  Button: styled.button`
    color: white;
    border-radius: 10px;
    border: none;
    padding: 15px;
    font-size: 17px;
    background-color: ${({ theme }) => theme.primary};
  `,
};
