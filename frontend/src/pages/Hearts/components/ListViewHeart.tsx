import styled from '@emotion/styled';
import { useState } from 'react';
import { useGetHeart, usePostHeartMutation } from '../../../hooks/@queries/hearts';
import { Hearts } from '../hooks/useHeartsMembers';
import useToast from '../../../hooks/useToast';
import { FlexCenter } from '../../../styles/mixIn';
import Button from '../../../components/@shared/Button/Button';
import Avatar from '../../../components/Avatar';
import { ListRow } from '../../../components/ListRow';

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
    <ListRow
      css={{ width: '100%', alignItems: 'center' }}
      left={<Avatar src={user.imageUrl} alt={user.name} />}
      center={
        <ListRow.Text2Rows
          top={user.name}
          topProps={{ color: '#f7f7f7', fontSize: '1.5rem' }}
          bottom={modifiedLastReceived && `${modifiedLastReceived}에 콕!`}
          bottomProps={{ color: '#a4a4a4' }}
        />
      }
      right={
        <div css={{ display: 'flex', gap: '1rem' }}>
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
        </div>
      }
    />
  );
};

export default ListViewHeart;

const S = {
  UserImageWrapper: styled.div`
    width: 100%;
    height: 100%;
    ${FlexCenter};
  `,
  CountWrapper: styled.div`
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
    word-break: keep-all;
  `,
  SendButtonWrapper: styled.div`
    display: flex;
    justify-content: flex-end;
    margin-right: 5px;
    min-width: 70px;
  `,
};
