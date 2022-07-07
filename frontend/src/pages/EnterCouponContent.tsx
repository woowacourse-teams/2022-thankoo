import ArrowBackButton from '../commons/Header/ArrowBackButton';
import styled from '@emotion/styled';
import ArrowForwardIosIcon from '@mui/icons-material/ArrowForwardIos';
import CouponTypesNav from '../commons/Main/CouponTypesNav';
import useEnterCouponContent from '../hooks/EnterCouponContent/useEnterCouponContent';
import { couponTypeKeys } from '../types';

const couponTypesWithoutEntire = couponTypeKeys.filter(type => type !== 'entire');

const EnterCouponContent = () => {
  const { couponType, setCouponType } = useEnterCouponContent();

  return (
    <S.Container>
      <S.Header>
        <ArrowBackButton />
        <S.HeaderText>어떤 쿠폰을 보낼까요?</S.HeaderText>
      </S.Header>
      <S.Body>
        <CouponTypesNav
          onChangeType={setCouponType}
          currentType={couponType}
          selectableCouponTypes={couponTypesWithoutEntire}
        />
        <S.DummyCouponBox />
        <S.Form>
          <S.TitleInput type='text' placeholder='제목을 입력해주세요' />
          <S.MessageTextarea
            maxLength={100}
            placeholder='메세지를 작성해보세요'
          ></S.MessageTextarea>
        </S.Form>
      </S.Body>
      <S.LongButton>
        '친구들(7명)' 에게 쿠폰 전송하기
        <ArrowForwardIosIcon />
      </S.LongButton>
    </S.Container>
  );
};

const S = {
  Container: styled.div`
    display: flex;
    flex-direction: column;
    padding: 5px;
  `,
  Header: styled.header`
    display: flex;
    flex-direction: column;
    align-items: flex-start;
    gap: 15px;
    color: white;
    margin: 10px 0 0 2vw;
  `,
  HeaderText: styled.p`
    font-size: 26px;
    margin-left: calc(1vw + 6px);
  `,
  Body: styled.div`
    display: flex;
    flex-direction: column;
    gap: 2rem;
    padding: 15px 3vw;
    color: white;
    height: 70vh;
    justify-content: center;
  `,
  Form: styled.form`
    display: flex;
    flex-direction: column;
    gap: 10px;
  `,
  TitleInput: styled.input`
    border: none;
    padding: 5px;
    font-size: 18px;
    background-color: #8c888866;
    color: white;
    border-radius: 5px;
    padding: 10px;

    &::placeholder {
      color: #8e8e8e;
    }
    &:focus {
      outline: 3px solid #ff6450;
    }
  `,
  MessageTextarea: styled.textarea`
    resize: none;
    height: 10vh;
    border: none;
    padding: 5px;
    font-size: 18px;
    background-color: #8c888866;
    border-radius: 5px;
    padding: 10px;
    color: white;

    &::placeholder {
      color: #8e8e8e;
    }
    &:focus {
      outline: 3px solid #ff6450;
    }
  `,
  LongButton: styled.button`
    border: none;
    color: white;
    border-radius: 30px;
    font-size: 18px;
    margin: 0 3vw;
    padding: 10px 20px;
    display: flex;
    justify-content: space-between;
    align-items: center;
    background: #ff6450;
  `,
  DummyCouponBox: styled.div`
    width: 50vw;
    height: 30vh;
    background-color: #8e8e8e;
    margin: 0 auto;
    border-radius: 10px;
  `,
};

export default EnterCouponContent;
