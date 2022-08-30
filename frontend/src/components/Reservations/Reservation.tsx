import { useQueryClient } from 'react-query';
import { usePutCancelReseravation } from '../../hooks/Main/queries/couponDetail';
import { usePutReservationStatus } from '../../hooks/Reservations/queries/reservations';
import Slider from '../@shared/ChoiceSlider';
import ListViewReservation from './ListViewReservation';

const Reservation = ({ couponType, time, memberName, reservationId, order }) => {
  const queryClient = useQueryClient();

  const { mutate: handleReservation } = usePutReservationStatus(reservationId);

  const option1 = order === 'received' ? '거절' : '취소';
  const option2 = order === 'received' ? '승인' : '수정';
  const { mutate: cancelReservation } = usePutCancelReseravation(reservationId, {
    onSuccess: () => {
      queryClient.invalidateQueries(['reservations']);
    },
  });

  /** TODO 성공 실패 시 토스트 커스텀으로 올릴 수 있다. */
  const handleClickOption = {
    received: [
      () => {
        if (confirm('예약을 거절하시겠습니까?')) {
          handleReservation('deny');
        }
      },
      () => {
        if (confirm(`예약을 수락하시겠습니까? \n ${time?.meetingTime}`)) {
          handleReservation('accept');
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
  const optionsWidth = order === 'sent' ? '60%' : '100%';

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
            <Slider.OptionItem
              style={{ width: optionsWidth }}
              isAccept={false}
              onClick={handleClickOption[order][0]}
            >
              {option1}
            </Slider.OptionItem>
          )}
        </Slider.Options>
      </Slider.Inner>
    </Slider>
  );
};

export default Reservation;
