import styled from '@emotion/styled';
import { COUPON_IMAGE, RAND_COLORS } from '../../constants/coupon';
import { Coupon } from '../../types/index';
import CheckIcon from '@mui/icons-material/Check';

const ListViewReservation = ({
  coupon,
  onClickReservation,
}: {
  coupon: Coupon;
  onClickReservation: (string) => void;
}) => {
  const { sender, content } = coupon;

  return (
    <S.Container
      onClick={() => {
        onClickReservation(coupon);
      }}
      backgroundColor={RAND_COLORS[sender.id % RAND_COLORS.length].bg}
      color={RAND_COLORS[sender.id % RAND_COLORS.length].color}
    >
      <S.CouponImage src={COUPON_IMAGE[content.couponType]} />
      <S.UserName>{sender.name}</S.UserName>
      <S.RequestedDate>2022.07.21</S.RequestedDate>
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
    gap: 2px 0;
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
