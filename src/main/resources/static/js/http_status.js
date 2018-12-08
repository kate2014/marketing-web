$(function () {
}

function http_status(jsonResponse) {
    if (jsonResponse && jsonResponse.status != 1000) {
        return jsonResponse.message;
    }
    return "调用接口失败";
}