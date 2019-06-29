
import 'reflect-metadata';


class Foo {

}

interface IFoo {

}

class Greeter {
    
    @logType
    public greeting: string;

    @logParamTypes
    public doSomething(
        param1: string,
        param2: number,
        param3: Foo,
        param4: { test: string },
        param5: IFoo,
        param6: Function,
        param7: (a: number) => void
    ): number {
        return 1;
    }

    @logReturntype
    public doSomethingMore():string {
        return 'Test';
    }
}


function logReturntype(target: any, key: string) {
    var returnType = Reflect.getMetadata('design:returntype', target, key);
    console.log(`${key} return type: ${returnType.name}`);
}

function logParamTypes(target: any, key: string) {
    var types = Reflect.getMetadata('design:paramtypes', target, key);
    var s = types.map( a => a.name ).join(',');
    console.log(`${key} param types: ${s}`);
}

function logType(target: any, key: string) {
    var t = Reflect.getMetadata('design:type', target, key);
    console.log(`${key} type: ${t.name}`);
}