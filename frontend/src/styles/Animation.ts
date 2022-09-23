import { keyframes } from '@emotion/react';

export const onMountToast = keyframes`
  0% {
        opacity: 0;
        transform: translateX(30px);
      }
      30% {
        transform: translateX(-1px);
      }
      32% {
        transform: translateX(0);
      }
      100% {
        opacity: 1;
      }
`;
export const unMountToast = keyframes`
  0% {
        opacity: 1;
      }
      2% {
        transform: translateX(-1px);
      }
      5% {
        transform: translateX(0);
      }
      100% {
        opacity: 0;
        transform: translateX(30px);
      }
`;
export const onMountFromBottomModal = keyframes`
  0% {
        transform: translate(-50%,50%);
      }
      100% {
        transform: translate(-50%,0);
      }
`;
export const unMountToBottomModal = keyframes`
  0% {
        transform: translate(-50%,0);
      }
      100% {
        transform: translate(-50%,50%);
      }
`;

export const onMountToCenterModal = keyframes`
  0% {
        transform: translate(-50%,0);
      }
      100% {
        transform: translate(-50%,-50%);
      }
`;

export const unMountCenterToButtomModal = keyframes`
  0% {
        transform: translate(-50%,-50%);
      }
      100% {
        transform: translate(-50%,0);
      }
`;
