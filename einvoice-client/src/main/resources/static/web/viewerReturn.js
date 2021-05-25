$(document).ready(function(){
console.log('开始执行')
    $("#scaleSelectContainer").hide();
$("#scaleSelectContainer").addClass('scaleSelectContainerClass');
    $("#openFile").addClass('scaleSelectContainerClass');
    $("#openFile").hide();
$("#documentPreview").html('预览文档');
    if (/MicroMessenger/i.test(navigator.userAgent)) {
        //ios的ua中无miniProgram，很坑爹,但都有MicroMessenger（表示是微信浏览器）
        wx.miniProgram.getEnv((res)=>{
            if (res.miniprogram) {
            console.log("小程序")
          
            $("#openFile").removeClass('scaleSelectContainerClass');
            $("#openFile").show();
            $("#openFile").on('click',function () {
                wx.miniProgram.redirectTo({url: '../webView/webView?weburl=1'});
            })
        } else {
            console.log("在微信，不在小程序");
        }
    })
    }else {

    }

});
