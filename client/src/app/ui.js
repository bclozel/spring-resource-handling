'use strict';

define(["jquery", "app/model"], function($, ui) {

    return {
        update: function() {
            $("#greeting").html(ui.getMessage());
        }
    }
});