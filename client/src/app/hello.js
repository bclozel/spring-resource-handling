'use strict';

define(["jquery"], function($) {

    return {
        greet: function() {
            $("#hello").html("Hello world!");
        }
    }
});