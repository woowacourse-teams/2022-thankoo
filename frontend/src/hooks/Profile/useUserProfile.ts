import { useState } from 'react';
import { useQueryClient } from 'react-query';
import { UserProfile } from '../../types';
import {
  PROFILE_QUERY_KEY,
  useGetCouponExchangeCount,
  useGetUserProfile,
  usePutEditUserName,
} from '../@queries/profile';
import useToast from '../useToast';

const useUserProfile = () => {
  const { insertToastItem } = useToast();
  const queryClient = useQueryClient();
  const [isNameEdit, setIsNameEdit] = useState(false);
  const [name, setName] = useState<string>('');

  const { data: profile } = useGetUserProfile({
    onSuccess: (data: UserProfile) => {
      setName(data.name);
    },
  });

  const { data: exchangeCount } = useGetCouponExchangeCount();

  const { mutate: editUserName } = usePutEditUserName({
    onSuccess: () => {
      queryClient.invalidateQueries(PROFILE_QUERY_KEY.profile);
      insertToastItem(`수정이 완료됐습니다`);
    },
  });

  const submitModifyName = () => {
    if (!name.length || name === profile?.name) {
      return;
    }

    editUserName(name);
  };

  const handleClickModifyNameButton = () => {
    if (name.trim().length === 0) {
      if (!profile) {
        return;
      }

      setName(profile?.name);
      setIsNameEdit(prev => !prev);
      return;
    }

    if (isNameEdit) {
      submitModifyName();
    }

    setIsNameEdit(prev => !prev);
  };

  return {
    profile,
    isNameEdit,
    name,
    handleClickModifyNameButton,
    exchangeCount,
    setName,
  };
};

export default useUserProfile;
