import styled from '@emotion/styled';
import React, { ErrorInfo } from 'react';
import { FlexCenter } from '../styles/mixIn';

type Props = {
  children?: React.ReactNode;
  fallbackComponent: React.ElementType;
  onError?: () => {};
};

type State = {
  hasError: boolean;
};

const initialState: State = {
  hasError: false,
};

class CustomErrorBoundary extends React.Component<Props, State> {
  constructor(props) {
    super(props);
    this.state = initialState;
  }

  static getDerivedStateFromError(error: Error) {
    console.log('error occured');
    return { hasError: true };
  }

  componentDidCatch(error: Error, errorInfo: ErrorInfo) {
    if (this.props.onError) {
      this.props.onError();
    }
  }

  reset() {
    this.setState(initialState);
  }

  render() {
    if (this.state.hasError) {
      return (
        <S.Container>
          <this.props.fallbackComponent />
        </S.Container>
      );
    }
    return this.props.children;
  }
}

export default CustomErrorBoundary;

const S = {
  Container: styled.div`
    position: absolute;
    left: 50%;
    top: 50%;
    transform: translate(-50%, -50%);

    ${FlexCenter};
    flex-direction: column;
    gap: 2.5rem;

    z-index: 1000;
    button {
      width: 100%;
    }
  `,
};
