import React, { ErrorInfo } from 'react';

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
        <>
          <this.props.fallbackComponent />
          <button onClick={this.reset.bind(this)}>재시도</button>
        </>
      );
    }
    return this.props.children;
  }
}

export default CustomErrorBoundary;
