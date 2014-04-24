'use strict';

var $ = require('jquery');

module.exports.greet = greet;

function greet() {
    $("#hello").html("Hello world!");
};