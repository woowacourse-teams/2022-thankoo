import styled from '@emotion/styled';
import { Link } from 'react-router-dom';
import CouponIcon from '../../assets/images/coupons.svg';
import { ROUTE_PATH } from '../../constants/routes';

const CouponsPageButton = () => {
  return (
    <Link to={ROUTE_PATH.EXACT_MAIN}>
      <Icon src={CouponIcon} alt='coupon_page_butotn' />;
    </Link>
  );
};

export default CouponsPageButton;

const Icon = styled.img`
  transform: scale(0.6);
  fill: white;
  color: white;
`;
