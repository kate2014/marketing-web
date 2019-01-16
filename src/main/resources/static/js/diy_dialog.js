function dialogBox(message, yesCallback, noCallback){
    if(message){
        $('.dialog-message').html(message);
    }
    // 显示遮罩和对话框
    $('.wrap-dialog').removeClass("hide");
    // 确定按钮
    $('#confirm').click(function(){
        $('.wrap-dialog').addClass("hide");
        yesCallback();

        $('#confirm').unbind("click");
        $('#cancel').unbind("click");
    });
    // 取消按钮
    $('#cancel').click(function(){
        $('.wrap-dialog').addClass("hide");
        noCallback();

        $('#confirm').unbind("click");
        $('#cancel').unbind("click");
    });
}

window.onload = function() {

    $("#showDialog").append("<div class=\"wrap-dialog hide\">\n" +
        "    <div class=\"dialog\">\n" +
        "        <div class=\"dialog-header\">\n" +
        "            <span class=\"dialog-title\">操作提示</span>\n" +
        "        </div>\n" +
        "        <div class=\"dialog-body\">\n" +
        "            <span class=\"dialog-message\">？</span>\n" +
        "        </div>\n" +
        "        <div class=\"dialog-footer\">\n" +
        "            <input type=\"button\" class=\"btn_OK\" id=\"confirm\" value=\"确认\" />\n" +
        "            <input type=\"button\" class=\"btn_cancle ml50\" id=\"cancel\" value=\"取消\" />\n" +
        "        </div>\n" +
        "    </div>\n" +
        "</div>");
}


