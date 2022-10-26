import styled from '@emotion/styled';
import { useState } from 'react';
import { BASE_URL } from '../../../constants/api';
import { useGetHeart, usePostHeartMutation } from '../../../hooks/@queries/hearts';
import { Hearts } from '../hooks/useHeartsMembers';
import useToast from '../../../hooks/useToast';
import { FlexCenter } from '../../../styles/mixIn';
import Button from '../../../components/@shared/Button/Button';

const ListViewHeart = ({ user, canSend, modifiedLastReceived, sentCount }: Hearts) => {
  const [count, setCount] = useState(sentCount);
  const [heartCanSend, setHeartCanSend] = useState(canSend);
  const { refetch } = useGetHeart(user.id, {
    onSuccess: res => {
      const { sent, received } = res;
      const sentCount = sent?.count || 0;
      const receivedCount = received?.count || 0;

      setCount(Math.max(sentCount, receivedCount));
    },
  });
  const { insertToastItem } = useToast();
  const { mutate: postHeart, isLoading } = usePostHeartMutation({
    onSuccess: () => {
      refetch();
      setHeartCanSend(prev => !prev);
    },
    onError: error => {
      insertToastItem(error.response?.data.message);
    },
  });

  return (
    <S.UserWrappr key={user.id}>
      <S.UserImageWrapper>
        <S.UserImage src={`${BASE_URL}${user.imageUrl}`} />
      </S.UserImageWrapper>
      <S.UserName>{user.name}</S.UserName>
      {modifiedLastReceived && <S.ModifiedAt>{`${modifiedLastReceived}에 콕!`}</S.ModifiedAt>}

      <S.CountWrapper>
        <S.CountLabel>연속</S.CountLabel> <S.CountNum>{`${count}회`}</S.CountNum>
      </S.CountWrapper>
      <S.SendButtonWrapper>
        <Button
          isDisabled={!heartCanSend}
          isLoading={isLoading}
          onClick={() => {
            if (canSend) {
              postHeart(user.id);
            }
          }}
        >
          콕
        </Button>
      </S.SendButtonWrapper>
    </S.UserWrappr>
  );
};
export default ListViewHeart;

const S = {
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
    align-items: center;
    padding-left: 3rem;
    box-sizing: border-box;
    margin-right: 5px;
  `,
};
