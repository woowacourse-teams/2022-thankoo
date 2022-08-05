import { useState } from 'react';
import { useMutation, useQuery, useQueryClient } from 'react-query';
import { client } from '../../apis/axios';
import { API_PATH } from '../../constants/api';
import { UserProfile } from '../../types';
import useToast from './../useToast';

const useProfile = () => {
  const { show: showToast } = useToast();
  const queryClient = useQueryClient();
  const [isNameEdit, setIsNameEdit] = useState(false);
  const [name, setName] = useState<string>('');
  const onChangeName = e => {
    setName(e.target.value);
  };

  const { data: profile } = useQuery<UserProfile>(
    'profile',
    async () => {
      const { data } = await client({
        method: 'get',
        url: `${API_PATH.PROFILE}`,
      });

      return data;
    },
    {
      refetchOnWindowFocus: false,
      onSuccess: profile => {
        setName(profile.name);
      },
    }
  );

  const submitModifyName = () => {
    if (!name.length || name === profile?.name) {
      return;
    }

    editUserName.mutate(name);
  };

  const handleClickModifyNameButton = () => {
    if (name.length === 0) {
      if (!profile) {
        return;
      }

      setName(profile?.name);
      return;
    }

    if (isNameEdit) {
      submitModifyName();
      showToast('수정이 완료됐습니다!');
    }

    setIsNameEdit(prev => !prev);
  };

  const editUserName = useMutation(
    (name: string) =>
      client({
        method: 'put',
        url: `${API_PATH.PROFILE}`,
        data: {
          name,
        },
      }),
    {
      onSuccess: () => {
        queryClient.invalidateQueries('profile');
      },
    }
  );

  return { profile, editUserName, isNameEdit, name, handleClickModifyNameButton, onChangeName };
};

export default useProfile;
