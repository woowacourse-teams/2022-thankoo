import { useMutation, useQueryClient } from 'react-query';
import { requestInstance } from '../../api';
import { API_PATH } from '../../constants/api';
import Slider from '../@shared/ChoiceSlider';
import ListViewReservation from './ListViewReservation';

type status = 'deny' | 'accept';

const Reservation = ({ couponType, meetingTime, memberName, reservationId, order }) => {
  const queryClient = useQueryClient();

  const handleReservation = useMutation(
    async (status: status) => {
      await requestInstance({
        method: 'put',
        url: `${API_PATH.RESERVATIONS}/${reservationId}`,
        data: { status },
      });
    },
    {
      onSuccess: () => {
        queryClient.invalidateQueries('reservationsReceived');
        queryClient.invalidateQueries('reservationsSent');
      },
    }
  );

  const option1 = order === 'received' ? '거절' : '취소';
  const option2 = order === 'received' ? '승인' : '수정';

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
            }}
          >
            {option1}
          </Slider.OptionItem>
          <Slider.OptionItem
            isAccept={true}
            onClick={() => {
              handleReservation.mutate('accept');
            }}
          >
            {option2}
          </Slider.OptionItem>
        </Slider.Options>
      </Slider.Inner>
    </Slider>
  );
};

export default Reservation;
