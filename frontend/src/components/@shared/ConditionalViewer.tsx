import { PropsWithChildren } from 'react';

type ConditionalViewerProps = { showReplacement: boolean; replacement: React.ReactNode };

const ConditionalViewer = ({
  children,
  showReplacement: condition,
  replacement,
}: PropsWithChildren<ConditionalViewerProps>) => {
  return <>{condition ? replacement : children}</>;
};

export default ConditionalViewer;
