import { css } from '@emotion/react';
import styled from '@emotion/styled';
import { createContext, Dispatch, SetStateAction, useContext, useState } from 'react';
import Dimmer from './Dimmer';

type ToggleContextState = {
  toggle: boolean;
  setToggle: Dispatch<SetStateAction<boolean>>;
};

const ToggleContext = createContext<ToggleContextState>({
  toggle: false,
  setToggle: () => {},
});

const Dropdown = ({ children, ...props }) => {
  const [toggle, setToggle] = useState<boolean>(false);

  return (
    <ToggleContext.Provider value={{ toggle, setToggle }}>
      <Container {...props}>{children}</Container>
    </ToggleContext.Provider>
  );
};

Dropdown.Toggle = ({ children, ...props }) => {
  const { setToggle } = useContext(ToggleContext);

  return (
    <Toggle
      onClick={() => {
        setToggle(prev => !prev);
      }}
      {...props}
    >
      {children}
    </Toggle>
  );
};

Dropdown.Menu = ({ children, ...props }) => {
  const { setToggle, toggle } = useContext(ToggleContext);

  return (
    <>
      <Menu show={toggle} {...props}>
        {children}
      </Menu>
      <Dimmer
        onClick={() => {
          setToggle(false);
        }}
        show={toggle}
      />
    </>
  );
};

Dropdown.Item = ({ children, selected, onClick = () => {}, ...props }) => {
  const { setToggle } = useContext(ToggleContext);

  return (
    <Item
      onClick={() => {
        onClick?.();
        setToggle(false);
      }}
      selected={selected}
      {...props}
    >
      {children}
    </Item>
  );
};

type MenuProps = {
  show: boolean;
};

type ItemProps = {
  selected: boolean;
};

const Container = styled.section`
  position: relative;
`;
const Menu = styled.ul<MenuProps>`
  position: absolute;
  top: 170%;
  left: 0;

  display: flex;
  flex-flow: column;

  width: max-content;
  background-color: white;
  padding: 1rem;
  border-radius: 7px;

  z-index: 1000;
  cursor: pointer;
  ${({ show }) =>
    show
      ? css`
          display: flex;
        `
      : css`
          display: none;
        `}
`;
const Toggle = styled.span`
  display: flex;
  align-items: center;
  color: white;
  font-size: 1.7rem;
  letter-spacing: 0.1rem;
  cursor: pointer;
`;
const Item = styled.li<ItemProps>`
  ${({ selected }) =>
    selected
      ? css`
          color: black;
          font-weight: bold;
        `
      : css`
          color: #656f7a;
        `}
  font-size: 1.3rem;
  padding: 1.4rem 1rem;
`;

export default Dropdown;
