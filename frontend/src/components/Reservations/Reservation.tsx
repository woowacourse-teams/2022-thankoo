import Slider from '../@shared/ChoiceSlider';
import ListViewReservation from './ListViewReservation';

const Reservation = ({ couponType, meetingTime, memberName, reservationId }) => {
  console.log(couponType, meetingTime, memberName, reservationId);

  return (
    <Slider>
      <Slider.Inner>
        <Slider.Content>
          <ListViewReservation
            couponType={couponType}
            meetingTime={meetingTime}
            memberName={memberName}
            reservationId={reservationId}
          />
        </Slider.Content>
        <Slider.Options>
          <Slider.OptionItem
            isAccept={false}
            onClick={() => {
              console.log('거절');
            }}
          >
            거절
          </Slider.OptionItem>
          <Slider.OptionItem
            isAccept={true}
            onClick={() => {
              console.log('승낙');
            }}
          >
            승낙
          </Slider.OptionItem>
        </Slider.Options>
      </Slider.Inner>
    </Slider>
  );
};

export default Reservation;
