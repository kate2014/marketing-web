$(function () {
    //正整数
    $(".zm_input_number").keyup(function () {
        $(this).val(this.value.replace(/\D|^0/g,''));
    }).bind("paste",function(){
        $(this).val(this.value.replace(/\D|^0/g,''));
    });

    //数字0.00

    //text文本

    //密码password

    //datetime 时间日期
    //date 日期键盘
    //time 时间键盘

    //file

})