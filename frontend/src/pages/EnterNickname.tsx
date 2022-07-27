import { useQuery, useQueryClient } from 'react-query';
import { useNavigate } from 'react-router-dom';
import { requestInstance } from '../apis/axios';
import Header from '../components/@shared/Header';
import HeaderText from '../components/@shared/HeaderText';
import PageLayout from '../components/@shared/PageLayout';
import { ROUTE_PATH } from '../constants/routes';
import { saveAuth } from '../utils/auth';

const EnterNickname = () => {
  //useQueryClinet로 email, nickname, token
  const queryClient = useQueryClient();
  const navigate = useNavigate();
  const { email, memberId } = queryClient.getQueryData('token') as any;

  const onclick = () => {
    //받아온걸 바탕으로 api 요청을 보냄
    const data = useQuery(
      '닉네임입력',
      () => {
        requestInstance({
          method: 'post',
        });
      },
      {
        onSuccess: data => {
          saveAuth(data.accesstoken);
          navigate(ROUTE_PATH.EXACT_MAIN); //redirect to exactMain
        },
      }
    );
  };

  return (
    <PageLayout>
      <Header>
        <HeaderText>닉네임을 입력해주세요</HeaderText>
        <button onClick={onclick}>닉네임 제출</button>
      </Header>
    </PageLayout>
  );
};

export default EnterNickname;
