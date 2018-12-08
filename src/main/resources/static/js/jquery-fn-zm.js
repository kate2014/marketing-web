$(function () {
    var _load = $.fn.load;
    $.fn.load = function (url, params, callback) {
        var self = this;
        /*$('.select-com').load('[[${basePath}]]/customer/service  #container', null, function (result) {
            $("script").each(function () {
                if (this.src) {
                    //$select_com.appendTo('#container');
                    //alert(this.src);
                    $.getScript(this.src);
                } else {

                }
            });
        });*/

        //此处用apply方法调用原来的load方法，因为load方法属于对象，所以不可直接对象._load（...）
        return _load.apply(this, [url, params, callback && function (response, status, jqXHR) {
            self.each(function () {
                callback.apply(this, response || [jqXHR.responseText, status, jqXHR]);
            });
        }]);
    }
});