package com.unjhawalateaadmin.common.utils

import com.imateplus.utilities.utils.DateFormatsConstants

object AppConstants {
    const val UNAUTHORIZED = 401
    const val DEVICE_TYPE = 1

    //    const val USER_TYPE = UserType.User
    const val CURRENCY = "₤"
    const val MAX_IMAGE_WIDTH = 1280
    const val MAX_IMAGE_HEIGHT = 1280
    const val IMAGE_QUALITY = 80
    const val defaultDateFormat = DateFormatsConstants.DD_MMM_YYYY_SPACE
    const val apiDateFormat = DateFormatsConstants.DD_MM_YYYY_DASH
    const val defaultCountryCode = "GB"
    const val guard = "api"
    const val FCM_ROOM = "chatRooms"
    const val FCM_USERS = "users"
    const val FCM_MESSAGES = "messages"
    const val SERVER_IMAGE_PATH = "https://otmsystem.com/storage/app/user_images/"
    const val EXTRA_CHANNEL_SID = "C_SID"
    const val DYNAMIC_LINK_PREFIX = "https://swastikenterprises.page.link"

    const val DYNAMIC_LINK_URL = "https://tilebazar.com"

    object IntentKey {
        const val PHONE_NUMBER = "PHONE_NUMBER"
        const val IMAGE_URI = "image_uri"
        const val CROP_RATIO_X = "crop_ratio_X"
        const val CROP_RATIO_Y = "crop_ratio_Y"
        const val FILE_EXTENSION = "file_extension"
        const val PRODUCT_INFO = "PRODUCT_INFO"
        const val NOTIFICATION_TYPE = "NOTIFICATION_TYPE"
        const val IS_FROM_NOTIFICATION = "IS_FROM_NOTIFICATION"
        const val PRODUCT_ID = "PRODUCT_ID"
        const val PRODUCT_RESOURCES = "PRODUCT_RESOURCES"
        const val PRODUCT_DETAILS = "PRODUCT_DETAILS"
        const val USER_DETAILS = "USER_DETAILS"
        const val PRODUCT_STOCKS = "PRODUCT_STOCKS"
        const val USER_ID = "USER_ID"
        const val USER_TYPE = "USER_TYPE"
        const val WEB_URL = "WEB_URL"
        const val TITLE = "TITLE"
        const val INWARD_ID = "INWARD_ID"
        const val DRIVE_INWARD_DETAILS = "DRIVE_INWARD_DETAILS"
        const val FROM_AGENCY = "FROM_AGENCY"
        const val AREA_ID = "AREA_ID"
        const val CITY_ID = "CITY_ID"
        const val AREA_NAME = "AREA_NAME"
        const val WORKING_AREA_DETAILS = "WORKING_AREA_DETAILS"
        const val AGENCY_DETAILS = "AGENCY_DETAILS"
        const val PERSONAL_DETAILS = "PERSONAL_DETAILS"
        const val POLICY_TYPE = "POLICY_TYPE"
        const val TO_DO_DETAILS = "TO_DO_DETAILS"
        const val PERSONAL_DOCUMENTS = "PERSONAL_DOCUMENTS"
        const val PERSONAL_DOCUMENT_TITLE = "PERSONAL_DOCUMENT_TITLE"
        const val SCAN_ORDER_DATA = "SCAN_ORDER_DATA"
        const val REQUEST_CROP_IMAGE = 1
        const val EXTERNAL_STORAGE_PERMISSION = 2
        const val CAMERA_PERMISSION = 3
        const val HISTORY_TYPE = "HISTORY_TYPE"
        const val ORDER_TYPE = "ORDER_TYPE"
        const val ORDER_DETAILS = "ORDER_DETAILS"
        const val RATE_PRINT_TYPE = "RATE_PRINT_TYPE"
    }

    object SharedPrefKey {
        const val USER_INFO = "USER_INFO"
        const val USERS = "USERS"
        const val APP_URL = "APP_URL"
        const val THEME_MODE = "THEME_MODE"
        const val CHAT_USER_INFO = "CHAT_USER_INFO"
        const val DEVICE_ID = "DEVICE_ID"
        const val PRODUCT_CONFIGURATION = "PRODUCT_CONFIGURATION"
        const val COMPARE_PRODUCT_IDS = "COMPARE_PRODUCT_IDS"
        const val INTRODUCTION_SLIDER = "INTRODUCTION_SLIDER"
    }

