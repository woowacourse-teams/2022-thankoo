export const urlQueryHandler = (url: string, query: string) => {
  if (url.includes(query)) {
    return url;
  }

  return url.includes('?') ? `${url}&${query}` : `${url}?${query}`;
};
