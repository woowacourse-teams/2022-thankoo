import styled from '@emotion/styled';
import { createContext, Fragment, useContext, useState } from 'react';

const ChoiceSlider = ({ children }) => {
  return <S.Container>{children}</S.Container>;
};

export default ChoiceSlider;

type OptionsProps = {
  show: boolean;
};
type OptionItemProps = {
  isAccept: boolean;
  index: number;
  show: boolean;
};
type ToggleContextState = {
  toggle: boolean;
  setToggle: React.Dispatch<React.SetStateAction<boolean>>;
};
const ToggleContext = createContext<ToggleContextState>({ toggle: false, setToggle: () => {} });

ChoiceSlider.Inner = ({ children, ...props }) => {
  const [toggle, setToggle] = useState(false);

  return (
    <ToggleContext.Provider {...props} value={{ toggle, setToggle }}>
      <S.Inner>{children}</S.Inner>
    </ToggleContext.Provider>
  );
};

ChoiceSlider.Content = ({ children, ...props }) => {
  const { toggle, setToggle } = useContext(ToggleContext);

  return (
    <S.Content
      show={toggle}
      onClick={() => {
        setToggle(prev => !prev);
      }}
      {...props}
    >
      {children}
    </S.Content>
  );
};
ChoiceSlider.Options = ({ children, ...props }) => {
  const { toggle } = useContext(ToggleContext);

  return (
    <S.Options show={toggle} {...props}>
      {children}
    </S.Options>
  );
};

ChoiceSlider.OptionItem = ({ children, onClick, index, isAccept, ...props }) => {
  const { setToggle, toggle } = useContext(ToggleContext);
  const onClickItem = () => {
    onClick();
    setToggle(prev => !prev);
  };

  return (
    <S.OptionItem show={toggle} index={index} isAccept={isAccept} onClick={onClickItem} {...props}>
      {children}
    </S.OptionItem>
  );
};

const S = {
  Container: styled.div``,
  Inner: styled.div`
    display: flex;
    position: relative;
  `,
  Content: styled.div<OptionsProps>`
    width: ${({ show }) => (show ? '80%' : '100%')};
    min-width: 55%;
    transition: all ease-in-out 0.1s;
    z-index: 3;
    overflow: hidden;
    border-radius: 11px;
  `,
  Options: styled.div<OptionsProps>`
    display: flex;
    position: absolute;
    right: 0;
    width: 100%;
    height: 100%;
    border-radius: 0 3px 3px 0;
    overflow: hidden;
  `,
  OptionItem: styled.button<OptionItemProps>`
    position: absolute;
    background-color: ${({ theme, isAccept }) => (isAccept ? `${theme.primary}` : '#8e8e8e')};
    color: white;
    font-weight: bold;
    word-break: keep-all;
    padding: 10px;
    border: none;
    border-radius: 11px;
    width: ${({ index, show }) => (show ? `${100 - (2 - index) * 10}%` : '100%')};
    transition: all ease-in-out 0.1s;
    height: 100%;
    z-index: ${({ index }) => 2 - index};
    display: flex;
    justify-content: flex-end;
    align-items: center;
  `,
};
