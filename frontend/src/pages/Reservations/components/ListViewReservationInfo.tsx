import styled from '@emotion/styled';
import CheckIcon from '@mui/icons-material/Check';
import { ListRow } from '../../../components/ListRow';
import { COUPON_IMAGE, RAND_COLORS } from '../../../constants/coupon';
import { CouponTransmitableType } from '../../../types/coupon';
import { serverDateFormmater } from '../../../utils/date';

type ListViewReservationInfoProps = {
  memberName: string;
  reservationId: number;
  couponType: CouponTransmitableType;
  meetingTime: string;
};

const ListViewReservationInfo = ({
  memberName,
  reservationId,
  couponType,
  meetingTime,
}: ListViewReservationInfoProps) => {
  const { day, date, time } = serverDateFormmater(meetingTime);

  return (
    <S.Container
      backgroundColor={RAND_COLORS[reservationId % RAND_COLORS.length].bg}
      color={RAND_COLORS[reservationId % RAND_COLORS.length].color}
    >
      <ListRow
        left={<S.CouponImage src={COUPON_IMAGE[couponType]} alt={couponType} />}
        center={<ListRow.Text2Rows top={memberName} bottom={`${date} (${day}) ${time}`} />}
      />
    </S.Container>
  );
};

export default ListViewReservationInfo;

type ContentProp = {
  backgroundColor: string;
  color: string;
};
type CheckBoxProp = { isChecked: boolean };

const S = {
  Container: styled.div<ContentProp>`
    gap: 5px 0;
    padding: 1.5rem;
    background-color: ${({ backgroundColor }) => backgroundColor};
    color: ${({ color }) => color};
    align-items: center;
    font-size: 1.5rem;
    cursor: pointer;
  `,
  CouponImage: styled.img`
    width: 40px;
    height: 40px;
    /* border-radius: 50%; */
  `,
  UserName: styled.span``,
  RequestedDate: styled.span``,
  Checkbox: styled(CheckIcon)<CheckBoxProp>`
    justify-self: end;
    margin-right: 5px;
    border-radius: 50%;
    background-color: ${({ theme }) => theme.primary};
    fill: white;
    padding: 1px;
    display: ${({ isChecked }) => (isChecked ? 'inline-block' : 'none')};
  `,
};
