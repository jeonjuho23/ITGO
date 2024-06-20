package itgo.it_secondhand.service.like;


import itgo.it_secondhand.service.like.DTO.LikeReqDTO;

import java.util.List;

public interface LikeService<T,F> {

    Long regist(LikeReqDTO<F> likeReqDTO);

    void delete(LikeReqDTO<Long> likeReqDTO);

    List<T> checkList(Long memberId);

}
