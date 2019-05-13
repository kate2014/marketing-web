//改写alert
window.alert = function (txt) {
    var shield = document.createElement("DIV");
    shield.id = "___alert";
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
    strHtml += " <li style=\"background:#DD828D;text-align:left;padding-left:5px;font-size:14px;font-weight:bold;height:30px;line-height:30px;border:1px solid #F9CADE;\">[系统提示]</li>\n";
    strHtml += " <li style=\"background:#fff;text-align:center;font-size:12px;height:80px;line-height:80px;border-left:1px solid #F9CADE;border-right:1px solid #F9CADE;\">" + txt + "</li>\n";
    strHtml += " <li style=\"background:#FDEEF4;text-align:center;font-weight:bold;height:40px;line-height:40px; border:1px solid #F9CADE;\"><input type=\"button\" style=\"width:70px;height:30px;border-radius:4px;border:1px solid #bbb;background:#fff;\" value=\"确 定\" onclick=\"doOk()\" /></li>\n";
    strHtml += "</ul>\n";
    alertFram.innerHTML = strHtml;
    shield.appendChild(alertFram);
    document.body.appendChild(shield);
    this.doOk = function () {
        document.body.removeChild(shield);
    }
    //alertFram.focus();
    document.body.onselectstart = function(){return false;};
}