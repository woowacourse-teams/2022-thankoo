import styled from '@emotion/styled';
import { Link, Outlet } from 'react-router-dom';
import BottomNavBar from '../components/PageButton/BottomNavBar';
import UserProfileButton from '../components/@shared/UserProfileButton';
import PageLayout from './PageLayout';
import ArrowDropDownIcon from '@mui/icons-material/ArrowDropDown';
import Dropdown from '../components/@shared/Dropdown';

const ORGANIZATIONS = [
  {
    organizationId: 1,
    organizationName: '땡쿠',
    organizationCode: 'THANKOO1',
    orderNumber: 1,
    lastAccessed: true,
  },
  {
    organizationId: 2,
    organizationName: '우아한테크코스 4기',
    organizationCode: 'WOOWACO1',
    orderNumber: 2,
    lastAccessed: false,
  },
];

export default () => {
  // const { data: selectedOrganization } = useGetOrganizations();
  const selectedOrganization = ORGANIZATIONS[0];

  return (
    <PageLayout>
      <UserProfileWrapper>
        <Dropdown>
          <Dropdown.Toggle>
            {selectedOrganization.organizationName}
            <ArrowDropDownIcon />
          </Dropdown.Toggle>
          <Dropdown.Menu>
            {ORGANIZATIONS.map(organization => (
              <Link to={`/${organization.organizationCode}`} reloadDocument={true}>
                <Dropdown.Item
                  key={organization.organizationId}
                  selected={organization.organizationName === selectedOrganization.organizationName}
                >
                  {organization.organizationName}
                </Dropdown.Item>
              </Link>
            ))}
          </Dropdown.Menu>
        </Dropdown>
        <UserProfileButton />
      </UserProfileWrapper>
      <Outlet />
      <BottomNavBar />
    </PageLayout>
  );
};

const UserProfileWrapper = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin: 1rem;
`;
