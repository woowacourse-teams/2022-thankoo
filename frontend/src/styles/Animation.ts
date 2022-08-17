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
        bottom: -100%;
      }
      40% {
        bottom: -50%;
      }
      100% {
        bottom: 0;
      }
`;
export const unMountToBottomModal = keyframes`
  0% {
        bottom: 0%;
      }
      100% {
        bottom: -100%;
      }
`;