export function LogClass (target: any) {

    //保存原构造函数的引用
    var original = target;

    //用来生成类的实例的工具方法
    function construct(constructor, args) {
        var c: any = function() {
            return constructor.apply(this, args);
        }
        c.prototype = constructor.prototype;
        return new c();
    }

    //新的构造函数行为
    var f: any = function(...args) {
        console.log("New: " + original.name);
        return construct(original, args);
    }

    //复制原型，使 instanceof 操作能正常使用
    f.prototype = original.prototype;

    //返回新的构造函数(将会覆盖原构造函数)
    return f;
}