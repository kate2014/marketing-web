var window_ext_reg = /(^|.*[\\\/])window\.ext(-\w+)?\.js(?:\?.*|;.*)?$/i;
window.getBaseExtPath = function () {
    var b = "";
    for (var c = document.getElementsByTagName("script"), d = 0; d < c.length; d++) {
        var h = c[d].src.match(window_ext_reg);
        if (h) {
            b = h[1];
            break;
        }
    }

    return b;
}

//改写alert
window.alert = function (txt) {
    var shield = document.createElement("DIV");
    shield.id = "___alert_";
    shield.style.position = "absolute";
    shield.style.left = "0px";
    shield.style.top = "0px";
    shield.style.width = "100%";
    shield.style.height = document.body.scrollHeight + "px";
    shield.style.background = "rgba(0,0,0,.2)";
    //shield.style.opacity = "0.1";
    shield.style.textAlign = "center";
    shield.style.zIndex = "10000";
    shield.style.filter = "alpha(opacity=0)";
    var alertFram = document.createElement("DIV");
    alertFram.id = "alertFram";
    alertFram.style.position = "fixed";
    alertFram.style.left = "50%";
    alertFram.style.top = "50%";
    alertFram.style.marginLeft = "-150px";
    alertFram.style.marginTop = "-75px";
    alertFram.style.width = "300px";
    alertFram.style.height = "150px";
    alertFram.style.background = "#ccc";
    alertFram.style.textAlign = "center";
    alertFram.style.lineHeight = "150px";
    alertFram.style.zIndex = "10001";
    strHtml = "<ul style=\"list-style:none;margin:0px;padding:0px;width:100%\">\n";
    strHtml += " <li style=\"background:#000;text-align:center;padding-left:5px;font-size:16px;font-weight:bold;height:40px;line-height:40px;border:1px solid #000;color:#fff;\">操作提示</li>\n";
    strHtml += " <li style=\"background:#fff;text-align:center;font-size:16px;height:90px;line-height:90px;border-left:1px solid #000;border-right:1px solid #000;color:#333333;\">" + txt + "</li>\n";
    strHtml += " <li style=\"background:#e4e4e4;text-align:center;font-weight:bold;height:50px;line-height:50px; border:1px solid #000;border-top:0;\"><input type=\"button\" style=\"width:80px;height:36px;border-radius:4px;border:1px solid #FF6600;background:#FF6600;color:#fff;\" value=\"确 定\" onclick=\"doOk()\" /></li>\n";
    strHtml += "</ul>\n";
    alertFram.innerHTML = strHtml;
    shield.appendChild(alertFram);
    document.body.appendChild(shield);
    this.doOk = function () {
        //document.body.removeChild(shield);
        document.getElementById(shield.id).remove();
    }
    //alertFram.focus();
    document.body.onselectstart = function () {
        return false;
    };
}

window.showLoading = function (txt) {
    if (!txt) txt = "正在加载数据，请稍后...";
    var loading = document.createElement("DIV");
    loading.id = "__win_loading_";
    loading.style.cssText = "display:flex;top:0;left:0;right:0;align-items:center;justify-content:center;position:fixed;background:rgba(0,0,0,0);height:100%;width:100%;color: #707070;font-size:18px;margin-top:36px";
    var loading_html = "<div style=\"background:#fff;padding:30px;border-style:solid;border-width:8px;border-color:#f8f8f8\"><image style=\"display:block;margin:auto;margin-bottom:8px;\" src=\""+ getBaseExtPath() + "/loading.gif\"/>" + txt + "</div>\n";
    loading.innerHTML = loading_html;
    document.body.appendChild(loading);
}

window.hideLoading = function () {
    document.body.removeChild(document.getElementById("__win_loading_"));
}