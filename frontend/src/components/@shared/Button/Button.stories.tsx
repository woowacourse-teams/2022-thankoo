import { Story, Meta } from '@storybook/react';

import Button, { ButtonProps } from './Button';

export default {
  title: 'Button',
  component: Button,
  decorators: [Story => <Story>버튼</Story>],
} as Meta<ButtonProps>;

const Template: Story<ButtonProps> = args => <Button {...args} />;

export const Primary = Template.bind({});

Primary.args = {
  color: 'primary',
  size: 'medium',
  children: 'Button',
  isDisabled: false,
  isLoading: false,
};
