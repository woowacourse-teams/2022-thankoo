import { useState } from 'react';
import { Coupon } from '../../types';
import Modal from '../@shared/Modal';
import CouponDetails from './CouponDetails';
import GridViewCoupon from './GridViewCoupon';

const MainCoupon = ({ coupon }: { coupon: Coupon }) => {
  const [show, setShow] = useState(false);

  return (
    <>
      <div
        onClick={() => {
          setShow(true);
        }}
      >
        <GridViewCoupon key={coupon.couponHistoryId} coupon={coupon} />
      </div>
      {show && (
        <Modal>
          <CouponDetails
            closeModal={() => {
              setShow(false);
            }}
            coupon={coupon}
          />
        </Modal>
      )}
    </>
  );
};

export default MainCoupon;
