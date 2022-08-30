import { css } from '@emotion/react';
import styled from '@emotion/styled';
import Header from '../components/@shared/Header';
import HeaderText from '../components/@shared/HeaderText';
import Input from '../components/@shared/Input';
import PageLayout from '../components/@shared/PageLayout';
import useEnterNickname from '../hooks/EnterNickname/useEnterNickname';
import { USER_NICKNAME_MAX_LENGTH } from './../constants/users';

const EnterNickname = () => {
  const { email, nickname, setNickname, signUpWithNickname } = useEnterNickname();

  return (
    <S.PageLayout>
      <Header>
        <HeaderText>회원가입을 완료해주세요!</HeaderText>
      </Header>
      <S.Body>
        <S.Form onSubmit={signUpWithNickname}>
          <S.FlexColumn>
            <S.Label htmlFor='email'>이메일</S.Label>
            <S.Email>{email}</S.Email>
          </S.FlexColumn>
          <S.FlexColumn>
            <S.Label htmlFor='nickname'>닉네임</S.Label>
            <Input
              type='text'
              id='nickname'
              value={nickname}
              setValue={setNickname}
              placeholder='닉네임을 입력해주세요'
              maxLength={USER_NICKNAME_MAX_LENGTH}
            />
          </S.FlexColumn>
          <S.Button disabled={nickname.trim().length === 0} type='submit'>
            회원가입 완료
          </S.Button>
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
    gap: 25px;
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
  Label: styled.label`
    font-size: 1.7rem;
  `,
  Button: styled.button`
    color: white;
    border-radius: 10px;
    border: none;
    padding: 8px;
    font-size: 17px;
    background-color: ${({ theme }) => theme.primary};
    ${({ disabled, theme }) =>
      disabled
        ? css`
            background-color: ${theme.button.disbaled.background};
            color: ${theme.button.disbaled.color};
            cursor: not-allowed;
          `
        : css`
            background-color: ${theme.button.active.background};
            color: ${theme.button.active.color};
          `}
  `,
};
