import styled from '@emotion/styled';
import CheckIcon from '@mui/icons-material/Check';
import { COUPON_IMAGE, RAND_COLORS } from '../../constants/coupon';
import { serverDateFormmater } from '../../utils/date';

const ListViewReservationDetail = ({ memberName, reservationId, couponType, meetingTime }) => {
  const { day, date, time } = serverDateFormmater(meetingTime);

  return (
    <S.Container
      backgroundColor={RAND_COLORS[reservationId % RAND_COLORS.length].bg}
      color={RAND_COLORS[reservationId % RAND_COLORS.length].color}
    >
      <S.CouponImage src={COUPON_IMAGE[couponType]} />
      <S.UserName>{memberName}</S.UserName>
      <S.RequestedDate>{`${date} (${day}) ${time}`}</S.RequestedDate>
    </S.Container>
  );
};

export default ListViewReservationDetail;

type ContentProp = {
  backgroundColor: string;
  color: string;
};
type CheckBoxProp = { isChecked: boolean };

const S = {
  Container: styled.div<ContentProp>`
    display: grid;
    grid-template-areas:
      'ci un'
      'ci ed';
    grid-template-columns: 22% 78%;
    gap: 5px 0;
    padding: 1.5rem;
    background-color: ${({ backgroundColor }) => backgroundColor};
    color: ${({ color }) => color};
    align-items: center;
    font-size: 1.5rem;
    cursor: pointer;
  `,
  CouponImage: styled.img`
    grid-area: ci;
    width: 4rem;
    height: 4rem;
    border-radius: 50%;
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
