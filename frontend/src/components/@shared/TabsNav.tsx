import styled from '@emotion/styled';

interface TabsNavProps {
  children?: JSX.Element | string;
  onChangeTab: (string) => void;
  currentTab: string;
  tabList: {};
  selectableTabs: any[];
  className?: string;
}

const TabsNav = ({
  children,
  onChangeTab,
  currentTab,
  tabList,
  selectableTabs,
  className,
}: TabsNavProps) => {
  return (
    <S.Container className={className}>
      <S.ButtonWrapper>
        {selectableTabs.map((tab, idx) => (
          <S.Button
            key={idx}
            onClick={() => {
              onChangeTab(tab);
            }}
            isActive={currentTab === tab}
          >
            {tabList[tab]}
          </S.Button>
        ))}
      </S.ButtonWrapper>
      {children}
    </S.Container>
  );
};

type ButtonProp = { isActive: boolean };

const S = {
  Container: styled.div`
    display: flex;
    justify-content: space-between;
  `,
  ButtonWrapper: styled.div`
    display: flex;
    gap: 10px;
  `,
  Button: styled.button<ButtonProp>`
    border: none;
    background-color: transparent;
    padding: 0;

    color: ${({ isActive, theme }) => (isActive ? theme.page.color : theme.page.subColor)};
    font-size: 18px;
    font-weight: 600;
  `,
};

export default TabsNav;
