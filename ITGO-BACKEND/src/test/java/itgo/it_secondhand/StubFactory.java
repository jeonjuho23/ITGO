package itgo.it_secondhand;

import itgo.it_secondhand.domain.*;
import itgo.it_secondhand.domain.value.Location;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StubFactory {


    public static Device getDevice() {
        return Device.builder()
                .id(1L)
                .detailId("detailId")
                .deviceName("deviceName")
                .category(getCategory())
                .launchPrice(1000)
                .releaseDate(getLocalDateTimeNow())
                .build();
    }

    public static MemberLikeDevice getMemberLikeDevice() {
        return MemberLikeDevice.builder()
                .id(1L)
                .device(getDevice())
                .member(getMember())
                .likeDate(getLocalDateTimeNow())
                .build();
    }

    public static Member getMember() {
        return Member.builder()
                .id(1L)
                .name("name")
                .phone("phone")
                .location(getLocation())
                .imgAddress("imgAddress")
                .build();
    }

    public static MemberLikeLocation getMemberLikeLocation() {
        return MemberLikeLocation.builder()
                .id(1L)
                .location(getLocation())
                .member(getMember())
                .likeDate(getLocalDateTimeNow())
                .build();
    }

    public static LocationMongo getLocationMongo() {
        return LocationMongo.builder()
                .id("id")
                .location(getLocation())
                .build();
    }

    public static MemberLikePost getMemberLikeSecondhandScrapedPost() {
        SecondhandScrapedPost secondhandScrapedPost = getSecondhandScrapedPost();
        return MemberLikePost.builder()
                .id(1L)
                .post(secondhandScrapedPost)
                .member(secondhandScrapedPost.getMember())
                .likeDate(getLocalDateTimeNow())
                .build();
    }

    public static SecondhandScrapedPost getSecondhandScrapedPost() {
        return SecondhandScrapedPost.builder()
                .id(1L)
                .device(getDevice())
                .postUrl("postUrl")
                .location(getLocation())
                .member(getMember())
                .postTime(getLocalDateTimeNow())
                .secondhandPrice(1000)
                .postTitle("postTitle")
                .postContent("postContent")
                .postUpdateTime(getLocalDateTimeNow())
                .postLikeCount(0)
                .postViewCount(0)
                .imgFolderAddress("imgFolderAddress")
                .build();
    }

    public static Notification getNotification() {
        return Notification.builder()
                .id("id")
                .memberId(getMember().getId())
                .messages(getNotificationMessageList())
                .build();
    }

    public static Keyword getKeyword() {
        return Keyword.builder()
                .id(1L)
                .keyword("keyword")
                .count(0)
                .build();
    }

    public static List<MemberSearchKeyword> getMemberSearchKeywordList() {
        return new ArrayList<>(List.of(getMemberSearchKeyword()));
    }

    public static Slice<MemberSearchKeyword> getMemberSearchKeywordSlice() {
        return new SliceImpl<>(getMemberSearchKeywordList(), getPageable(), false);
    }

    public static Slice<MemberSearchKeyword> getMemberSearchKeywordSlice(boolean hasNext) {
        return new SliceImpl<>(getMemberSearchKeywordList(), getPageable(), hasNext);
    }

    public static MemberViewPost getMemberViewPost(SecondhandScrapedPost secondhandScrapedPost) {
        return MemberViewPost
                .createMemberViewPost(secondhandScrapedPost.getMember(), secondhandScrapedPost);
    }

    public static MemberSearchKeyword getMemberSearchKeyword() {
        return MemberSearchKeyword.createMemberSearchKeyword(getMember(), getKeyword());
    }

    public static List<SecondhandScrapedPost> getSecondhandScrapedPostList() {
        return new ArrayList<>(List.of(getSecondhandScrapedPost()));
    }

    public static Slice<SecondhandScrapedPost> getSecondhandScrapedPostSlice() {
        return new SliceImpl<>(getSecondhandScrapedPostList(), getPageable(), false);
    }

    public static List<NotificationMessage> getNotificationMessageList() {
        return new ArrayList<>(List.of(getNotificationMessage()));
    }

    private static NotificationMessage getNotificationMessage() {
        return new NotificationMessage("message", getLocalDateTimeNow());
    }

    public static List<LocationMongo> getLocationMongoList() {
        return new ArrayList<>(List.of(getLocationMongo()));
    }

    public static List<MemberLikeLocation> getMemberLikeLocationList() {
        return new ArrayList<>(List.of(getMemberLikeLocation()));
    }

    private static LocalDateTime getLocalDateTimeNow() {
        return LocalDateTime.now();
    }

    public static List<MemberLikeDevice> getMemberLikeDeviceList() {
        return new ArrayList<>(List.of(getMemberLikeDevice()));
    }

    public static Location getLocation() {
        return new Location("city", "street", "zipcode");
    }

    public static List<Device> getDeviceList() {
        return new ArrayList<>(List.of(getDevice()));
    }

    public static Slice<Device> getDeviceSlice() {
        return new SliceImpl<>(getDeviceList(), getPageable(), false);
    }

    public static Slice<Device> getDeviceSlice(List<Device> content) {
        return new SliceImpl<>(content, getPageable(), false);
    }

    public static Pageable getPageable() {
        return PageRequest.of(0, 10);
    }

    public static Category getCategory() {
        return Category.createCategory("manufacturer", "deviceType");
    }

    public static MobileInfo getMobileInfo(){
        return MobileInfo.builder()
                .id("detailId")
                .modelname("modelName")
                .image("image")
                .build();
    }

    public static LaptopInfo getLaptopInfo() {
        return LaptopInfo.builder()
                .id("detailId")
                .modelname("modelName")
                .build();
    }

    public static Optional<MobileInfo> getOptionalMobileInfo() {
        return Optional.of(getMobileInfo());
    }

    public static Optional<LaptopInfo> getOptionalLaptopInfo() {
        return Optional.of(getLaptopInfo());
    }

}
