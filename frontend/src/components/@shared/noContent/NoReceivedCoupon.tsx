import styled from '@emotion/styled';
import { Link } from 'react-router-dom';
import { ROUTE_PATH } from '../../../constants/routes';
import { FlexCenter } from '../../../styles/mixIn';
import Button from '../Button';
import IconEmptyList from '../LogoEmptyList';

const NoReceivedCoupon = () => {
  return (
    <S.Container>
      <S.Box>
        <S.IconWrapper>
          <IconEmptyList />
        </S.IconWrapper>
        <S.Comment>
          받은 쿠폰이 없어요🤣
          <br />
          기브 앤 테이크를 노려볼까요...!?
        </S.Comment>
        <Link to={ROUTE_PATH.SELECT_RECEIVER}>
          <Button size='small'>쿠폰 보내기</Button>
        </Link>
      </S.Box>
    </S.Container>
  );
};

const S = {
  Container: styled.div`
    ${FlexCenter}
    flex: 1;
  `,
  Box: styled.div`
    ${FlexCenter}
    flex-direction: column;
    width: 280px;
    height: fit-content;
    border-radius: 10px;
    color: ${({ theme }) => theme.header.color};
    text-align: center;
    gap: 8px;
    padding: 0 10px 60px 10px;
  `,
  IconWrapper: styled.div`
    height: 70px;
  `,
  Comment: styled.h3`
    font-size: 1.5rem;
    line-height: 30px;
  `,
};
export default NoReceivedCoupon;
