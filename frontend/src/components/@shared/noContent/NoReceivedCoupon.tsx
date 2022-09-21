import styled from '@emotion/styled';
import { Link } from 'react-router-dom';
import { ROUTE_PATH } from '../../../constants/routes';
import { FlexCenter } from '../../../styles/mixIn';
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
        <Link to={`${ROUTE_PATH.HEARTS}`}>
          <S.Button>마음 보내기💛</S.Button>
        </Link>
      </S.Box>
    </S.Container>
  );
};

const S = {
  Container: styled.div`
    position: absolute;
    left: 50%;
    top: 50%;
    transform: translate(-50%, -50%);
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
    padding: 30px 10px;
  `,
  IconWrapper: styled.div`
    height: 70px;
  `,
  Comment: styled.h3`
    font-size: 1.5rem;
    line-height: 30px;
  `,
  Button: styled.button`
    background-color: ${({ theme }) => theme.primary};
    color: ${({ theme }) => theme.button.abled};
    padding: 8px 12px;
    border: none;
    border-radius: 6px;
    margin-top: 20px;
  `,
};
export default NoReceivedCoupon;
