import styled from '@emotion/styled';
import React, {
  createContext,
  Dispatch,
  PropsWithChildren,
  ReactChild,
  ReactElement,
  SetStateAction,
  useContext,
  useEffect,
  useState,
} from 'react';

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

const ChoiceSlider = ({ children, ...props }) => {
  const [toggle, setToggle] = useState<boolean>(false);
  const [length, setLength] = useState<number>(0);

  return (
    <ToggleContext.Provider {...props} value={{ toggle, setToggle, length, setLength }}>
      <S.Container>{children}</S.Container>
    </ToggleContext.Provider>
  );
};

export default ChoiceSlider;

type ToggleProps = {
  children: ReactElement | string | number;
};
ChoiceSlider.Toggle = ({ children, ...props }: ToggleProps) => {
  const { toggle, setToggle, length } = useContext(ToggleContext);

  return (
    <S.Toggle
      totalLength={length}
      show={toggle}
      onClick={() => {
        setToggle(prev => !prev);
      }}
      {...props}
    >
      {children}
    </S.Toggle>
  );
};

const isMultipleChildren = (input): input is ReactElement[] => {
  return Array.isArray(input);
};
type PropsWithReactElementChild = {
  children: ReactElement | ReactElement[];
};
ChoiceSlider.Options = ({ children, ...props }: PropsWithReactElementChild) => {
  if (React.Children.count(children) > 3) {
    throw new Error('ChoiceSlider의 OptionsItem은 3개가 최대입니다.');
  }

  const childList = isMultipleChildren(children) ? children : [children];
  const indexedChildren = childList.map((child: ReactElement, idx) => {
    return {
      ...child,
      props: { ...child.props, index: idx + 1 },
    };
  });

  const { toggle, setLength } = useContext(ToggleContext);

  useEffect(() => {
    setLength(React.Children.count(children));
  }, []);

  return (
    <S.Options show={toggle} {...props}>
      {indexedChildren}
    </S.Options>
  );
};

type OptionItemProps = {
  index?: number;
} & PropsWithChildren;

ChoiceSlider.OptionItem = ({ children, index, ...props }: OptionItemProps) => {
  const { setToggle, toggle, length } = useContext(ToggleContext);

  return (
    <S.OptionItem
      show={toggle}
      index={index!}
      totalIndex={length}
      onClick={() => {
        setToggle(prev => !prev);
      }}
      {...props}
    >
      {children}
    </S.OptionItem>
  );
};

type ToggleStyleProps = {
  show: boolean;
  totalLength: number;
};
type OptionsStyleProps = {
  show: boolean;
};
type OptionItemStyleProps = {
  totalIndex: number;
  index: number;
  show: boolean;
};

const S = {
  Container: styled.div`
    display: flex;
    position: relative;
  `,
  Toggle: styled.div<ToggleStyleProps>`
    width: ${({ show, totalLength }) => (show ? `${100 - totalLength * 11}%` : '100%')};
    min-width: 55%;
    transition: all ease-in-out 0.1s;
    z-index: ${({ totalLength }) => totalLength + 1};
    overflow: hidden;
    border-radius: 11px;
    box-shadow: rgba(0, 0, 0, 0.25) 0px 14px 28px, rgba(0, 0, 0, 0.22) 0px 10px 10px;
  `,
  Options: styled.div<OptionsStyleProps>`
    display: flex;
    position: absolute;
    right: 0;
    width: 100%;
    height: 100%;
    border-radius: 0 3px 3px 0;
    overflow: hidden;
  `,
  OptionItem: styled.button<OptionItemStyleProps>`
    position: absolute;
    padding: 0;
    border: none;
    width: ${({ index, show, totalIndex }) =>
      show ? `${100 - (totalIndex - index) * 11}%` : '100%'};
    transition: all ease-in-out 0.1s;
    height: 100%;
    z-index: ${({ totalIndex, index }) => totalIndex - index};
    display: flex;
    justify-content: flex-end;
    align-items: center;
    border-radius: 11px;
    overflow: hidden;
    box-shadow: rgba(0, 0, 0, 0.25) 0px 14px 28px, rgba(0, 0, 0, 0.22) 0px 10px 10px;
  `,
};
