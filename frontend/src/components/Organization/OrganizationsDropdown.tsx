import Dropdown from '../@shared/Dropdown';
import ArrowDropDownIcon from '@mui/icons-material/ArrowDropDown';
import { useOrganizationsDropdown } from '../../hooks/Organization/useOrganizationsDropdown';
import { Link } from 'react-router-dom';
import { ROUTE_PATH } from '../../constants/routes';

const OrganizationsDropdown = () => {
  const { lastAccessedOrganization, organizations, updateLastAccessedOrganization } =
    useOrganizationsDropdown();

  const handleClickOrganization = id => {
    updateLastAccessedOrganization(id);
  };

  return (
    <Dropdown>
      <Dropdown.Toggle>
        {lastAccessedOrganization}
        <ArrowDropDownIcon />
      </Dropdown.Toggle>
      <Dropdown.Menu>
        {organizations?.map(organization => (
          <Dropdown.Item
            key={organization.organizationId}
            selected={organization.organizationName === lastAccessedOrganization}
            onClick={() => {
              handleClickOrganization(organization.organizationId);
            }}
          >
            {organization.organizationName}
          </Dropdown.Item>
        ))}
        <Dropdown.Item selected={false}>
          <Link to={ROUTE_PATH.JOIN_ORGANIZATION}>그룹 추가</Link>
        </Dropdown.Item>
      </Dropdown.Menu>
    </Dropdown>
  );
};
export default OrganizationsDropdown;
