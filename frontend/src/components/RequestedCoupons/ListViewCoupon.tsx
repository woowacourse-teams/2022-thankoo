import styled from '@emotion/styled';
import CheckIcon from '@mui/icons-material/Check';
import { COUPON_IMAGE, RAND_COLORS } from '../../constants/coupon';
import { Coupon } from '../../types/index';

const ListViewCoupon = ({
  coupon,
  isClickedCoupon,
  onClickCoupon,
}: {
  coupon: Coupon;
  isClickedCoupon: boolean;
  onClickCoupon: () => void;
}) => {
  const { sender, content } = coupon;

  return (
    <S.Container
      onClick={onClickCoupon}
      backgroundColor={RAND_COLORS[sender.id % RAND_COLORS.length].bg}
      color={RAND_COLORS[sender.id % RAND_COLORS.length].color}
    >
      <S.CouponImage src={COUPON_IMAGE[content.couponType]} />
      <S.UserName>{sender.name}</S.UserName>
      <S.RequestedDate>2022.07.21</S.RequestedDate>
      <S.Checkbox isChecked={isClickedCoupon} />
    </S.Container>
  );
};

export default ListViewCoupon;

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
    grid-template-columns: 17% 63% 20%;
    gap: 2px 0;
    padding: 1rem;
    border-radius: 5px;
    background-color: ${({ backgroundColor }) => backgroundColor};
    color: ${({ color }) => color};
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
