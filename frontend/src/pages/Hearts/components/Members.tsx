import styled from '@emotion/styled';
import { Suspense } from 'react';
import { FlexCenter } from '../../../styles/mixIn';
import useHeartsMembers from '../hooks/useHeartsMembers';
import ListViewHeart from './ListViewHeart';
import ListViewHeartSkeleton from './ListViewHeartSkeleton';

const Members = ({ searchKeyword }: { searchKeyword: string }) => {
  const { searchedUserWithState } = useHeartsMembers(searchKeyword);

  return (
    <S.MembersContainer>
      {searchedUserWithState?.map(hearts => (
        <Suspense key={hearts.user.id} fallback={<ListViewHeartSkeleton user={hearts.user} />}>
          <ListViewHeart {...hearts} />
        </Suspense>
      ))}
    </S.MembersContainer>
  );
};

export default Members;

type CheckBoxProp = { canSend: boolean };

const S = {
  MembersContainer: styled.div`
    width: 100%;

    display: flex;
    flex-direction: column;
    align-items: center;
    gap: 1rem;

    overflow: auto;
    height: calc(100% - 5rem);
    padding-bottom: 17rem;

    &::-webkit-scrollbar {
      width: 2px;
      background-color: transparent;
    }
    &::-webkit-scrollbar-thumb {
      background-color: transparent;
      border-radius: 5px;
    }

    &:hover {
      overflow-y: auto;

      &::-webkit-scrollbar {
        width: 2px;
        background-color: transparent;
      }
      &::-webkit-scrollbar-thumb {
        background-color: ${({ theme }) => theme.page.color};
        border-radius: 5px;
      }
    }
  `,
  UserWrappr: styled.div`
    width: 99.5%;
    min-height: 5rem;
    display: grid;
    grid-template-areas:
      'ui un ct cb'
      'ui sn ct cb';
    grid-template-columns: 17% 50% 13% 20%;
    grid-template-rows: 60% 40%;
    border-radius: 5px;
    gap: 2px 0;
  `,
  UserImageWrapper: styled.div`
    grid-area: ui;
    width: 100%;
    height: 100%;
    ${FlexCenter};
  `,

  UserImage: styled.img`
    width: 30px;

    border-radius: 50%;
    object-fit: cover;
  `,
  UserName: styled.span`
    grid-area: un;
    height: 100%;
    line-height: 3.5rem;
    font-size: 16px;
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
    justify-content: center;
  `,
  CountLabel: styled.div`
    color: #c9c9c9;
    font-size: 1rem;
  `,
  CountNum: styled.div`
    color: mediumspringgreen; //10회 미만 darksalmon 10회 이상 mediumspringgreen 30회 이상 fuchsia 100회 이상 gold
    font-size: 1.5rem;
  `,
  SendButtonWrapper: styled.div`
    grid-area: cb;
    ${FlexCenter}
    margin-right: 5px;
    align-items: center;
    width: 100%;
    height: 100%;
  `,
  SendButton: styled.span<CheckBoxProp>`
    display: grid;
    place-items: center;

    width: 80%;
    height: 80%;
    border-radius: 8px;

    text-align: center;
    background-color: ${({ canSend }) => (canSend ? `#ff7a62` : `#adadad`)};
    font-size: 1.3rem;
    line-height: 3rem;

    color: ${({ canSend }) => (canSend ? 'white' : '#7a7a7a')};
    cursor: ${({ canSend }) => (canSend ? 'pointer' : '')};
  `,
};
