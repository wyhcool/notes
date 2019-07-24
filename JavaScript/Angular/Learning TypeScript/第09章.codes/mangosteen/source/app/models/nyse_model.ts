import { Model, ModelSettings } from "../../framework/model";
import { AppEvent } from "../../framework/framwork";

@ModelSettings("./data/nyse.json")
class NyseModel extends Model {
    constructor(mediator: IMediator) {
        super(mediator);
    }

    public initialize(): void {
        //监听 model 事件
        this.subscribeToEvents([
            new AppEvent("app.model.nyse.change", null, (e, args)=> {
                this.onChange(args);
            })
        ]);
    }

    public dispose(): void {
        this.unsubscribeToEvents();
    }

    //事件处理函数
    //使用 getAsync方法从 ModelSettings 装饰器定义的服务 URL 中获取数据
    private onChange(args: any): void {
        this.getAsync("json", args)
            .then( (data)=>{
                //格式化数据
                var stocks = { items: data, market: "NYSE" };

                //将控制权转给 market view
                this.triggerEvent(
                    new AppEvent("app.view.market.render", stocks, null)
                );
            })
            .catch((ex) => {
                //将程序控制权转给全局错误处理方法
                this.triggerEvent(
                    new AppEvent("app.error", ex, null)
                );
            });
    }
}

export { NyseModel };