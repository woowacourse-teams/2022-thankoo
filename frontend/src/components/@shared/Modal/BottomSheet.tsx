import styled from '@emotion/styled';
import { forwardRef, LegacyRef, ReactNode } from 'react';
import { modalMountTime, modalUnMountTime } from '../../../constants/modal';
import { onMountFromBottomModal, unMountToBottomModal } from '../../../styles/Animation';

type BottomSheetModalSize = 'large' | 'medium' | 'small';

type BottomSheetProps = {
  children: ReactNode;
  size?: BottomSheetModalSize;
};

export default forwardRef(
  ({ children, size }: BottomSheetProps, ref: LegacyRef<HTMLDivElement>) => {
    return (
      <S.Layout ref={ref} size={size}>
        <S.Inner>{children}</S.Inner>
      </S.Layout>
    );
  }
);

type BottomSheetLayoutStyleProps = {
  size?: BottomSheetModalSize;
};

const S = {
  Layout: styled.div<BottomSheetLayoutStyleProps>`
    position: fixed;
    bottom: 0;
    left: 50%;
    transform: translateX(-50%);
    max-width: 55rem;
    width: 100%;
    background-color: #272727;
    border-radius: 7% 7% 0 0;
    display: flex;
    z-index: 10000;
    color: white;

    &.onMount {
      animation: ${onMountFromBottomModal} ${`${modalMountTime}ms`} ease-in-out;
    }
    &.unMount {
      animation: ${unMountToBottomModal} ${`${modalUnMountTime}ms`} ease-in-out;
    }
  `,
  Inner: styled.div`
    display: flex;
    padding: 3rem 2rem;
    flex-direction: column;
    width: 100%;
    justify-content: space-between;
    box-sizing: border-box;
    gap: 2rem;
  `,
};
