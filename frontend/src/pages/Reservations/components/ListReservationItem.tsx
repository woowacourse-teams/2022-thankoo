import useListReservationItem from '../hooks/useListReservationItem';
import { CouponTransmitStatus, CouponType } from '../../../types/coupon';
import { MeetingTime } from '../../../types/meeting';
import Slider from '../../../components/@shared/ChoiceSlider';
import ListViewReservationDetail from './ListViewReservation';

type ListReservationItemProps = {
  couponType: CouponType;
  time: MeetingTime;
  memberName: string;
  reservationId: number;
  transmitStatus: CouponTransmitStatus;
};

const ListReservationItem = ({
  couponType,
  time,
  memberName,
  reservationId,
  transmitStatus,
}: ListReservationItemProps) => {
  const { handleClickOption } = useListReservationItem({ reservationId, time });

  return (
    <Slider>
      <Slider.Toggle>
        <ListViewReservationDetail
          couponType={couponType}
          meetingTime={time.meetingTime}
          memberName={memberName}
          reservationId={reservationId}
        />
      </Slider.Toggle>
      {transmitStatus === 'received' ? (
        <Slider.Options>
          <Slider.OptionItem backgroundColor='#8e8e8e'>
            <span onClick={handleClickOption[transmitStatus][0]}>거절</span>
          </Slider.OptionItem>

          <Slider.OptionItem backgroundColor='tomato'>
            <span onClick={handleClickOption[transmitStatus][1]}>승인</span>
          </Slider.OptionItem>
        </Slider.Options>
      ) : (
        <Slider.Options>
          <Slider.OptionItem backgroundColor='#8e8e8e'>
            <span onClick={handleClickOption[transmitStatus][0]}>취소</span>
          </Slider.OptionItem>
        </Slider.Options>
      )}
    </Slider>
  );
};

export default ListReservationItem;
