import styled from '@emotion/styled';
import SearchIcon from '@mui/icons-material/Search';

const UserSearchInput = () => {
  return (
    <S.Container>
      <S.SearchIcon />
      <S.Input placeholder='검색/직접 입력'></S.Input>
      <S.EraseAllButton>X</S.EraseAllButton>
    </S.Container>
  );
};

export default UserSearchInput;

const S = {
  Container: styled.div`
    display: flex;
    align-items: center;
    background-color: #838383;
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
    color: white;

    &:focus {
      outline: none;
    }
    &::placeholder {
      color: white;
    }
  `,
  EraseAllButton: styled.button`
    border-radius: 50%;
    border: none;
    background-color: transparent;
    color: white;
    padding: 0 10px;
  `,
  SearchIcon: styled(SearchIcon)`
    padding: 0 5px;
  `,
};
