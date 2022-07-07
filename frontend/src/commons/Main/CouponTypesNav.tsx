import styled from '@emotion/styled';
import { couponTypes } from '../../types';

const CouponTypesNav = ({ onChangeType, currentType, selectableCouponTypes }) => {
  return (
    <S.Container>
      {selectableCouponTypes.map(type => (
        <S.Button
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

    color: ${({ isActive }) => (isActive ? 'white' : '#8e8e8e')};
    font-size: 18px;
    font-weight: 600;
  `,
};

export default CouponTypesNav;
