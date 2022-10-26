import styled from '@emotion/styled';
import Header from '../../layout/Header';
import Input from '../../components/@shared/Input';
import PageLayout from '../../layout/PageLayout';
import useEnterNickname from './hooks/useEnterNickname';
import { USER_NICKNAME_MAX_LENGTH } from '../../constants/users';
import HeaderText from '../../layout/HeaderText';
import Button from '../../components/@shared/Button/Button';

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
          <Button isDisabled={nickname.trim().length === 0} type='submit'>
            회원가입 완료
          </Button>
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
};
