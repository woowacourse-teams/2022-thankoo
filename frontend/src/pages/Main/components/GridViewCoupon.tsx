import CouponLayout from '../../../components/@shared/CouponLayout';
import { Coupon } from '../../../types/coupon';

const GridViewCoupon = ({ coupon }: { coupon: Coupon }) => {
  const { sender, content } = coupon;

  return (
    <CouponLayout
      title={content?.title}
      couponType={content?.couponType}
      id={sender?.id}
      name={sender?.name}
    />
  );
};

export default GridViewCoupon;
