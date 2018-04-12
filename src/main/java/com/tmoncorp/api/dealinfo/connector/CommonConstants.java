package com.tmoncorp.api.dealinfo.connector;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by laydios on 2017. 8. 19..
 */
public class CommonConstants {

    /*
     공통 부분
     */
    public static final boolean DIRECT_MATERIAL = false; // dealinfo API에서 딜과 옵션의 정보를 카우치베이스에서 조회시 material 쪽을 볼지 operation 을 볼지 선택하는 값. true 일 경우 material 을 바라본다.
    public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static final int MAX_BULK_LIMIT = 2000;
    public static final int DEFAULT_BULK_SIZE = 50;

    public static final int DEAL_FIND_ID_FORK_JOIN_POOL_DEFAULT = 100;
    public static final int DEAL_FIND_ID_FORK_JOIN_POOL = 100;
    public static final int OPTION_FIND_ID_FORK_JOIN_POOL = 200;
    public static final int DEAL_MAX_FORK_JOIN_POOL = 100;

    public static final int DISCOUNT_FORK_JOIN_POOL = 100;
    public static final int HONEY_COUPON_FORK_JOIN_POOL = 100;

    public static final int COUCHBASE_DEFAULT_TIMEOUT = 30 * 1000; // millisecond
    public static final int COUCHBASE_DEFAULT_READ_TIMEOUT = 75; // millisecond

    /*
    DealListInfo model 의 parsing 처리를 위한 부분
     */
    public static final String DEAL_META_VERSION = "0.0.1";

    /*
     ImageInfo model 의 parsing 처리를 위한 값
     */
    public static final String MOBILE_PARAMETER_KEY = "mobilelist";
    public static final String MOBILE3COL_PARAMETER_KEY = "col3list";

    // {딜번호}_DEAL document 의 root 에 존재하는 jaon path.
    public static final String MOBILE_IMAGE_UPDATE_PATH = "dealImages.t:mobile.updateDate";
    public static final String MOBILE3COL_IMAGE_UPDATE_PATH = "dealImages.t:catlist_3col_v2.updateDate";

    /*
    DealListInfo model 의 parsing 처리를 위한 값
     */
    // 웨어웨어 딜 구분을 위한 매출 카테고리 번호
    public static final long WHERE_SALES_CATEGORY_NO = 1233L;

    // {딜번호}_DEAL document 의 root 에 존재하는 jaon path.
    public static final String EXTERNAL_TYPE_PATH = "externalType";
    public static final String VIEW_CATEGORIES_PATH = "viewCategories";


    // {딜번호}_DEAL document 의 root 에 존재하는 jaon path.
    public static final String SALES_CATEGORY_NO_PATH = "baseInfo.salesCategoryNo";


    /*
    DealListUseDate model 의 parsing 처리를 위한 값
     */
    // {딜번호}_DEAL document 의 root 에 존재하는 jaon path.
    public static final String DEAL_LIST_USE_DATE_VIEW_YN = "contentInfo.dealListUseDate.deallist_usedate_view_yn";


    /*
    StickerInfo model 의 parsing 처리를 위한 값
     */
    public static final String STICKER_PLATFORM_TYPE_PATH = "platform"; // stickers 가 array 여서 stickers 하위의 path를 사용해야 함


    /*
    OPTION_TREE 의 parsing 처리를 위한 값
     */
    public static final String TREE_DATA_CHILDNODES_PATH = "treeData.childNodes";


    /*
    dealMax parsing 처리를 위한 부분
     */
    public static final String DEAL_MAX_SUFFIX = "dealstocksmall::";
    public static final String DEAL_MAX_CONTENT_KEY = "data";
    public static final String DEAL_MAX_UPDATE_TIME_KEY = "dealcache_uptime";
    public static final int DEAL_MAX_EXPIRE_MINUTE = 30;

    /*
    notice 정보의 parsing 처리를 위한 부분
     */
    public static final String NOTICE_CONTENT_KEY = "data";

    // 슈퍼마트 예약배송 대상 매출 카테고리
    public static List<Long> getMartReservationSalesCategoryNos() {
        return new ArrayList<>(Arrays.asList(1249L, 1250L, 1251L));
    }

    // 슈퍼마트 묶음배송 대상 매출 카테고리
    public static List<Long> getMartPackageSalesCategoryNos() {
        return new ArrayList<>(Arrays.asList(1222L, 1223L, 1224L, 1225L, 1235L, 1236L));
    }
}
