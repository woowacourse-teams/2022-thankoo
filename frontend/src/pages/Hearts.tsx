import styled from '@emotion/styled';
import { Link } from 'react-router-dom';
import { flexCenter } from '../styles/mixIn';
import ArrowBackButton from './../components/@shared/ArrowBackButton';
import Header from './../components/@shared/Header';
import HeaderText from './../components/@shared/HeaderText';
import PageLayout from './../components/@shared/PageLayout';
import { BASE_URL } from './../constants/api';
import { useGetHearts, usePostHeartMutation } from './../hooks/@queries/hearts';
import { useGetMembers } from './../hooks/@queries/members';

const Hearts = () => {
  const { data: members, isLoading: isMemberLoading, isError: isMemberError } = useGetMembers(); //userList 전체 받아오기
  const { data: heartHistory, isLoading: isHeartLoading, isError: isHeartError } = useGetHearts(); //
  const { mutate: postHeart } = usePostHeartMutation();

  //sent를 돌면서 sent에 있는 receiver들은 못 보내는 애들
  // received에 있는 sender들은 보낼 수 있는 애들
  if (isHeartLoading) return <></>;

  const sent = heartHistory?.sent!;
  const received = heartHistory?.received!;

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
          {members?.map(user => {
            const canSend =
              !sent?.some(sentHistory => sentHistory.receiverId === user.id) ||
              received?.some(receivedHistory => receivedHistory.senderId === user.id);

            const count = sent?.find(sentHistory => sentHistory.receiverId === user.id)?.count;
            const lastReceived =
              sent?.find(sentHistory => sentHistory.receiverId === user.id)?.modifiedAt ||
              received?.find(receiveHistory => receiveHistory.senderId === user.id)?.modifiedAt;
            const modifiedLastReceived = lastReceived?.split(' ')[0];
            const receivedUserCount = received?.find(
              receiveHistory => receiveHistory.senderId === user.id
            )?.count;

            return (
              <S.UserWrappr key={user.id} canSend={canSend}>
                <S.UserImageWrapper>
                  <S.UserImage src={`${BASE_URL}${user.imageUrl}`} />
                </S.UserImageWrapper>
                <S.UserName>{user.name}</S.UserName>
                {modifiedLastReceived ? (
                  <S.ModifiedAt>{`${modifiedLastReceived}에 툭!`}</S.ModifiedAt>
                ) : (
                  <></>
                )}
                (
                <S.CountWrapper>
                  <S.CountLabel>연속</S.CountLabel>{' '}
                  <S.CountNum>{`${count || receivedUserCount || 0}회`}</S.CountNum>
                </S.CountWrapper>
                )
                <S.SendButtonWrapper>
                  <S.SendButton
                    canSend={canSend}
                    onClick={() => {
                      postHeart(user.id);
                    }}
                  >
                    {'툭'}
                  </S.SendButton>
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
    height: calc(80%);
    overflow: auto;
    ::-webkit-scrollbar {
      display: none;
    }
  `,
  MembersContainer: styled.div`
    width: 100%;

    display: flex;
    flex-direction: column;
    align-items: center;
    gap: 1rem;
  `,
  UserWrappr: styled.div<CheckBoxProp>`
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
    border: 0.1rem solid ${({ canSend }) => (canSend ? 'tomato' : '#787878')};
  `,
  UserImageWrapper: styled.div`
    grid-area: ui;
    width: 100%;
    height: 100%;
    ${flexCenter};
  `,

  UserImage: styled.img`
    transform: scale(0.5);

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
  SendButtonWrapper: styled.div`
    grid-area: cb;
    ${flexCenter}
    margin-right: 5px;
    align-items: center;
    width: 100%;
    height: 100%;
  `,
  SendButton: styled.span<CheckBoxProp>`
    width: 80%;
    height: 65%;
    border-radius: 8px;

    text-align: center;
    background-color: ${({ canSend }) => (canSend ? `#ff7a62` : `#adadad`)};
    font-size: 1.8rem;
    line-height: 3rem;

    color: ${({ canSend }) => (canSend ? 'white' : '#7a7a7a')};
    cursor: ${({ canSend }) => (canSend ? 'pointer' : '')};
  `,
};

export default Hearts;
