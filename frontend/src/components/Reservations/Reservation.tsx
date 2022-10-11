import useReservation from '../../hooks/Reservations/useReservation';
import { CouponTransmitStatus, CouponType } from '../../types/coupon';
import { MeetingTime } from '../../types/meeting';
import Slider from '../@shared/ChoiceSlider';
import ListViewReservation from './ListViewReservation';

type ReservationProps = {
  couponType: CouponType;
  time: MeetingTime;
  memberName: string;
  reservationId: number;
  transmitStatus: CouponTransmitStatus;
};

const Reservation = ({
  couponType,
  time,
  memberName,
  reservationId,
  transmitStatus,
}: ReservationProps) => {
  const { handleClickOption } = useReservation({ reservationId, time });

  return (
    <Slider>
      <Slider.Inner>
        <Slider.Content>
          <ListViewReservation
            couponType={couponType}
            meetingTime={time.meetingTime}
            memberName={memberName}
            reservationId={reservationId}
          />
        </Slider.Content>
        <Slider.Options>
          {transmitStatus === 'received' ? (
            /** TODO index는 Slider.OptionItem에서 자동으로 부여해주도록 수정 */
            <>
              <Slider.OptionItem
                index={1}
                isAccept={false}
                onClick={handleClickOption[transmitStatus][0]}
              >
                거절
              </Slider.OptionItem>
              <Slider.OptionItem
                index={2}
                isAccept={true}
                onClick={handleClickOption[transmitStatus][1]}
              >
                승인
              </Slider.OptionItem>
            </>
          ) : (
            <Slider.OptionItem
              index={1}
              isAccept={false}
              onClick={handleClickOption[transmitStatus][0]}
            >
              취소
            </Slider.OptionItem>
          )}
        </Slider.Options>
      </Slider.Inner>
    </Slider>
  );
};

export default Reservation;
