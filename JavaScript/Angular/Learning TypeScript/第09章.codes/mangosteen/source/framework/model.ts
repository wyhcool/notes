/// <reference path="./interfaces.ts"/>

import { EventEmittor } from "./event_emitter";
import * as Q from "q"; 
import * as $ from "jquery";

//ref: https://github.com/microsoft/TypeScript/issues/10178

//报错: Cannot find module 'q'
//解决: 
/*
# Implementation package (required to run)
$ npm install --save lodash

# Typescript Description
$ npm install --save @types/lodash 
*/
//ref: https://stackoverflow.com/questions/34660265/importing-lodash-into-angular2-typescript-application

function ModelSettings(serviceUrl: string) {
    return function(target: any) {
        //保存原构造函数的引用
        var original: any = target;

        //用于生成类实例的工具函数
        function construct(constructor: any, args: any): any {
            var c: any = function() {
                return constructor.apply(this, args);
            }

            c.prototype = constructor.prototype;
            var instance = new c();
            instance._serviceUrl = serviceUrl;
            return instance;
        }

        //新构造函数的行为
        var f: any = function(...args: any[]) {
            return construct(original, args);
        }

        //为了使 instanceOf 操作符继续可用
        f.prototype = original.prototype;

        //返回新的构造函数(将覆盖原来的)
        return f;
    }
}

class Model extends EventEmittor implements IModel {

    // _serviceUrl 的值必须使用 ModelSettings 装饰器来设置
    private _serviceUrl: string;

    constructor(mediator: IMediator) {
        super(mediator);
    }

    //必须由派生类来实现
    public initialize(): void {
        throw new Error("Model.prototype.initialize() is abstract you must implement it!");
    }

    //必须由派生类来实现
    public dispose(): void {
        throw new Error("Model.prototype.dispose() is abstract you must implement it!");
    }

    protected requestAsync(method: string, dataType: string, data: any) {
        //NOTICE: npm install --save @types/q
        return Q.Promise((resolve: (r: any) => any, reject: (e: any) => any) => {
            $.ajax({
                method: method,
                url: this._serviceUrl,
                data: data || {},
                dataType: dataType,
                success: (response) => {
                    resolve(response);
                },
                error: (...args: any[]) => {
                    reject(args);
                }
            });
        });
    }

    protected getAsync(dataType: string, data: any) {
        return this.requestAsync("GET", dataType, data);
    }

    protected postAsync(dataType: string, data: any) {
        return this.requestAsync("POST", dataType, data);
    }

    protected putAsync(dataType: string, data: any) {
        return this.requestAsync("PUT", dataType, data);
    }

    protected deleteAsync(dataType: string, data: any) {
        return this.requestAsync("DELETE", dataType, data);
    }
}

export { Model, ModelSettings };