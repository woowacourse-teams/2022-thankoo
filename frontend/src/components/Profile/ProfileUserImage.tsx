import styled from '@emotion/styled';
import CreateIcon from '@mui/icons-material/Create';

const ProfileUserImage = ({ src }) => {
  return (
    <S.ImageBox>
      <S.UserImage src={src} />
      {/* <S.ModifyButton>
        <S.FileInputLabel htmlFor='user_img'>
          <S.ModifyIcon />
          수정
        </S.FileInputLabel>
        <S.FileInput id='user_img' type='file' />
      </S.ModifyButton> */}
    </S.ImageBox>
  );
};

export default ProfileUserImage;

const S = {
  ImageBox: styled.div`
    position: relative;
    width: 12.5rem;
    height: 12.5rem;
    margin: 3rem auto;
  `,
  UserImage: styled.img`
    width: 100%;
    height: 100%;
    border-radius: 50%;
    object-fit: cover;
    cursor: pointer;
    border: 1px solid;
  `,
  ModifyButton: styled.button`
    position: absolute;
    bottom: -5%;
    right: -20%;
    border: ${({ theme }) => `0.5px solid ${theme.button.abled.color}`};
    color: black;
    border-radius: 5px;
    display: flex;
    align-items: center;
    gap: 3px;
    margin: 0;
    padding: 0;
  `,
  ModifyIcon: styled(CreateIcon)`
    fill: none;
    stroke: black;
  `,
  FileInputLabel: styled.label`
    display: flex;
    align-items: center;
    padding: 1px 4px;
  `,
  FileInput: styled.input`
    position: absolute;
    width: 1px;
    height: 1px;
    padding: 0;
    margin: -1px;
    overflow: hidden;
    clip: rect(0, 0, 0, 0);
    border: 0;
  `,
};
