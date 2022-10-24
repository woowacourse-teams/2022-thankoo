import useListReservationItem from '../hook/useListReservationItem';
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
      <Slider.Inner>
        <Slider.Content>
          <ListViewReservationDetail
            couponType={couponType}
            meetingTime={time.meetingTime}
            memberName={memberName}
            reservationId={reservationId}
          />
        </Slider.Content>
        <Slider.Options>
          {transmitStatus === 'received' ? (
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

export default ListReservationItem;
