import { useState } from 'react';
import { useMutation, useQueryClient } from 'react-query';
import { client } from '../../apis/axios';
import { API_PATH } from '../../constants/api';
import { UserProfile } from '../../types';
import { exchangeCount, useGetCouponExchangeCount, useGetUserProfile } from '../@queries/profile';
import useToast from '../useToast';

const useUserProfile = () => {
  const { insertToastItem } = useToast();
  const queryClient = useQueryClient();
  const [isNameEdit, setIsNameEdit] = useState(false);
  const [name, setName] = useState<string>('');
  const [exchangeCount, setExchangeCount] = useState({ sentCount: 0, receivedCount: 0 });

  const { data: profile } = useGetUserProfile({
    onSuccess: (data: UserProfile) => {
      setName(data.name);
    },
  });

  const { data } = useGetCouponExchangeCount({
    onSuccess: (data: exchangeCount) => {
      setExchangeCount(data);
    },
  });

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
      insertToastItem(`수정이 완료됐습니다`);
    }

    setIsNameEdit(prev => !prev);
  };

  const editUserName = useMutation(
    (name: string) =>
      client({
        method: 'put',
        url: `${API_PATH.PROFILE_NAME}`,
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

  const editUserProfile = useMutation(
    (userProfile: string) =>
      client({
        method: 'put',
        url: `${API_PATH.EDIT_PROFILE}`,
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
  return {
    profile,
    editUserName,
    editUserProfile,
    isNameEdit,
    name,
    handleClickModifyNameButton,
    exchangeCount,
    setName,
  };
};

export default useUserProfile;
