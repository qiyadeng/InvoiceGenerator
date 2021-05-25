(function() {
    var utilsFn = function ($) {
        function format() {
            if (arguments.length == 0)
                return null;
            var str = arguments[0];
            var re = '', i = 0;
            // 如果第二个参数是数组
            if ($.isArray(arguments[1])) {
                var array = arguments[1];
                for (; i < array.length; i++) {
                    re = new RegExp('\\{' + i + '\\}', 'gm');
                    str = str.replace(re, array[i]);
                }
            } else {
                for (i = 1; i < arguments.length; i++) {
                    re = new RegExp('\\{' + (i - 1) + '\\}', 'gm');
                    str = str.replace(re, arguments[i]);
                }
            }
            return str;
        }
        return {
            format:format
        };
    }


    if (typeof require != 'undefined') {
        define(['jquery'], function ($) {
            return utilsFn($);
        })
    } else {
        window.utils = utilsFn($);
    }
})();