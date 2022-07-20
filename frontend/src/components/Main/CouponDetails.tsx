import styled from '@emotion/styled';
import { Coupon } from '../../types';
import CloseButton from '../@shared/CloseButton';
import GridViewCoupon from './GridViewCoupon';

const CouponDetails = ({ coupon }: { coupon: Coupon }) => {
  const { sender, content } = coupon;

  return (
    <S.Container>
      <S.Modal>
        <S.Header>
          <span>D-49</span>
          <CloseButton color='white' />
        </S.Header>
        <S.CouponArea>
          <GridViewCoupon coupon={coupon} />
        </S.CouponArea>
        <S.Contents>
          <S.SpaceBetween>
            <S.Sender>{sender.name}</S.Sender>
            <span>2022.02.22</span>
          </S.SpaceBetween>
          <S.Message>{content.message}</S.Message>
        </S.Contents>
        <S.Footer>
          <S.Button>사용하기</S.Button>
        </S.Footer>
      </S.Modal>
    </S.Container>
  );
};

export default CouponDetails;

const S = {
  Container: styled.section`
    width: 100%;
    height: 100%;
    display: flex;
    align-items: center;
    justify-content: center;
    color: white;
  `,
  Modal: styled.div`
    width: 70%;
    height: 65%;
    border-radius: 5px;
    background-color: #232323;
    padding: 1rem;
  `,
  Header: styled.div`
    display: flex;
    align-items: center;
    justify-content: space-between;
    height: 10%;
  `,
  CouponArea: styled.div`
    display: flex;
    align-items: center;
    justify-content: center;
    height: 30%;
  `,
  Contents: styled.div`
    display: flex;
    flex-direction: column;
    height: 45%;
    justify-content: center;
  `,
  SpaceBetween: styled.div`
    display: flex;
    justify-content: space-between;
    align-items: center;
    flex: 1;
  `,
  Sender: styled.span`
    font-size: 20px;
  `,
  Message: styled.div`
    font-size: 15px;
    overflow-y: auto;
    flex: 1;
  `,
  Footer: styled.div`
    display: flex;
    justify-content: center;
    height: 15%;
    align-items: flex-end;
  `,
  Button: styled.button`
    border: none;
    border-radius: 4px;
    background-color: ${({ theme }) => theme.primary};
    color: ${({ theme }) => theme.button.abled.color};
    width: 100%;
    padding: 0.5rem;
    font-size: 15px;
    height: fit-content;
  `,
};
