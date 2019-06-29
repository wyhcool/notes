export function LogMethod (target: any, key: string, descriptor: any) {

    //保存原方法的引用
    var originalMethod = descriptor.value;

    //编辑 descriptor 参数的 value 属性
    descriptor.value = function(...args: any[]) {

        console.log(this) // 运行时确定因此这里是的 this 指向实例的。如果这里是箭头函数，this则指向undefined
        
        //将方法参数转化为字符串
        var  a = args.map( a => JSON.stringify(a) ).join();

        //执行方法，得到其返回值
        var result = originalMethod.apply(this, args);

        //将返回值转化为字符串
        var r = JSON.stringify(result);

        //将函数的调用细节打印在控制器中
        console.log(`Call: ${key}(${a}) => ${r}`);

        //返回方法的调用结果
        return result;
    }

    //返回编辑后的属性描述对象
    return descriptor;
}