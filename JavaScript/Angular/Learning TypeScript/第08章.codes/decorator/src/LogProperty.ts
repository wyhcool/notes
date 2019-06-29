export function LogProperty(target: any, key: string) {

    //属性值
    var _val = this[key];

    //属性的 getter
    var getter = function() {
        console.log(`Get: ${key} => ${_val}`);
        return _val;
    }

    //属性的 setter
    var setter = function(newVal) {
        console.log(`Set: ${key} => ${newVal}`);
        _val = newVal;
    }

    //删除属性
    //如果对象是不可配置的，在严格模式下会抛出一个错误；
    //在非严格模式下则会返回 false
    if (delete this[key]) {
        Object.defineProperty(target, key, {
            get: getter,
            set: setter,
            enumerable: true,
            configurable: true
        });
    }
}