import styled from '@emotion/styled';
import { couponTypes } from '../../types';

const CouponTypesNav = ({ onChangeType, currentType, selectableCouponTypes }) => {
  return (
    <S.Container>
      {selectableCouponTypes.map((type, idx) => (
        <S.Button
          key={idx}
          onClick={() => {
            onChangeType(type);
          }}
          isActive={currentType === type}
        >
          {couponTypes[type]}
        </S.Button>
      ))}
    </S.Container>
  );
};

type ButtonProp = { isActive: boolean };

const S = {
  Container: styled.div``,
  Button: styled.button<ButtonProp>`
    border: none;
    background-color: transparent;

    color: ${({ isActive, theme }) => (isActive ? theme.page.color : theme.page.subColor)};
    font-size: 18px;
    font-weight: 600;
  `,
};

export default CouponTypesNav;
