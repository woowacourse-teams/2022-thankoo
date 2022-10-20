import styled from '@emotion/styled';
import { createContext, Dispatch, SetStateAction, useContext, useEffect, useState } from 'react';

const ChoiceSlider = ({ children }) => {
  return <S.Container>{children}</S.Container>;
};

export default ChoiceSlider;

type ContentProps = {
  show: boolean;
  totalLength: number;
};
type OptionsProps = {
  show: boolean;
};
type OptionItemProps = {
  isAccept: boolean;
  totalIndex: number;
  index: number;
  show: boolean;
};
type ToggleContextState = {
  toggle: boolean;
  setToggle: Dispatch<SetStateAction<boolean>>;
  length: number;
  setLength: Dispatch<SetStateAction<number>>;
};
const ToggleContext = createContext<ToggleContextState>({
  toggle: false,
  setToggle: () => {},
  length: 0,
  setLength: () => {},
});

ChoiceSlider.Inner = ({ children, ...props }) => {
  const [toggle, setToggle] = useState<boolean>(false);
  const [length, setLength] = useState<number>(0);

  return (
    <ToggleContext.Provider {...props} value={{ toggle, setToggle, length, setLength }}>
      <S.Inner>{children}</S.Inner>
    </ToggleContext.Provider>
  );
};

ChoiceSlider.Content = ({ children, ...props }) => {
  const { toggle, setToggle, length } = useContext(ToggleContext);

  return (
    <S.Content
      totalLength={length}
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
  const { toggle, setLength } = useContext(ToggleContext);
  const hasChildren = Array.isArray(children.props.children);
  const length = hasChildren ? children.props.children.length : 1;

  useEffect(() => {
    setLength(length);
  }, [length]);

  return (
    <S.Options show={toggle} {...props}>
      {children}
    </S.Options>
  );
};

ChoiceSlider.OptionItem = ({ children, onClick, index, isAccept, ...props }) => {
  const { setToggle, toggle, length } = useContext(ToggleContext);
  const onClickItem = () => {
    onClick();
    setToggle(prev => !prev);
  };

  return (
    <S.OptionItem
      show={toggle}
      index={index}
      totalIndex={length}
      isAccept={isAccept}
      onClick={onClickItem}
      {...props}
    >
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
  Content: styled.div<ContentProps>`
    width: ${({ show, totalLength }) => (show ? `${100 - totalLength * 11}%` : '100%')};
    min-width: 55%;
    transition: all ease-in-out 0.1s;
    z-index: 3;
    overflow: hidden;
    border-radius: 11px;
    box-shadow: rgba(0, 0, 0, 0.25) 0px 14px 28px, rgba(0, 0, 0, 0.22) 0px 10px 10px;
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
    width: ${({ index, show, totalIndex }) =>
      show ? `${100 - (totalIndex - index) * 11}%` : '100%'};
    transition: all ease-in-out 0.1s;
    height: 100%;
    z-index: ${({ index }) => 2 - index};
    display: flex;
    justify-content: flex-end;
    align-items: center;
    box-shadow: rgba(0, 0, 0, 0.25) 0px 14px 28px, rgba(0, 0, 0, 0.22) 0px 10px 10px;
  `,
};
