package itgo.it_secondhand.service.post;

import itgo.it_secondhand.domain.SecondhandScrapedPost;
import itgo.it_secondhand.service.post.DTO.*;
import org.springframework.data.domain.Slice;


public interface ScrapingPostService {
    public ScrapedPostViewResDTO viewScrapingPost(PostViewReqDTO postViewReqDTO);
    public FindPostResDTO findALlScrapingPostList(FindPostReqDTO findPostReqDTO);
    public FindPostResDTO findLikeScrapingPostList(FindPostReqDTO findPostReqDTO);
    public FindPostResDTO findScrapingPostListByCategory(FindPostByCategoryReqDTO findPostByCategoryReqDTO);
    FindPostResDTO findScrapingPostListByLocation(FindPostByLocationReqDTO findPostByLocationReqDTO);
}
