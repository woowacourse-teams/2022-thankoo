import { Link } from 'react-router-dom';
import ArrowBackButton from '../components/@shared/ArrowBackButton';
import Header from '../components/@shared/Header';
import HeaderText from '../components/@shared/HeaderText';
import PageLayout from '../components/@shared/PageLayout';
import { ROUTE_PATH } from '../constants/routes';

const Meetings = () => {
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
