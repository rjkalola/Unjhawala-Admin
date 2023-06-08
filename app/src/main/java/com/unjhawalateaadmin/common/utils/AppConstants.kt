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
        const val NOTIFICATION_TYPE = "NOTIFICATION_TYPE"
        const val USER_ID = "USER_ID"
        const val WEB_URL = "WEB_URL"
        const val TITLE = "TITLE"
        const val AGENCY_DETAILS = "AGENCY_DETAILS"
        const val CONFIGURATION_TYPE = "CONFIGURATION_TYPE"
        const val CONFIGURATION_TYPE_NAME = "CONFIGURATION_TYPE_NAME"
        const val IS_FROM_NOTIFICATION = "IS_FROM_NOTIFICATION"
        const val IMAGE_URI = "image_uri"
        const val CROP_RATIO_X = "crop_ratio_X"
        const val CROP_RATIO_Y = "crop_ratio_Y"
        const val FILE_EXTENSION = "file_extension"
        const val TEA_SAMPLE_INFO = "TEA_SAMPLE_INFO"
        const val AVAILABLE_TEA_SAMPLE_DATA = "AVAILABLE_TEA_SAMPLE_DATA"
        const val TEA_SOURCE_LEVEL = "TEA_SOURCE_LEVEL"
        const val ID = "ID"

        const val CAMERA_PERMISSION = 1
        const val REQUEST_CROP_IMAGE = 2
        const val EXTERNAL_STORAGE_PERMISSION = 2

    }

    object Direction {
        const val LEFT = 1
        const val RIGHT = 2
        const val CLEAR = 3
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
        const val DELETE_ITEM = 2
        const val UPDATE_APP = 3
        const val SELECT_LEAF_TYPE = 4
        const val SELECT_VENDOR = 5
        const val SELECT_GARDEN = 6
        const val SELECT_GRADE = 7
        const val SELECT_AREA = 8
        const val SELECT_CUTTING = 9
        const val SELECT_COLOUR = 10
        const val SELECT_DENSITY = 11
        const val SELECT_TEA_SOURCE_LEVEL_1 = 12
        const val SELECT_TEA_SOURCE_LEVEL_2 = 13
        const val SELECT_TEA_SOURCE_LEVEL_3 = 14
        const val SELECT_SEASON_AND_QUALITY = 15
        const val SELECT_OUR_QUALITY = 16
        const val SELECT_TEA_PREFERENCE = 17
        const val SELECT_FIRST_LEVEL = 18
        const val SELECT_SECOND_LEVEL = 19
        const val CLEAR_FILTER = 20
        const val TEA_SAMPLE_RATING = 21
        const val SELECT_PERSONAL_GRADE = 22

        const val BIRTH_DATE = "BIRTH_DATE"
        const val SELECT_DATE = "SELECT_DATE"
    }

    object LocationMode {
        const val LOCATION_MODE_HIGH_ACCURACY = 1
    }

    object TeaConfiguration {
        const val TEA_GARDEN_AREA = 1
        const val LEAF_TYPE = 2
        const val TEA_GARDEN = 3
        const val TEA_GRADE = 4
        const val TEA_QUALITY = 5
        const val TEA_TYPE = 6
        const val TEA_COLOR = 7
        const val TEA_DENSITY = 8
        const val TEA_SEASON = 9
        const val TEA_PRODUCT_PREFERENCE = 10
        const val TEA_INWARD_BAG_TYPE = 11
        const val TEA_SOURCE = 12
    }

    object Action {
        const val RETAILER_DETAILS = 1
        const val APPROVE_TERMS_CONDITIONS = 2
        const val CONFIGURATION_DETAILS = 3
        const val EDIT_CONFIGURATION_ITEM = 4
        const val EDIT_TEA_SAMPLE = 5
        const val ADD_TEA_SAMPLE_TESTING = 6
        const val SELECT_PDF = 7
        const val SELECT_GALLERY_IMAGE = 8
        const val SELECT_CAMERA_IMAGE = 9
        const val ADD_TEA_SAMPLE_Quantity = 10
        const val DELETE_TEA_SAMPLE_Quantity = 11
        const val EDIT_TEA_SOURCE = 12
        const val DELETE_TEA_SOURCE = 13
        const val VIEW_TEA_SAMPLE = 14
        const val SHARE_CONTENT = 15
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

        const val TEA_SOURCE_LEVEL_1 = 1
        const val TEA_SOURCE_LEVEL_2 = 2
        const val TEA_SOURCE_LEVEL_3 = 3

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
        const val CONFIGURATION_ITEM_LIMIT = 20
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

