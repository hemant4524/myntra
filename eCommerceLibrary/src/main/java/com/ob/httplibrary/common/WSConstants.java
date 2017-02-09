package com.ob.httplibrary.common;

public class WSConstants {
    //    public static final String BASE_URL = "http://172.16.120.69/ob-product-web-client-s/web/app_dev.php/api/v1/";
    //    public static final String BASE_URL = "http://162.242.214.42/aakronline.com/web/api/v1/";
    //   public static final String BASE_URL = "http://demo.aakronline.ca/app_dev.php/api/v1/";
    // public static final String BASE_URL="http://172.16.120.110/ob-product-web-client/web/app_mobile.php/api/v1/"; // For temporary
//    public static  String BASE_URL="http://172.16.120.110/ob-product-web-client/web/app_dev.php/api/v1/"; // For development
    public static String BASE_URL;
//    public static final String BASE_URL = "http://aakronline.com/app_dev.php/api/v1/";
    //public static final String BASE_URL = "http://aakronline.com/api/v1/";

    public static final int URL_DEVELOPMENT = 0;
    public static final int URL_LIVE = 1;
    public static final int URL_QA= 2;

    public static final int STATUS_OK = 1;
    public static final int STATUS_ERROR = 0;


    public static void initUrlConstants(int urlType) {

        if (urlType == URL_LIVE)
            BASE_URL = "http://aakronline.com/api/v1/";
        else if (urlType == URL_DEVELOPMENT) {
            BASE_URL = "http://172.16.120.110/aakronline/web/app_dev.php/api/v1/";
            BASE_URL = "http://172.16.120.83/5460/aakron_web_client/web/app.php/api/v1/";
        }
        else if (urlType == URL_QA)
            BASE_URL = "http://172.16.120.29/aakron_web_client/web/app_qa.php/api/v1/";


    }

    public static final String WS_RESPONSE_FORMAT = "?_format=json";

    //Define All Urls Here.
    public static final String WS_LOGIN = "/get_login";
    public static final String WS_FORGOT_PASSWORD = "/forgot-password";
    public static final String WS_BROWSER_CATEGORY = "/browse-category";
    public static final String WS_MENU_CATEGORY = "/cms/menu/";
    public static final String WS_REGISTER = "/register?_format=json";
    public static final String WS_REGISTER_EDIT = "/my-account/edit-info?_format=json";
    public static final String WS_COUNTRY_LIST = "/get-all-country";
    public static final String WS_STATE_LIST = "/get-state-by-country/";
    public static final String WS_CITY_LIST = "/get-city-by-state-country/";
    public static final String WS_SEARCH = "/search";
    public static final String WS_SEARCH_FILTER = "/search-attribute-filter-list";
    public static final String WS_HOME_CONTENT = "/get-home-page-listing";
    public static final String WS_SUB_CATEGORY_LIST = "/browse-sub-category/";
    public static final String WS_CHANGE_PASSWORD = "/my-account/change-password";
    public static final String WS_PRODUCT_DETAIL = "/product/";
    public static final String WS_PRODUCT_LIST = "/category-product-list/";
    public static final String WS_ADDRESS_TYPE = "/get-contact-book-address-type";
    public static final String WS_GET_ADDRESS_BOOK = "/my-account/address-book/view";
    public static final String WS_ADD_ADDRESS = "/my-account/address-book/add";
    public static final String WS_DELETE_ADDRESS = "/my-account/address-book/delete";
    public static final String WS_EDIT_ADDRESS = "/my-account/address-book/edit/";
    public static final String WS_WISH_LIST = "/wish-list";
    public static final String WS_ADD_TO_WISH_LIST = "/add-to-wishlist-product";
    public static final String WS_REMOVE_FROM_WISH_LIST = "/wish-list-delete";
    public static final String WS_LOGOUT = "api-logout";
    public static final String WS_PROFILE_VIEW = "/my-account/view-info";
    public static final String WS_PROFILE_PHOTO_UPLOAD = "/my-account/edit-photo";
    public static final String WS_SIMILAR_PRODUCT = "/similar-sku";
    public static final String WS_GET_DEFAULT_ADDRESS = "/address-book/get-default-address";
    public static final String WS_SUBMIT_CATALOG_FLYER = "/ecatalog/order/save";
    public static final String WS_INVENTORY_LOOKUP = "product-inventory-lookup";
    public static final String WS_GET_SAMPLE_PAYMENT = "/payment";
    public static final String WS_MAPPED_PRODUCT_CATEGORY = "/mapped-product-list/";
    public static final String WS_TAG_PRODUCT_CATEGORY = "/browse-tag-category/";
    public static final String WS_SET_PRODUCT_TO_CART = "/set-product-to-cart";
    public static final String WS_ADD_IMPRINT_POSITION = "/add-imprint-location";
    public static final String WS_ADD_IMPRINT_POSITION_ORDER = "/order-add-imprint-location";
    public static final String WS_ADD_IMPRINT_SELECT_COLOR = "/select-color-request-quote";
    public static final String WS_ADD_IMPRINT_SELECT_COLOR_ORDER = "/order-select-color-request-quote";
    public static final String WS_SELECT_IMPRINT_METHOD = "/select-method";
    public static final String WS_SELECT_IMPRINT_METHOD_ORDER = "/order-select-method";
    public static final String WS_SELECT_ARTWORK_LOGO = "/select-logo-block";
    public static final String WS_UPLOAD_ARTWORK = "/upload-artwork-detail";
    public static final String WS_REQUEST_QUOTE_CONFIGURATION = "/get-request-quote-configuration-rule";
    public static final String WS_SUBMIT_REQUEST_QUOTE = "/set-request-quote-session";
    public static final String WS_SHARE_PRODUCT_VIA_MAIL = "/product-detail-email";


