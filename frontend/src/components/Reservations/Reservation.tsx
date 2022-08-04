import { useMutation, useQueryClient } from 'react-query';
import { client } from '../../apis/axios';
import { API_PATH } from '../../constants/api';
import { usePutCancelReseravation } from '../../hooks/Main/queries/couponDetail';
import Slider from '../@shared/ChoiceSlider';
import ListViewReservation from './ListViewReservation';

type status = 'deny' | 'accept';

const Reservation = ({ couponType, time, memberName, reservationId, order }) => {
  const queryClient = useQueryClient();

  const handleReservation = useMutation(
    async (status: status) => {
      await client({
        method: 'put',
        url: `${API_PATH.RESERVATIONS}/${reservationId}`,
        data: { status },
      });
    },
    {
      onSuccess: () => {
        queryClient.invalidateQueries('reservations');
      },
    }
  );

  const option1 = order === 'received' ? '거절' : '취소';
  const option2 = order === 'received' ? '승인' : '수정';
  const { mutate: cancelReservation } = usePutCancelReseravation(reservationId, {
    onSuccess: () => {
      queryClient.invalidateQueries(['reservations']);
    },
  });
  const handleClickOption = {
    received: [
      () => {
        if (confirm('예약을 취소하시겠습니까?')) {
          cancelReservation();
        }
      },
      () => {
        if (confirm(`예약을 수락하시겠습니까? \n ${time?.meetingTime}`)) {
          handleReservation.mutate('accept');
        }
      },
    ],
    sent: [
      () => {
        if (confirm('예약을 취소하시겠습니까?')) {
          cancelReservation();
        }
      },
    ],
  };

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
          {order === 'received' ? (
            <>
              <Slider.OptionItem isAccept={false} onClick={handleClickOption[order][0]}>
                {option1}
              </Slider.OptionItem>
              <Slider.OptionItem isAccept={true} onClick={handleClickOption[order][1]}>
                {option2}
              </Slider.OptionItem>
            </>
          ) : (
            <Slider.OptionItem isAccept={false} onClick={handleClickOption[order][0]}>
              {option1}
            </Slider.OptionItem>
          )}
        </Slider.Options>
      </Slider.Inner>
    </Slider>
  );
};

export default Reservation;
