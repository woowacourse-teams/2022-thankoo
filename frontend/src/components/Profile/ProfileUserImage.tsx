import styled from '@emotion/styled';
import AddIcon from '@mui/icons-material/Add';
import useUserProfile from './../../hooks/Profile/useUserProfile';
import useModal from './../../hooks/useModal';
import ProfileIcon from './../@shared/ProfileIcon';
import SelectProfileImgModal from './SelectProfileImgModal';

const ProfileUserImage = ({ src }: { src: string }) => {
  const { setModalContent, show } = useModal();
  const { editUserProfileImage } = useUserProfile();

  const selectProfileImg = () => {
    show();
    setModalContent(<SelectProfileImgModal patchImageMutation={editUserProfileImage} />);
  };

  return (
    <S.ImageBox>
      <ProfileIcon src={src} size={'100px'} />
      <S.ModifyButton onClick={selectProfileImg}>
        <AddIcon />
      </S.ModifyButton>
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
    bottom: -3%;
    right: -8%;
    border: ${({ theme }) => `0.5px solid ${theme.button.abled.color}`};
    color: black;
    border-radius: 50%;
    display: flex;
    align-items: center;
    gap: 3px;
    margin: 0;
    padding: 8px;
  `,
};
