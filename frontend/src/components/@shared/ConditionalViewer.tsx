const ConditionalViewer = ({ children, condition, replacement }) => {
  return <>{condition ? children : replacement}</>;
};

export default ConditionalViewer;
