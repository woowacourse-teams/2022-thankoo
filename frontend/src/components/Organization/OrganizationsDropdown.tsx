import Dropdown from '../@shared/Dropdown';
import ArrowDropDownIcon from '@mui/icons-material/ArrowDropDown';
import { useOrganizationsDropdown } from '../../hooks/Organization/useOrganizationsDropdown';

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
      </Dropdown.Menu>
    </Dropdown>
  );
};
export default OrganizationsDropdown;
