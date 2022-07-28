import styled from '@emotion/styled';
import { createContext, useContext, useState } from 'react';

const ChoiceSlider = ({ children }) => {
  return <S.Container>{children}</S.Container>;
};

export default ChoiceSlider;

type OptionsProps = {
  show: boolean;
};
type OptionItemProps = {
  isAccept: boolean;
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
  const { setToggle } = useContext(ToggleContext);

  return (
    <S.Content
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

ChoiceSlider.OptionItem = ({ children, onClick, isAccept, ...props }) => {
  const { setToggle } = useContext(ToggleContext);
  const onClickItem = () => {
    onClick();
    setToggle(prev => !prev);
  };

  return (
    <S.OptionItem isAccept={isAccept} onClick={onClickItem} {...props}>
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
  Content: styled.div`
    width: 100%;
  `,
  Options: styled.div<OptionsProps>`
    display: flex;
    position: absolute;
    height: 100%;
    right: 0;
    width: ${({ show }) => (show ? '100px' : 0)};
    transition: all ease-in-out 0.1s;
    border-radius: 0 3px 3px 0;
    overflow: hidden;
  `,
  OptionItem: styled.button<OptionItemProps>`
    background-color: ${({ theme, isAccept }) => (isAccept ? `${theme.primary}` : '#8e8e8e')};
    color: white;
    font-weight: bold;
    word-break: keep-all;
    padding: 10px;
    border: none;
    width: 100%;
  `,
};
