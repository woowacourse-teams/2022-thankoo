import { Coupon } from '../../types';
import CouponLayout from '../@shared/CouponLayout';

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
