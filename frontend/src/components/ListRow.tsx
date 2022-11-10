import { ComponentProps, ReactElement, ReactNode } from 'react';

type ListProps = {
  left?: ReactNode;
  center?: Text1RowsElement | Text2RowsElement;
  right?: ReactNode;
  className?: string;
  onClick?: () => void;
};

type Text1RowsElement = ReactElement<ComponentProps<typeof ListRow.Text2Rows>>;
type Text2RowsElement = ReactElement<ComponentProps<typeof ListRow.Text2Rows>>;

export const ListRow = ({ left, center, right, onClick, className, ...props }: ListProps) => {
  return (
    <div
      className={className}
      css={{
        display: 'grid',
        gridTemplateColumns: '1fr 3fr 1fr',
      }}
      onClick={onClick}
    >
      {left}
      {center}
      {right}
    </div>
  );
};

interface Text2RowsProps {
  top: string;
  topProps?: any;
  bottom: string;
  bottomProps?: any;
}
ListRow.Text2Rows = ({ top, topProps, bottom, bottomProps }: Text2RowsProps) => {
  return (
    <div
      css={{
        display: 'flex',
        flexDirection: 'column',
        height: '100%',
        justifyContent: 'center',
        gap: '3px',
      }}
    >
      <span css={{ ...topProps }}>{top}</span>
      <span css={{ ...bottomProps }}>{bottom}</span>
    </div>
  );
};

interface Text1RowProps {
  top: string;
}
ListRow.Text1Row = ({ top }: Text1RowProps) => {
  return <span>{top}</span>;
};
