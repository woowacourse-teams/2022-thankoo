import styled from '@emotion/styled';
import PageLayout from '../layout/PageLayout';
import BirdLogoWhite from '../components/@shared/LogoWhite';
import Button from '../components/@shared/Button';
import { useNavigate } from 'react-router-dom';
import Input from '../components/@shared/Input';
import { useState, FormEvent } from 'react';
import { ROUTE_PATH } from '../constants/routes';
import useToast from '../hooks/useToast';
import { usePutJoinOrganization } from '../hooks/@queries/organization';

const INVITE_CODE_LENGTH = 8;

const Organization = () => {
  const navigate = useNavigate();
  const [inviteCode, setInviteCode] = useState('');
  const { insertToastItem } = useToast();
  const { mutate: joinOrganization } = usePutJoinOrganization({
    onSuccess: () => {
      navigate(ROUTE_PATH.EXACT_MAIN);
      window.location.reload();
    },
    onError: error => {
      insertToastItem(error.response?.data.message);
    },
  });

  const handleClickJoinButton = (e: FormEvent) => {
    e.preventDefault();

    joinOrganization(inviteCode);
  };

  return (
    <S.Container>
      <S.Header>
        <BirdLogoWhite size={'60px'} />
        <S.HeaderText>Thankoo</S.HeaderText>
      </S.Header>
      <S.Body>
        <S.OrganizationForm onSubmit={handleClickJoinButton}>
          <Input
            maxLength={INVITE_CODE_LENGTH}
            setValue={setInviteCode}
            value={inviteCode}
            onChange={e => {
              setInviteCode(e.target.value);
            }}
            type='text'
            placeholder='초대 코드를 입력해주세요'
            aria-label='초대 코드 입력창'
          />
          <S.ButtonWrapper>
            <Button
              type='submit'
              size='small'
              isDisabled={inviteCode.length !== INVITE_CODE_LENGTH}
            >
              참여하기
            </Button>
          </S.ButtonWrapper>
        </S.OrganizationForm>
      </S.Body>
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
  Body: styled.main`
    height: 50%;

    display: flex;
    align-items: center;
    justify-content: center;
  `,
  OrganizationForm: styled.form`
    width: 100%;

    display: flex;
    align-items: center;
    justify-content: center;
    gap: 1rem;
  `,
  ButtonWrapper: styled.div`
    width: 100px;
  `,
};
