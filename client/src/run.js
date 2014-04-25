'use strict';

var baseUrl;
(function () {
    curl.config({
        baseUrl: baseUrl || '',
        paths: {
            jquery: 'lib/jquery/jquery.min'
        }
    });

    curl(['jquery', 'app/ui']).then(start, fail);

    function start($, ui) {
        $(function(){
            ui.update();
        });
    }

    function fail(ex) {
        throw ex;
    }

}());