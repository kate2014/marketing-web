$(function(){
	$(".project").keyup(function(){
         var content_len = $(".project").val().length;
         $(".sub").text(content_len);
         if(content_len = 10){
         	alert("输入名称不允许超过10个字")
         }
    })
	
	/*选择商品*/
	var $li = $('.tab li');
    var $tabs = $('.select-tab .tabs');

    $li.click(function(){
        var $this = $(this);
        var $t = $this.index();
        $li.removeClass();
        $this.addClass('current');
        $tabs.css('display','none');
        $tabs.eq($t).css('display','block');

        var $dishIframe = $("#dish_iframe");
        if ($dishIframe[0]) {
            $tabs.css('display','block');
            var itemId = $this.attr("href");
            $dishIframe.attr("src", itemId);
        }
    })
    
    $('.select-shop').click(function(){
    	$('.select-com').show();
    })
    $('.close').click(function(){
    	$('.select-com').hide();
    })

    /*参团人数*/
   $(".detail-main table tr td:last-child").each(function(){  
        var currentEle = $(this);  
        currentEle.click(function(){  
           $('.list-text').show();
           $('.list-text').find('li').html($(this).html());
        });  
	}); 
	
	$('.detail-main .details').click(function(){
		$('.select-com').show();
	})

    /**
     * 富文本
     */
    $("body").css({visibility:"visible"});
    $("#back").click(function(){
        location.href = "promotion.html";
    });

    /*KindEditor.ready(function(K) {
        window.editor = K.create('#description',{
            items : [
                'source', '|', 'undo', 'redo', '|', 'preview', 'print', 'template', 'code', 'cut', 'copy', 'paste',
                'plainpaste', 'wordpaste', '|', 'justifyleft', 'justifycenter', 'justifyright',
                'justifyfull', 'insertorderedlist', 'insertunorderedlist', 'indent', 'outdent', 'subscript',
                'superscript', 'clearhtml', 'quickformat', 'selectall', '|', 'fullscreen', '/',
                'formatblock', 'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold',
                'italic', 'underline', 'strikethrough', 'lineheight', 'removeformat', '|', 'image', 'multiimage',
                'flash', 'media', 'insertfile', 'table', 'hr', 'emoticons', 'baidumap', 'pagebreak',
                'anchor', 'link', 'unlink', '|', 'about'
            ],
            allowFileManager:true,
            uploadJson : '../../image_upload.action',
            fileManagerJson : '../../image_manage.action'
        });
    });*/

    // 为保存按钮，添加click事件
    $("#save").click(function(){
        if($("#promotionForm").form('validate')){
            // 通过kindEditor数据到textarea
            window.editor.sync();
            // 提交表单
            $("#promotionForm").submit();
        }else{
            // 校验失败
            $.messager.alert("警告信息","表单中存在数据非法项！","warning");
        }
    });

    //全选与反选
    $("#checkAll").click(function() {
        /*if (this.checked){
            $("input[name='checkItem']:checkbox").each(function(){
                $(this).attr("checked", true);
            });
        } else {
            $("input[name='checkItem']:checkbox").each(function() {
                $(this).attr("checked", false);
            });
        }*/
        $("input[name='checkItem']:checkbox").prop("checked", this.checked);
    });

    //单选
    $("input[name='itemRadio']:checkbox").click(function () {
        $("input[name='itemRadio']:checkbox").prop("checked", false);
        $(this).prop("checked", true);
    });

    $("input[name='checkSingleItem']:checkbox").click(function () {
        $("input[name='checkSingleItem']:checkbox").prop("checked", false);
        $(this).prop("checked", true);
    });

})

Date.prototype.format = function(fmt) {
    var o = {
        "M+": this.getMonth() + 1,
        "d+": this.getDate(),
        "H+": this.getHours(),
        "m+": this.getMinutes(),
        "s+": this.getSeconds(),
        "S+": this.getMilliseconds()
    };

    //因位date.getFullYear()出来的结果是number类型的,所以为了让结果变成字符串型，下面有两种方法：


    if (/(y+)/.test(fmt)) {
        //第一种：利用字符串连接符“+”给date.getFullYear()+""，加一个空字符串便可以将number类型转换成字符串。

        fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    }
    for (var k in o) {
        if (new RegExp("(" + k + ")").test(fmt)) {

            //第二种：使用String()类型进行强制数据类型转换String(date.getFullYear())，这种更容易理解。

            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(String(o[k]).length)));
        }
    }
    return fmt;
}

function clearNoNum(obj){

    obj.value = obj.value.replace(/[^\d.]/g,""); //清除"数字"和"."以外的字符

    obj.value = obj.value.replace(/^\./g,""); //验证第一个字符是数字而不是.

    obj.value = obj.value.replace(/\.{2,}/g,"."); //只保留第一个. 清除多余的.

    obj.value = obj.value.replace(".","$#$").replace(/\./g,"").replace("$#$",".");//只允许输入一个小数点

    obj.value = obj.value.replace(/^(\-)*(\d+)\.(\d\d).*$/,'$1$2.$3'); //只能输入两个小数

}

//强制限制只能输入数字
function onlyNumber(obj){

    obj.value = obj.value.replace(/[^\d.]/g,""); //清除"数字"和"."以外的字符

    obj.value = obj.value.replace(/^\./g,""); //验证第一个字符是数字而不是.

}