    //Define all WS constants here.
    public static final int REQUEST_CODE_CALL_GET_SEARCH_PRODUCT_ITEM_WS = 0;
    public static final int REQUEST_CODE_CALL_REGISTRATION_WS = 200;
    public static final int REQUEST_CODE_CALL_COUNTRY_LIST_WS = 201;
    public static final int REQUEST_CODE_CALL_STATE_LIST_WS = 202;
    public static final int REQUEST_CODE_CALL_CITY_LIST_WS = 203;
    public static final int REQUEST_CODE_CALL_INVENTORY_LOOKUP = 204;

    public static final int REQUEST_CODE_CALL_GET_FORGOT_PASSWORD = 500;
    public static final int REQUEST_CODE_CALL_GET_LOGIN = 501;
    public static final int REQUEST_CODE_CALL_GET_SEARCH = 502;
    public static final int REQUEST_CODE_CALL_GET_SEARCH_FILTER_ATTRIBUTE = 503;
    public static final int REQUEST_CODE_CALL_GET_HOME_SCREEN_CONTENT = 504;
    public static final int REQUEST_CODE_CALL_SUB_CATEGORY_LIST = 505;
    public static final int REQUEST_CODE_CALL_PRODUCT_DETAIL = 506;
    public static final int REQUEST_CODE_CALL_PRODUCT_LIST = 507;
    public static final int REQUEST_CODE_CALL_GET_ADDRESS_BOOK = 508;
    public static final int REQUEST_CODE_CALL_GET_LOGOUT = 509;
    public static final int REQUEST_CODE_CALL_DELETE_ADDRESS_BOOK = 510;
    public static final int REQUEST_CODE_CALL_WISH_LIST = 511;
    public static final int REQUEST_CODE_CALL_WISH_LIST_DELETE = 512;
    public static final int REQUEST_CODE_CALL_PROFILE_VIEW = 513;
    public static final int REQUEST_CODE_CALL_PROFILE_PHOTO_UPLOAD = 514;
    public static final int REQUEST_CODE_CALL_GET_DEFAULT_ADDRESS = 515;
    public static final int REQUEST_CODE_CALL_POST_CATALOG_FLYER_SUBMIT = 516;
    public static final int REQUEST_CODE_CALL_TAG_PRODUCT_CATEGORY = 517;
    public static final int REQUEST_CODE_CALL_ADD_IMPRINT_POSITION = 518;
    public static final int REQUEST_CODE_CALL_SELECT_IMPRINT_COLOR = 519;
    public static final int REQUEST_CODE_CALL_SELECT_IMPRINT_METHOD = 520;
    public static final int REQUEST_CODE_CALL_SELECT_ARTWORK_LOGO = 521;
    public static final int REQUEST_CODE_CALL_UPLOAD_ARTWORK_LOGO = 522;
    public static final int REQUEST_CODE_CALL_REQUEST_QUOTE_CONFIG = 523;
    public static final int REQUEST_CODE_CALL_SUBMIT_REQUEST_QUOTE = 524;
    public static final int REQUEST_CODE_CALL_SUBMIT_PLACE_ORDER = 525;

    public static final int REQUEST_CODE_CALL_GET_BROWSE_CATEGORY_WS = 1;
    public static final int REQUEST_CODE_CALL_ADDRESS_TYPE = 2;

    public static final int REQUEST_CODE_CALL_GET_CHANGE_PASSWORD = 3;
    public static final String ACCESS_TOKEN_KEY = "access_token";
    public static final String REFRESH_TOKEN_KEY = "refresh_token";
    public static final String REFERENCE_ACCESS_TOKEN_KEY = "reference_access_token";
    public static final int REQUEST_CODE_CALL_ADD_ADDRESS = 4;
    public static final int REQUEST_CODE_CALL_EDIT_ADDRESS = 5;
    public static final int REQUEST_CODE_PRODUCT_WISH_LIST_ADD = 6;
    public static final int REQUEST_CODE_PRODUCT_WISH_LIST_DELETE = 7;

    public static final String WS_BASE_FILENAME = "/base_filename";
    public static final int REQUEST_CODE_CALL_SIMILAR_PRODUCT = 8;
    public static final int REQUEST_CODE_CALL_POST_REQUEST_SAMPLE_TO_CART = 9;
    public static final int REQUEST_CODE_CALL_POST_SAMPLE_PAYMENT = 10;
    public static final int REQUEST_CODE_CALL_GET_MENU_CATEGORY_WS = 11;
    public static final int REQUEST_CODE_CALL_POST_SHARE_PRODUCt = 11;





}
