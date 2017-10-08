//
// Created by sontr on 9/26/2017.
//

#include "ApplicationInfo.h"

const std::string ApplicationInfo::APP_ENCRYPT_KEY = "KEY SO 1";
const std::string ApplicationInfo::APP_FIRE_BASE_KEY = "KEY SO 2";
const std::string ApplicationInfo::APP_SQLITE_ENCRYPT_KEY = "KEY SO 3";

std::string ApplicationInfo::getApplicationEncryptKey() {
    return ApplicationInfo::APP_ENCRYPT_KEY;
}

std::string ApplicationInfo::getApplicationFirebaseKey() {
    return ApplicationInfo::APP_FIRE_BASE_KEY;
}

std::string ApplicationInfo::getApplicationSQLiteEncryptKey() {
    return ApplicationInfo::APP_SQLITE_ENCRYPT_KEY;
}

