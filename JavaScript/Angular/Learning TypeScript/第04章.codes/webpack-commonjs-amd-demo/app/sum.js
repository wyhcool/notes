define([
    './reduce',
    './add'
], function(reduce, add) {
    'use strict';
    
    var sum = function(arr) {
        return reduce(arr, add);
    }

    return sum;
});