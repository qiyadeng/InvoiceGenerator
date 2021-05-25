(function () {
    var s = {
        ENV:'DEV',
        BIZ_VERSION: '2.7',//测试 DEV, 正式PROD  运行环境
        DEFAULT_PLATFORM_VESION: '0',
        FE_VERSION: '20200926', //前端版本号
    }
    var apiConfig = function ($) {
        function _config() {
            var PARAM = {
                DEV:{
                    qrcodeUrl:'http://192.168.0.4:8081/i?lsh={0}'
                },
                PROD:{
                    qrcodeUrl:'http://192.168.0.4:8081/i?lsh={0}'
                }
            }
            return PARAM[s.ENV];
        }
        function _qrcodeUrl() {
            return _config().qrcodeUrl
        }
        return {
            config:_config,
            qrcodeUrl:_qrcodeUrl()
        }
    }

    if (typeof require != 'undefined') {
        define(['jquery'], function ($) {
            return apiConfig($);
        })
    } else {
        window.apiConfig = apiConfig($);
    }
})();