import styled from '@emotion/styled';
import SearchIcon from '@mui/icons-material/Search';

const Input = ({ setValue, ...rest }) => {
  return (
    <S.Container>
      <S.Input
        {...rest}
        onChange={e => {
          setValue(e.target.value);
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
          X
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
  `,
  Input: styled.input`
    width: 100%;
    flex: 1;
    width: 50px;
    font-size: 18px;
    padding: 10px 5px;
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
  SearchIcon: styled(SearchIcon)`
    padding: 0 5px;
    color: ${({ theme }) => theme.page.color};
  `,
};