import { Coupon } from '../../types';
import Slider from '../@shared/ChoiceSlider';
import ListViewReservation from './ListViewReservation';

const Reservation = ({ coupon }: { coupon: Coupon }) => {
  //TODO Reservation 승낙, 거절 api 파서 coupon써서 보내면 될듯
  //PUT /api/reservations/{reservationId}
  return (
    <Slider>
      <Slider.Inner>
        <Slider.Content>
          <ListViewReservation coupon={coupon} />
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
