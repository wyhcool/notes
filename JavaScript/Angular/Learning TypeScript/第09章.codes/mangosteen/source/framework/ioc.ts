import "reflect-metadata";

//ioc 容器
const classPool: Array<Function> = [];

//注册该类进入容器
export function Injectable() {
    return (_constructor: Function) => {
        const paramTypes: Array<Function> = Reflect.getMetadata("design:paramtypes", _constructor);

        //未注册
        if (classPool.indexOf(_constructor) !== -1) {
            return;
        }

        //判断依赖是否已注册
        for (const val of paramTypes) {
            if (val === _constructor) {
                throw new Error("Can't depend on class self");
            } else if (classPool.indexOf(val) === -1) {
                throw new Error(`${val} is not registed`);
            }
        }

        //注册
        classPool.push(_constructor);
    };
}

//实例化工厂
export function classFactory<T>(_constructor: { new (...args: Array<any>): T}): T {
    const paramTypes: Array<Function> = Reflect.getMetadata("design:paramtypes", _constructor);

    //参数实例化
    const paramInstances = paramTypes.map((val: Function) => {

        //依赖的类必须全部进行注册
        if (classPool.indexOf(val) === -1) {
            throw new Error(`${val} is not registered`);

        //参数还有依赖
        } else if (val.length) {
            return classFactory(val as any);
        } else {
            return new (val as any)();
        }

    });

    console.log(paramInstances);

    //区别 _constructor(paramInstances) Vs. _constructor(...paramInstances)
    //使用剩余参数允许开发者传递任意数量的参数，剩余参数语法允许把不限量的参数表示为一个数组，在参数前面有一个三个点的省略号，且必须包含一个数组类型
    return new _constructor(paramInstances);
}