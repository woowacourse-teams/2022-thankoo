import styled from '@emotion/styled';
import Button from '../../../components/@shared/Button/Button';
import Avatar from '../../../components/Avatar';
import { ListRow } from '../../../components/ListRow';
import { UserProfile } from '../../../types/user';

type ListViewHeartSkeletonProps = {
  user: UserProfile;
};

const ListViewHeartSkeleton = ({ user }: ListViewHeartSkeletonProps) => {
  return (
    <ListRow
      css={{ width: '100%', alignItems: 'center' }}
      left={<Avatar alt={user.name} />}
      center={
        <ListRow.Text2Rows
          top={user.name}
          topProps={{ color: '#f7f7f7', fontSize: '1.5rem' }}
          bottom=''
          bottomProps={{ color: '#a4a4a4' }}
        />
      }
      right={
        <div css={{ display: 'flex', gap: '1rem' }}>
          <S.CountWrapper>
            <S.CountLabel>연속</S.CountLabel> <S.CountNum>0회</S.CountNum>
          </S.CountWrapper>
          <S.SendButtonWrapper>
            <Button isLoading={true}>콕</Button>
          </S.SendButtonWrapper>
        </div>
      }
    />
  );
};
export default ListViewHeartSkeleton;

const S = {
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
    color: mediumspringgreen;
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
