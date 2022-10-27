import Dropdown from '../../../components/@shared/Dropdown';
import ArrowDropDownIcon from '@mui/icons-material/ArrowDropDown';
import { useOrganizationsDropdown } from '../hooks/useOrganizationsDropdown';
import { Link } from 'react-router-dom';
import { ROUTE_PATH } from '../../../constants/routes';
import styled from '@emotion/styled';

const OrganizationsDropdown = () => {
  const { lastAccessedOrganizationName, organizations, updateLastAccessedOrganization } =
    useOrganizationsDropdown();

  const handleClickOrganization = id => {
    updateLastAccessedOrganization(id);
  };

  return (
    <Dropdown>
      <Dropdown.Toggle>
        {lastAccessedOrganizationName}
        <ArrowDropDownIcon />
      </Dropdown.Toggle>
      <Dropdown.Menu>
        {organizations?.map(organization => (
          <Dropdown.Item
            key={organization.organizationId}
            selected={organization.organizationName === lastAccessedOrganizationName}
            onClick={() => {
              handleClickOrganization(organization.organizationId);
            }}
          >
            {organization.organizationName}
          </Dropdown.Item>
        ))}
        <SplitLine />
        <Link to={ROUTE_PATH.JOIN_ORGANIZATION}>
          <Dropdown.Item selected={false}>그룹 추가</Dropdown.Item>
        </Link>
      </Dropdown.Menu>
    </Dropdown>
  );
};
export default OrganizationsDropdown;

const SplitLine = styled.hr`
  width: 100%;
  border: 0.1px solid lightgray;
`;
