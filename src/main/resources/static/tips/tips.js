function successTips(title, message) {
    $.toast().reset('all');
    $.toast({
        heading: title,
        text: message,
        showHideTransition: 'slide',
        position: 'top-right',
        icon: 'success'
    })
}

function warningTips(title, message) {
    $.toast().reset('all');
    $.toast({
        heading: title,
        text: message,
        position: 'top-right',
        icon: 'warning'
    })
}

function errorTips(title, message) {
    $.toast().reset('all');
    $.toast({
        heading: title,
        text: message,
        position: 'top-right',
        icon: 'error'
    })
}