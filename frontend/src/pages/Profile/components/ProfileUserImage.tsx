import styled from '@emotion/styled';
import AddIcon from '@mui/icons-material/Add';
import { useQueryClient } from 'react-query';
import ModalWrapper from '../../../components/@shared/Modal/ModalWrapper';
import Avatar from '../../../components/Avatar';
import { PROFILE_QUERY_KEY, usePutEditUserProfileImage } from '../../../hooks/@queries/profile';

import SelectProfileImgModal from './SelectProfileImgModal';

const ProfileUserImage = ({ src }: { src: string }) => {
  const queryClient = useQueryClient();

  const { mutate: editUserProfileImage } = usePutEditUserProfileImage({
    onSuccess: () => {
      queryClient.invalidateQueries(PROFILE_QUERY_KEY.profile);
    },
  });

  return (
    <S.ImageBox>
      <Avatar src={src} size={100} alt={'프로필'} />
      <ModalWrapper modal={<SelectProfileImgModal editUserProfileImage={editUserProfileImage} />}>
        <S.ModifyButton>
          <AddIcon />
        </S.ModifyButton>
      </ModalWrapper>
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
