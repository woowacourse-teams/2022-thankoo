import { useQueryClient } from 'react-query';
import {
  RESERVATION_QUERY_KEYS,
  usePutCancelReseravation,
  usePutReservationStatus,
} from '../../hooks/@queries/reservation';
import { 예약요청응답별코멘트 } from '../../hooks/Main/useCouponDetail';
import useToast from '../../hooks/useToast';
import Slider from '../@shared/ChoiceSlider';
import ListViewReservation from './ListViewReservation';

const Reservation = ({ couponType, time, memberName, reservationId, order }) => {
  const queryClient = useQueryClient();
  const { insertToastItem } = useToast();

  const { mutate: handleReservation } = usePutReservationStatus(reservationId, {
    onSuccess: status => {
      insertToastItem(예약요청응답별코멘트[status]);
      queryClient.invalidateQueries(RESERVATION_QUERY_KEYS.reservations);
    },
    onError: error => {
      insertToastItem(error.response?.data.message);
    },
  });

  const option1 = order === 'received' ? '거절' : '취소';
  const option2 = order === 'received' ? '승인' : '수정';
  const { mutate: cancelReservation } = usePutCancelReseravation(reservationId, {
    onSuccess: () => {
      insertToastItem('예약을 취소했습니다');
      queryClient.invalidateQueries([RESERVATION_QUERY_KEYS.reservations]);
    },
    onError: error => {
      insertToastItem(error.response?.data.message);
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
