import styled from '@emotion/styled';
import Button from '../../../components/@shared/Button/Button';
import { FlexCenter } from '../../../styles/mixIn';
import { UserProfile } from '../../../types/user';

type ListViewHeartSkeletonProps = {
  user: UserProfile;
};

const ListViewHeartSkeleton = ({ user }: ListViewHeartSkeletonProps) => {
  return (
    <S.UserWrappr key={user.id}>
      <S.UserImageWrapper>
        <S.UserImage />
      </S.UserImageWrapper>
      <S.UserName>{user.name}</S.UserName>
      <S.ModifiedAt>ㅤㅤㅤㅤ</S.ModifiedAt>

      <S.CountWrapper>
        <S.CountLabel>연속</S.CountLabel> <S.CountNum>ㅤ</S.CountNum>
      </S.CountWrapper>
      <S.SendButtonWrapper>
        <Button isLoading={true}>콕</Button>
      </S.SendButtonWrapper>
    </S.UserWrappr>
  );
};
export default ListViewHeartSkeleton;

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

  UserImage: styled.div`
    width: 30px;
    height: 30px;

    border-radius: 50%;
    background-color: #404040;
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
    background-color: ${{}};
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
    border-radius: 5px;
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
