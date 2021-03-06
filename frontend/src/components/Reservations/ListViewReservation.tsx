import styled from '@emotion/styled';
import CheckIcon from '@mui/icons-material/Check';
import { COUPON_IMAGE, RAND_COLORS } from '../../constants/coupon';

const ListViewReservation = ({ memberName, reservationId, couponType, meetingTime }) => {
  const date = meetingTime.split('T')[0].split('-');
  const time = meetingTime.split('T')[1].split('.')[0].slice(0, 5);

  return (
    <S.Container
      backgroundColor={RAND_COLORS[reservationId % RAND_COLORS.length].bg}
      color={RAND_COLORS[reservationId % RAND_COLORS.length].color}
    >
      <S.CouponImage src={COUPON_IMAGE[couponType]} />
      <S.UserName>{memberName}</S.UserName>
      <S.RequestedDate>{`${date[1]}월 ${date[2]}일`}</S.RequestedDate>
    </S.Container>
  );
};

export default ListViewReservation;

type ContentProp = {
  backgroundColor: string;
  color: string;
};
type CheckBoxProp = { isChecked: boolean };

const S = {
  Container: styled.div<ContentProp>`
    display: grid;
    grid-template-areas:
      'ci un cb'
      'ci ed cb';
    grid-template-columns: 22% 58% 20%;
    gap: 5px 0;
    padding: 1rem;
    border-radius: 5px;
    background-color: ${({ backgroundColor }) => backgroundColor};
    color: ${({ color }) => color};
    align-items: center;
    cursor: pointer;
  `,
  CouponImage: styled.img`
    grid-area: ci;
    width: 2rem;
    height: 2rem;
    border-radius: 50%;
    object-fit: cover;
  `,
  UserName: styled.span`
    grid-area: un;
  `,
  RequestedDate: styled.span`
    grid-area: ed;
  `,
  Checkbox: styled(CheckIcon)<CheckBoxProp>`
    grid-area: cb;
    justify-self: end;
    margin-right: 5px;
    border-radius: 50%;
    background-color: ${({ theme }) => theme.primary};
    fill: white;
    padding: 1px;
    display: ${({ isChecked }) => (isChecked ? 'inline-block' : 'none')};
  `,
};
