import styled from '@emotion/styled';
import { useEffect, useRef, useState } from 'react';

const PageSlider = ({ children, ...rest }) => {
  const [currentPage, setCurrentPage] = useState(0);
  const pageRefs = useRef<Array<HTMLDivElement | null>>([]);
  const childrenLengthArray = Array.from({ length: children.length }, (_, i) => i);

  useEffect(() => {
    pageRefs.current[currentPage]?.scrollIntoView({
      behavior: 'smooth',
    });
  }, [currentPage]);

  const setPageRef = (page: number) => (el: HTMLDivElement | null) => {
    pageRefs.current[page] = el;

    return pageRefs.current[page];
  };

  const syncPageWithScroll = e => {
    const parentWidthX = e.currentTarget.getBoundingClientRect().x;
    for (const idx of childrenLengthArray) {
      const pageX = e.currentTarget.children[idx]?.getBoundingClientRect().x;
      if (pageX === parentWidthX) {
        setCurrentPage(idx);
        break;
      }
    }
  };

  const refedChildren = children.map((child, idx) => {
    return { ...child, ref: setPageRef(idx) };
  });

  return (
    <S.Container {...rest}>
      <S.PagesWrapper onScroll={syncPageWithScroll}>{refedChildren}</S.PagesWrapper>
      <S.DotsWrapper>
        {childrenLengthArray.map(idx => (
          <S.Dot
            key={`dot-${idx}`}
            onClick={() => {
              setCurrentPage(idx);
            }}
            className={currentPage === idx ? 'active' : ''}
          />
        ))}
      </S.DotsWrapper>
    </S.Container>
  );
};

export default PageSlider;

const S = {
  Container: styled.div`
    width: 100%;
    height: 100%;
  `,
  PagesWrapper: styled.div`
    width: 100%;
    display: flex;
    flex-wrap: nowrap;
    overflow-x: scroll;
    overflow-y: hidden;
    scroll-snap-type: x mandatory;

    & > div {
      flex: 0 0 auto;
      scroll-margin: 0;
      scroll-snap-align: start;
    }
    &::-webkit-scrollbar {
      display: none; /* Chrome, Safari, Opera*/
    }
    -ms-overflow-style: none; /* IE and Edge */
    scrollbar-width: none; /* Firefox */
  `,
  DotsWrapper: styled.div`
    width: 100%;
    margin: 10px auto;
    display: flex;
    gap: 7px;
    justify-content: center;
  `,
  Dot: styled.div`
    width: 10px;
    height: 10px;
    border-radius: 50%;
    background-color: #8e8e8e;
    transition: all ease-in-out 0.1s;

    &.active {
      width: 22px;
      border-radius: 10px;
      background-color: ${({ theme }) => theme.primary};
    }
  `,
};
