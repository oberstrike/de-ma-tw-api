package de.ma.tw.utils.http

import com.marcinziolo.kotlin.wiremock.RequestSpecification
import com.marcinziolo.kotlin.wiremock.contains
import com.marcinziolo.kotlin.wiremock.equalTo




fun RequestSpecification.applyAndroidBrowserHeaders(){
    headers contains "pragma" equalTo "no-cache"
    headers contains "cache-control" equalTo "no-cache"
    headers contains "upgrade-insecure-requests" equalTo "1"
    headers contains "user-agent" equalTo "Mozilla/5.0 (Linux; Android 11; sdk_gphone_x86 Build/RSR1.201013.001; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/83.0.4103.106 Mobile Safari/537.36"
    headers contains "accept" equalTo "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9"
    headers contains "x-requested-with" equalTo "air.com.innogames.staemme"
    headers contains "sec-fetch-site" equalTo "none"
    headers contains "sec-fetch-mode" equalTo "navigate"
    headers contains "sec-fetch-user" equalTo "?1"
    headers contains "sec-fetch-dest" equalTo "document"
    headers contains "accept-encoding" equalTo "gzip, deflate"
    headers contains "accept-language" equalTo "en-US,en;q=0.9"
}