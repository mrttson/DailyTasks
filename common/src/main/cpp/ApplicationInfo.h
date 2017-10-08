//
// Created by sontr on 9/26/2017.
//

#ifndef DAILYTASKS_APPLICATION_INFO_H
#define DAILYTASKS_APPLICATION_INFO_H

#include <jni.h>
#include <string>

class ApplicationInfo {
public:
    static std::string getApplicationEncryptKey();
    static std::string getApplicationFirebaseKey();
    static std::string getApplicationSQLiteEncryptKey();

private:
    static const std::string APP_ENCRYPT_KEY;
    static const std::string APP_FIRE_BASE_KEY;
    static const std::string APP_SQLITE_ENCRYPT_KEY;
};

#endif //DAILYTASKS_APPLICATION_INFO_H
