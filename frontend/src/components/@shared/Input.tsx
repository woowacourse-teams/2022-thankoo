import styled from '@emotion/styled';
import CloseIcon from '@mui/icons-material/Close';

type InputProps = {
  setValue: React.Dispatch<React.SetStateAction<any>>;
  maxLength: number;
  [x: string]: any;
};

const Input = ({ setValue, maxLength, ...rest }: InputProps) => {
  const isValidInput = value => {
    return value.length <= maxLength;
  };

  return (
    <S.Container>
      <S.Input
        {...rest}
        autoComplete={'off'}
        onChange={e => {
          if (isValidInput(e.target.value)) {
            setValue(e.target.value);
          }
        }}
        onKeyDown={e => {
          if (e.nativeEvent.key === 'Escape') {
            setValue('');
          }
        }}
      />
      {rest.value.length !== 0 && !rest.disabled && (
        <S.EraseAllButton
          onClick={e => {
            e.preventDefault();
            setValue('');
          }}
        >
          <CloseIcon />
        </S.EraseAllButton>
      )}
    </S.Container>
  );
};

export default Input;

const S = {
  Container: styled.div`
    display: flex;
    align-items: center;
    background-color: ${({ theme }) => theme.input.background};
    border-radius: 4px;
    width: 100%;
  `,
  Input: styled.input`
    width: 100%;
    flex: 1;
    width: 50px;
    font-size: 1.5rem;
    padding: 1rem;
    border: none;
    background-color: transparent;
    -webkit-appearance: none;
    outline: none;
    color: ${({ theme }) => theme.input.color};

    :disabled {
      color: #b4b4b4;
    }
    &:focus {
      outline: none;
    }
    &::placeholder {
      color: ${({ theme }) => theme.input.placeholder};
    }
  `,
  EraseAllButton: styled.span`
    border-radius: 50%;
    border: none;
    background-color: transparent;
    color: ${({ theme }) => theme.page.color};
    padding: 0 10px;
  `,
};
