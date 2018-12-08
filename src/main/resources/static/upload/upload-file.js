$(function () {
    $.fn.ajaxFileUpload = function(options){

        var defaults = {
            data: null,
            url: '/upload/file',
            //allowType: ["gif", "jpeg", "jpg", "bmp",'png'],
            maxNum: 1,
            maxSize: 1, //设置允许上传文件的最大尺寸，单位M
            success:$.noop, //上传成功时的回调函数
            error:$.noop //上传失败时的回调函数

        };

        var thisObj = $(this);
        var config  = $.extend(defaults, options);

        var uploadBox = $(".upload-file-box");
        var imageBox  = $(".upload-file-box");
        var uploadBtn = $(".upload-file-btn");
        var fileInputName = thisObj.attr('name');

        var inputName = uploadBox.attr('name');
        if (!inputName) {
            inputName = "file";
        }

        thisObj.change(function(){
            //alert("qqq");
            handleFileSelect();
        });

        var handleFileSelect = function(){

            if (typeof FileReader == "undefined") {
                return false;
            }

            // 获取最新的section数量
            var imageNum  = $('.file-section').length;

            var postUrl   = config.url;
            var maxNum    = config.maxNum;
            var maxSize   = config.maxSize;
            var allowType = config.allowType;
            if(!postUrl){
                console.log('请设置要上传的服务端地址');
                return false;
            }

            if(imageNum + 1 > maxNum ){
                console.log("上传文件数目不可以超过"+maxNum+"个");
                return;
            }

            var files    = thisObj[0].files;
            var fileObj  = files[0];
            if(!fileObj){
                return false;
            }

            var fileName = fileObj.name;
            var fileSize = (fileObj.size)/(1024*1024);
            if (!isAllowFile(fileName, allowType)) {
                alert("文件类型必须是" + allowType.join("，") + "中的一种");
                return false;
            }

            if(fileSize > maxSize){
                alert('上传文件不能超过' + maxSize + 'M，当前上传文件的大小为'+fileSize.toFixed(2) + 'M');
                return false;
            }

            console.log("上传前回调函数");
            // 执行前置函数
            var callback = config.before;

            if(callback && callback() === false){
                return false;
            }

            createImageSection();
            ajaxUpload();
        };

        var ajaxUpload = function () {
            // 获取最新的
            var imageSection = $('.file-section:last');
            var imageShow    = $('.image-show:last');

            var formData = new FormData();
            var fileData = thisObj[0].files;
            if(fileData){
                // 目前仅支持单图上传
                formData.append(fileInputName, fileData[0]);
            }

            var postData = config.data;
            if (postData) {
                for (var i in postData) {
                    formData.append(i, postData[i]);
                }
            }

            // ajax提交表单对象
            $.ajax({
                url: config.url,
                type: "post",
                data: formData,
                processData: false,
                contentType: false,
                //dataType: 'json',
                success:function(json){
                    if(!json.content){
                        imageSection.remove();
                        uploadBtn.show();
                        return false;
                    }

                    imageSection.removeClass("image-loading");
                    imageShow.removeClass("image-opcity");

                    imageShow.text(json.content.name);
                    //imageShow.siblings('input').val(json.content.url);
                    imageShow.siblings('input').first().val(json.content.url);
                    imageShow.siblings('input').last().val(json.content.name);

                    console.log(json);
                    // 执行成功回调函数
                    var callback = config.success;
                    callback(json);

                },
                error:function(e){
                    console.log(e);
                    imageSection.remove();
                    uploadBtn.show();
                    // 执行失败回调函数
                    var callback = config.error;
                    callback(e);

                }
            });

        };

        var createDeleteModal = function () {
            var deleteImageSection;
            var deleteModal   = $("<aside class='delete-modal'><div class='modal-content'><p-msg class='modal-tip'>您确定要删除吗？</p-msg><p-btn class='modal-btn'><span-btn class='confirm-btn'>确定</span-btn><span-btn class='cancel-btn'>取消</span-btn></p-btn></div></aside>");
            // 创建删除模态框
            deleteModal.appendTo(imageBox);

            // 显示弹框
            imageBox.delegate(".image-delete","click",function(){

                // 声明全局变量
                deleteImageSection = $(this).parent();
                deleteModal.show();

            });

            // 确认删除
            $(".confirm-btn").click(function(){
                deleteImageSection.remove();
                uploadBtn.show();
                deleteModal.hide();
            });

            // 取消删除
            $(".cancel-btn").click(function(){
                deleteModal.hide();
            });

            var inputSrc = imageBox.attr("href");
            if (inputSrc) {
                createImageSection();
                var imageSection = $('.file-section:last');
                var imageShow    = $('.image-show:last');
                imageSection.removeClass("image-loading");
                imageShow.removeClass("image-opcity");

                imageShow.text(inputSrc);
                imageShow.siblings('input').remove();
            }
        };

        var createImageSection = function () {
            var imageNum  = $('.file-section').length;
            if (imageNum + 1 >= config.maxNum) {
                uploadBtn.hide();
            }

            var imageSection = $("<section class='file-section image-loading'></section>");
            var imageShow    = $("<span class='image-show image-opcity'></span>");
            var imageInput   = $("<input name='" + inputName + "' value='' type='hidden'>");
            var imageInputTitle   = $("<input name='" + inputName + "Title' value='' type='hidden'>");
            var imageDelete  = $("<div class='image-delete'></div>");

            uploadBtn.before(imageSection);
            imageDelete.appendTo(imageSection);
            imageShow.appendTo(imageSection);
            imageInput.appendTo(imageSection);
            imageInputTitle.appendTo(imageSection);

            return imageSection;

        };

        //获取上传文件的后缀名
        var getFileExt = function(fileName){
            if (!fileName) {
                return '';
            }

            var _index = fileName.lastIndexOf('.');
            if (_index < 1) {
                return '';
            }

            return fileName.substr(_index+1);
        };

        //是否是允许上传文件格式
        var isAllowFile = function(fileName, allowType){
            var fileExt = getFileExt(fileName).toLowerCase();
            if (!allowType) {
                return true;
            }

            if ($.inArray(fileExt, allowType) != -1) {
                return true;
            }
            return false;
        };

        createDeleteModal();
    };

    $("#upload-input-file").ajaxFileUpload({
        url: '/marketing/upload/file', //上传的服务器地址
        maxNum: 1, //允许上传文件数量
        //allowType: ['p12'], //允许上传文件的类型
        maxSize: 1, //允许上传文件的最大尺寸，单位M
        error: function (e) {
            alert('上传失败回调函数');
        }
    });
});