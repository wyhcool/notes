import { LogClass }  from './LogClass';
import { LogMethod } from './LogMethod';
import { LogProperty } from './LogProperty';

//参数装饰器
function addMetaData(target: any, key: string, index: number) {
    var metadataKey = `_log_${key}_parameters`;
    if (Array.isArray(target[metadataKey])) {
        target[metadataKey].push(index);
    } else {
        target[metadataKey] = [index];
    }
}


//方法装饰器
function readMetaData(target: any, key: string, descriptor: any) {
    var originalMethod = descriptor.value;
    descriptor.value = function(...args: any[]) {

        var metadataKey = `_log_${key}_parameters`;
        var indices = target[metadataKey];

        if (Array.isArray(indices)) {

            for (var i = 0; i < args.length; i++) {
                
                if (indices.indexOf(i) !== -1) {
                    
                    var arg = args[i];
                    var argStr = JSON.stringify(arg) || arg.toString();
                    console.log(`${key} arg[${i}]: ${argStr}`);
                    
                }

            }

        }

        var result = originalMethod.apply(this, args);
        return result;
    }
    return descriptor;
}

@LogClass
class Person {

    @LogProperty
    public name: string;

    public surname: string;

    constructor(name: string, surname: string) {
        this.name = name;
        this.surname = surname;
    }

    @LogMethod
    @readMetaData
    public saySomething(@addMetaData somthing: string): string {
        return this.name + " " + this.surname + " says: " + somthing;
    }
}





var me = new Person('Remo', 'Jansen');
me.saySomething('OK');