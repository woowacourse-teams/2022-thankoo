import useListReservation from '../hooks/useListReservation';
import { CouponTransmitableType, CouponTransmitStatus } from '../../../types/coupon';
import { MeetingTime } from '../../../types/meeting';
import Slider from '../../../components/@shared/ChoiceSlider';
import ListViewReservationInfo from './ListViewReservationInfo';
import styled from '@emotion/styled';

type ListReservationItemProps = {
  couponType: CouponTransmitableType;
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
  const { acceptRequest, cancelRequest, denyRequest } = useListReservation(reservationId);

  return (
    <Slider>
      <Slider.Toggle>
        <ListViewReservationInfo
          couponType={couponType}
          meetingTime={time.meetingTime}
          memberName={memberName}
          reservationId={reservationId}
        />
      </Slider.Toggle>
      {transmitStatus === 'received' ? (
        <Slider.Options>
          <Slider.OptionItem>
            <Reject onClick={denyRequest}>거절</Reject>
          </Slider.OptionItem>
          <Slider.OptionItem>
            <Accept onClick={acceptRequest}>승낙</Accept>
          </Slider.OptionItem>
        </Slider.Options>
      ) : (
        <Slider.Options>
          <Slider.OptionItem>
            <Reject onClick={cancelRequest}>취소</Reject>
          </Slider.OptionItem>
        </Slider.Options>
      )}
    </Slider>
  );
};

export default ListReservationItem;

const OptionItem = styled.span`
  color: white;
  display: grid;
  align-items: center;
  width: 100%;
  height: 100%;
  text-align: right;
  padding-right: 3%;
`;
const Reject = styled(OptionItem)`
  background-color: #8e8e8e;
`;
const Accept = styled(OptionItem)`
  background-color: tomato;
`;