    object THEME_MODE {
        const val LIGHT = 0
        const val DARK = 1
    }

    object DialogIdentifier {
        const val LOGOUT = 1
        const val PLACE_ORDER = 2
        const val DELETE_NOTE = 3
        const val SELECT_COUNTRY = 4
        const val SELECT_STATE = 5
        const val SELECT_DISRICT = 6
        const val SELECT_TALUKA = 7
        const val SELECT_CITY = 8
        const val SELECT_AREA = 9
        const val SELECT_FIRM_TYPE = 10
        const val PLACE_ORDER_SUCCESS = 11
        const val REJECT_ORDER = 12
        const val DELIVER_ORDER = 13
        const val UPDATE_APP = 14
        const val DELETE_ORDER_ITEM = 15

        const val BIRTH_DATE = "BIRTH_DATE"
        const val ANNIVERSARY_DATE = "ANNIVERSARY_DATE"
        const val REMINDER_DATE = "REMINDER_DATE"
        const val REMINDER_TIME = "REMINDER_TIME"
    }

    object LocationMode {
        const val LOCATION_MODE_HIGH_ACCURACY = 1
    }

    object Action {
        const val RETAILER_DETAILS = 1
        const val WORKING_AREA_DETAILS = 2
        const val DONE_REWARD = 3
        const val AGENCY_DETAILS = 4
        const val SELECT_PRODUCT = 5
        const val SELECT_PRODUCT_COMPONENT = 6
        const val DECREASE_QUANTITY = 7
        const val INCREASE_QUANTITY = 8
        const val ADD_TO_DO_NOTE = 9
        const val APPROVE_TERMS_CONDITIONS = 10
    }

    object UserType {
        const val Dealer = 1
        const val Agency = 2
        const val Retailer = 3
    }

    object FileExtension {
        const val JPG = ".jpg"
        const val PNG = ".png"
        const val PDF = ".pdf"
        const val MP3 = ".mp3"
        const val M4A = ".m4a"
    }

    object Type {
        const val CAMERA = "camera"
        const val PDF = "pdf"
        const val SELECT_FROM_CAMERA = 1
        const val SELECT_PHOTOS = 2
        const val SELECT_PDF = 3
        const val SELECT_VIDEO_FROM_CAMERA = 4

        const val TERMS_CONDITIONS = 1
        const val PRIVACY_POLICY = 2

        const val ADD_AGENCY = 1
        const val ADD_RETAILER = 2

        const val MY_ACCOUNT_CURRENT_MONTH_FILTER = 1
        const val MY_ACCOUNT_LAST_MONTH_FILTER = 2
        const val MY_ACCOUNT_CUSTOM_DATE_FILTER = 3

        const val TRANSFER_KG_LAST_WEEK_FILTER = 1
        const val TRANSFER_KG_LAST_MONTH_FILTER = 2
        const val TRANSFER_KG_CUSTOM_DATE_FILTER = 3

        const val KG_HISTORY = 1
        const val CURRENT_YEAR_KG_HISTORY = 2
        const val LAST_YEAR_KG_HISTORY = 3

        const val PENDING_ORDER = 1
        const val DELIVERED_ORDER = 2
        const val REJECTED_ORDER = 3

        const val RATE_PRINT_DEALER = 1
        const val RATE_PRINT_AGENCY = 2
        const val RATE_PRINT_RETAILER = 3

    }

    object Grade {
        const val Premium = 1
        const val Standard = 2
        const val Commercial = 3
        const val Eco = 4
        const val OneTimeLot = 5
    }

    object SortBy {
        const val PRODUCT_NAME_A_TO_Z = 1
        const val PRODUCT_NAME_Z_To_A = 2
        const val PRODUCT_PRICE_LOW_TO_HIGH = 3
        const val PRODUCT_PRICE_HIGH_TO_LOW = 4
        const val PRODUCT_DATE_MODIFIED = 5

        const val SELLER_NAME_A_TO_Z = 1
        const val SELLER_NAME_Z_To_A = 2
        const val SELLER_DATE_MODIFIED = 3
    }

    object DataLimit {
        const val USERS_LIMIT = 20
        const val PRODUCTS_LIMIT = 20
        const val DRIVE_INWARD_LIMIT = 15
    }

    object Directory {
        const val DEFAULT = "otmjobs"
        const val IMAGES = "otmjobs/images"
    }

    object Status {
        const val SUCCESS = 1
        const val ERROR = 2
        const val LOADING = 3
    }

}

