/// <reference path="./interfaces.ts"/>

import { EventEmittor } from "./event_emitter";
import * as Q from "q";
import * as Handlebars from "handlebars";
import * as $ from "jquery";

//ref: https://stackoverflow.com/questions/55962145/how-to-use-local-npm-package-ts-typings-for-ambient-module-handlebars
//Now we can /// node.d.ts and then load the modules using import url = require("url"); or import * as URL from "url".
function ViewSettings(templateUrl: string, container: string) {
    return function(target: any) {
        //保存原构造函数的引用
        var original: any = target;

        //用于生成类实例的工具函数
        function construct(constructor: any, args: any[]): any {
            var c: any = function() {
                return constructor.apply(this, args);
            }

            c.prototype = constructor.prototype;
            var instance = new c();
            instance._container = container;
            instance._templateUrl = templateUrl;
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

class View extends EventEmittor implements IView {
    // _container 和 _templateUrl 的值必须使用 ViewSettings 装饰器来设置
    protected _container: string;
    private _templateUrl: string;

    private _templateDelegate: HandlebarsTemplateDelegate;

    constructor(mediator: IMediator) {
        super(mediator);
    }
 
    //必须由派生类来实现
    public initialize(): void {
        throw new Error("View.prototype.initialize() is abstract you must implement it!");
    }

    //必须由派生类来实现
    public dispose(): void {
        throw new Error("View.prototype.dispose() is abstract you must implement it!");
    }

    //必须由派生类来实现
    protected bindDomEvents(model: any) {
        throw new Error("View.prototype.bindDomEvents() is abstract you must implement it!");
    }

    //必须由派生类来实现
    protected unbindDomEvents(model: any) {
        throw new Error("View.prototype.unbindDomEvents() is abstract you must implement it!");
    }

    //异步模板加载
    private loadTemplateAsync() {
        return Q.Promise((resolve: (r: any) => any, reject: (e: any) => any) => {
            $.ajax({
                method: "GET",
                url: this._templateUrl,
                dataType: "text",
                success: (response) => {
                    resolve(response);
                },
                error: (...args: any[]) => {
                    reject(args);
                }
            });
        });
    }

    //异步编译模板
    private compileTemplateAsync(source: string) {
        return Q.Promise((resolve: (r: any) => any, reject: (e: any) => any) => {
            try {
                var template = Handlebars.compile(source);
                resolve(template);
            } catch (ex) {
                reject(ex);
            }
        });
    }
    
    //缓存
    private getTemplateAsync() {
        return Q.Promise((resolve: (r: any) => any, reject: (e: any) => any) => {
            if (this._templateDelegate === undefined 
                || this._templateDelegate === null) {
                
                this.loadTemplateAsync()
                    .then((source) => {
                        return this.compileTemplateAsync(source);
                    })
                    .then((templateDelegate) => {
                        this._templateDelegate = templateDelegate;
                        resolve(this._templateDelegate);
                    })
                    .catch((ex) => {
                        reject(ex);
                    })
            } else {
                resolve(this._templateDelegate);
            }
        });
    }

    //异步渲染 view
    protected renderAsync(model: any) {
        return Q.Promise((resolve: (r: any) => any, reject: (e: any) => any) => {
            this.getTemplateAsync()
                .then((templateDelegate) => {
                    //生成 HTML 并添加到 DOM 中
                    var html = templateDelegate(model);
                    $(this._container).html(html);

                    //将 model 作为参数传递
                    //让子视图和 DOM 事件初始化
                    resolve(model);
                })
                .catch((ex) => {
                    reject(ex);
                })
        });
    }
}

export { View, ViewSettings };