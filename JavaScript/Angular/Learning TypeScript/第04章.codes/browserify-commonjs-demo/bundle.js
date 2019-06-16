(function(){function r(e,n,t){function o(i,f){if(!n[i]){if(!e[i]){var c="function"==typeof require&&require;if(!f&&c)return c(i,!0);if(u)return u(i,!0);var a=new Error("Cannot find module '"+i+"'");throw a.code="MODULE_NOT_FOUND",a}var p=n[i]={exports:{}};e[i][0].call(p.exports,function(r){var n=e[i][1][r];return o(n||r)},p,p.exports,r,e,n,t)}return n[i].exports}for(var u="function"==typeof require&&require,i=0;i<t.length;i++)o(t[i]);return o}return r})()({1:[function(require,module,exports){
//add.js
module.exports = function add(a,b){
    return a + b;
};
},{}],2:[function(require,module,exports){
//main.js
var sum = require('./sum');

var values = [ 1, 2, 4, 5, 6, 7, 8, 9 ];
var answer = sum(values)
 
document.getElementById("answer").innerHTML = answer;
},{"./sum":4}],3:[function(require,module,exports){
//reduce.js
module.exports = function reduce(arr, iteratee) {
  var index = 0,
    length = arr.length,
    memo = arr[index];
 
  index += 1;
  for(; index < length; index += 1){
    memo = iteratee(memo, arr[index])
  }
  return memo;
};
},{}],4:[function(require,module,exports){
//sum.js
var reduce = require('./reduce');
var add = require('./add');
 
module.exports = function(arr){
  return reduce(arr, add);
};
},{"./add":1,"./reduce":3}]},{},[2]);
