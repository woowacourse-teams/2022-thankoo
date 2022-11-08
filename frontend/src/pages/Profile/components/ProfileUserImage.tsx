import styled from '@emotion/styled';
import AddIcon from '@mui/icons-material/Add';
import ModalWrapper from '../../../components/@shared/Modal/ModalWrapper';
import Avatar from '../../../components/Avatar';
import SelectProfileImgModal from './SelectProfileImgModal';

const ProfileUserImage = ({ src }: { src: string }) => {
  return (
    <ModalWrapper
      modal={<SelectProfileImgModal />}
      style={{ width: 'fit-content', margin: '0 auto' }}
    >
      <S.ImageBox>
        <Avatar src={src} size={100} alt={'프로필 이미지 바꾸기'} />
        <S.ModifyButton />
      </S.ImageBox>
    </ModalWrapper>
  );
};

export default ProfileUserImage;

const S = {
  ImageBox: styled.div`
    display: grid;
    place-items: center;
    position: relative;
    width: 12.5rem;
    height: 12.5rem;
    margin: 3rem auto;
    cursor: pointer;
  `,
  ModifyButton: styled(AddIcon)`
    position: absolute;
    bottom: 0;
    right: 0;

    border: ${({ theme }) => `0.5px solid ${theme.button.abled.color}`};
    background-color: white;
    color: black;
    border-radius: 50%;
    display: flex;
    align-items: center;
    gap: 3px;
    margin: 0;
    padding: 8px;
  `,
};
