import { ComponentProps, ComponentPropsWithoutRef, ReactElement, ReactNode } from 'react';
import { css } from '@emotion/react';

type ListProps = {
  left?: ReactNode;
  center?: Text1RowsElement | Text2RowsElement;
  right?: ReactNode;
} & ComponentPropsWithoutRef<'div'>;

type Text1RowsElement = ReactElement<ComponentProps<typeof ListRow.Text2Rows>>;
type Text2RowsElement = ReactElement<ComponentProps<typeof ListRow.Text2Rows>>;

export const ListRow = ({ left, center, right, ...props }: ListProps) => {
  return (
    <div
      css={{
        display: 'grid',
        gridTemplateColumns: '1fr 3fr 1fr',
      }}
      {...props}
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
