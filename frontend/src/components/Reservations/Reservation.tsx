import axios from 'axios';
import { useMutation, useQueryClient } from 'react-query';
import { API_PATH } from '../../constants/api';
import Slider from '../@shared/ChoiceSlider';
import ListViewReservation from './ListViewReservation';

type status = 'deny' | 'accept';

const Reservation = ({ couponType, meetingTime, memberName, reservationId }) => {
  const accessToken = localStorage.getItem('token');
  const queryClient = useQueryClient();

  const handleReservation = useMutation(async (status: status) => {
    await axios({
      method: 'put',
      url: `${API_PATH.RESERVATIONS}/${reservationId}`,
      headers: { Authorization: `Bearer ${accessToken}` },
      data: { status },
    });
  });

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
              handleReservation.mutate('deny');
              queryClient.invalidateQueries(['reservationsReceived', 'reservationsSent']);
            }}
          >
            거절
          </Slider.OptionItem>
          <Slider.OptionItem
            isAccept={true}
            onClick={() => {
              handleReservation.mutate('accept');
              queryClient.invalidateQueries(['reservationsReceived', 'reservationsSent']);
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
