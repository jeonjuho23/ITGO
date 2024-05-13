package itgo.it_secondhand.service.like;


import itgo.it_secondhand.service.like.DTO.LikeReqDTO;

import java.util.List;

public interface LikeService<T,F> {

    public Long regist(LikeReqDTO<F> likeReqDTO);

    public void delete(LikeReqDTO<Long> likeReqDTO);

    public List<T> checkList(Long memberId);

}
