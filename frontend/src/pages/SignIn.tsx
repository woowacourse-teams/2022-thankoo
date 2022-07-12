import styled from '@emotion/styled';
import { Link, useNavigate } from 'react-router-dom';
import axios from 'axios';
import { useState } from 'react';
import { authAtom } from '../recoil/atom';
import { useRecoilState } from 'recoil';
import { BASE_URL } from '../constants';

const SignIn = () => {
  const [auth, setAuth] = useRecoilState(authAtom);
  const [id, setId] = useState('');
  const navigate = useNavigate();

  const login = async (e, id) => {
    e.preventDefault();

    try {
      const { data, statusText } = await axios({
        method: 'GET',
        url: `${BASE_URL}/api/sign-in?code=${id}`,
      });
      const { accessToken, memberId } = data;
      await setAuth({ accessToken, memberId, name: id });
      navigate('/');
    } catch (error) {
      alert('잘못된 유저 정보');
    }
  };

  return (
    <S.Container>
      <S.Header>
        <S.HeaderText>로그인 하시겠어요?</S.HeaderText>
      </S.Header>
      <S.Body>
        <S.AutoForm onSubmit={e => login(e, id)}>
          <S.AuthInput
            placeholder='아이디'
            onChange={e => {
              setId(e.target.value);
            }}
            value={id}
          />
          <S.AuthInput type='password' placeholder='비밀번호' />
          <S.SignInButton>확인</S.SignInButton>
        </S.AutoForm>
      </S.Body>
    </S.Container>
  );
};

const S = {
  Container: styled.div`
    display: flex;
    flex-direction: column;
    padding: 5px;
    justify-content: center;
    height: 100vh;
    gap: 30px;
    width: 80vw;
    margin: 0 auto;
  `,
  Header: styled.header`
    display: flex;
    flex-direction: column;
    align-items: flex-start;
    gap: 15px;
    color: white;
    text-align: left;
  `,
  HeaderText: styled.p`
    font-size: 25px;
  `,
  Body: styled.div`
    text-align: left;
    width: 80vw;
  `,
  AutoForm: styled.form`
    display: flex;
    flex-direction: column;
    gap: 1rem;
  `,
  AuthInput: styled.input`
    background-color: #8c888866;
    box-shadow: none;
    max-width: 400px;
    outline: none;
    border: none;
    padding: 10px;
    border-radius: 5px;
    color: white;
    font-size: 15px;
    &::placeholder {
      color: #8e8e8e;
    }
  `,
  SignInButton: styled.button`
    background-color: #ff6450;
    max-width: 420px;
    border: none;
    color: white;
    padding: 10px;
    border-radius: 10px;
  `,
};

export default SignIn;
