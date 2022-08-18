import styled from '@emotion/styled';
import { Link } from 'react-router-dom';
import { flexCenter } from '../styles/mixIn';
import ArrowBackButton from './../components/@shared/ArrowBackButton';
import Header from './../components/@shared/Header';
import HeaderText from './../components/@shared/HeaderText';
import PageLayout from './../components/@shared/PageLayout';
import { useGetHearts } from './../hooks/@queries/hearts';
import { useGetMembers } from './../hooks/@queries/members';

const Hearts = () => {
  const { data: members2, isLoading: isMemberLoading, isError: isMemberError } = useGetMembers();
  const { data: heartHistory, isLoading: isHeartLoading, isError: isHeartError } = useGetHearts();

  const members = [
    { id: 1, imageUrl: '', name: '호호', lastReceived: '3분전' },
    { id: 2, imageUrl: '', name: '호호', lastReceived: '' },
    { id: 3, imageUrl: '', name: '호호', lastReceived: '' },
    { id: 4, imageUrl: '', name: '호호', lastReceived: '' },
    { id: 5, imageUrl: '', name: '호호', lastReceived: '' },
    { id: 6, imageUrl: '', name: '호호', lastReceived: '' },
  ];
  const canSend = true;

  return (
    <PageLayout>
      <Header>
        <Link to='/'>
          <ArrowBackButton />
        </Link>
        <HeaderText>당신의 마음을 툭... 던져볼까요?</HeaderText>
      </Header>
      <S.Body>
        <S.MembersContainer>
          {members.map(user => {
            return (
              <S.UserWrappr key={user.id}>
                <S.UserImage src={user.imageUrl} />
                <S.UserName>{user.name}</S.UserName>
                <S.ModifiedAt>{user.lastReceived}</S.ModifiedAt>
                <S.CountWrapper>
                  <S.CountLabel>연속</S.CountLabel> <S.CountNum>4회</S.CountNum>
                </S.CountWrapper>
                <S.SendButtonWrapper canSend={true}>
                  <S.SendButton>{canSend ? '툭' : null}</S.SendButton>
                </S.SendButtonWrapper>
              </S.UserWrappr>
            );
          })}
        </S.MembersContainer>
      </S.Body>
    </PageLayout>
  );
};

type CheckBoxProp = { canSend: boolean };

const S = {
  Body: styled.div`
    width: 100vw;
    height: fit-content;
    max-height: calc(80%);
    overflow: auto;
  `,
  MembersContainer: styled.div`
    width: 100%;

    display: flex;
    flex-direction: column;
    align-items: center;
    gap: 1rem;
  `,
  UserWrappr: styled.div`
    width: 80%;
    height: 5rem;
    display: grid;
    grid-template-areas:
      'ui un ct cb'
      'ui sn ct cb';
    grid-template-columns: 17% 50% 13% 20%;
    grid-template-rows: 60% 40%;
    border-radius: 5px;
    gap: 2px 0;
    border: 0.1rem solid #787878;
  `,
  UserImage: styled.img`
    grid-area: ui;
    width: 100%;
    height: 100%;
    transform: scale(0.65);

    border-radius: 50%;
    object-fit: cover;
  `,
  UserName: styled.span`
    grid-area: un;
    height: 100%;
    line-height: 3rem;
    font-size: 1.5rem;
    color: ${({ theme }) => theme.page.color};
  `,
  ModifiedAt: styled.div`
    grid-area: sn;
    height: 100%;
    font-size: 1.3rem;
    color: ${({ theme }) => theme.page.subColor};
  `,
  CountWrapper: styled.div`
    grid-area: ct;
    display: flex;
    flex-direction: column;
    color: ${({ theme }) => theme.page.color};
    padding: 0.6rem 0;
    text-align: center;
    gap: 3px;
  `,
  CountLabel: styled.div`
    color: #c9c9c9;
    font-size: 1.5rem;
  `,
  CountNum: styled.div`
    color: darksalmon; //10회 미만 darksalmon 10회 이상 medumspringgreen 30회 이상 fuschia 100회 이상 gold
    font-size: 1.7rem;
  `,
  SendButtonWrapper: styled.div<CheckBoxProp>`
    grid-area: cb;
    ${flexCenter}
    margin-right: 5px;
    align-items: center;
    width: 100%;
    height: 100%;
  `,
  SendButton: styled.span`
    width: 80%;
    height: 65%;
    border-radius: 12px;

    text-align: center;
    background-color: #ff7a62;
    font-size: 1.8rem;
    line-height: 3rem;

    color: ${({ theme }) => theme.button.active};
  `,
};

export default Hearts;
