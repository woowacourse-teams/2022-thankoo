import { useQuery } from 'react-query';
import { Link } from 'react-router-dom';
import { client } from '../apis/axios';
import ArrowBackButton from '../components/@shared/ArrowBackButton';
import Header from '../components/@shared/Header';
import HeaderText from '../components/@shared/HeaderText';
import PageLayout from '../components/@shared/PageLayout';
import { API_PATH } from '../constants/api';
import { ROUTE_PATH } from '../constants/routes';

const Meetings = () => {
  const {
    data: meetings,
    isLoading,
    isError,
  } = useQuery('meetings', async () => {
    const { data } = await client({ method: 'get', url: API_PATH.MEETINGS });

    return data;
  });

  console.log(meetings);

  return (
    <PageLayout>
      <Header>
        <Link to={ROUTE_PATH.EXACT_MAIN}>
          <ArrowBackButton />
        </Link>
        <HeaderText>스케쥴</HeaderText>
      </Header>
    </PageLayout>
  );
};

export default Meetings;
