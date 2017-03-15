var ShipService = ng.core.Class({
    constructor: [function() {
        this.initGame = (function(){

            var xhr = new XMLHttpRequest();
            xhr.open('GET', 'http://localhost:8080/startSession', false);
            xhr.send();
            if (xhr.status != 200) {
                console.log( xhr.status + ': ' + xhr.statusText ); // пример вывода: 404: Not Found
            } else {
                console.log( xhr.responseText ); // responseText -- текст ответа.
                return JSON.parse(xhr.response.toString());
            }
            return null;
        });

        this.getClickCheck = (function(sessionId, x, y){

            var xhr = new XMLHttpRequest();
            xhr.open('GET', 'http://localhost:8080/startSession?sessionId=' + sessionId + '&x=' + x + '&y=' + y, false);
            xhr.send();
            if (xhr.status != 200) {
                console.log( xhr.status + ': ' + xhr.statusText ); // пример вывода: 404: Not Found
            } else {
                console.log( xhr.responseText ); // responseText -- текст ответа.
                return JSON.parse(xhr.response.toString());
            }
            return null;
        });
    }]
})
