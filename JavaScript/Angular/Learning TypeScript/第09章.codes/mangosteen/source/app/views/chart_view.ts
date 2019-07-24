import { View, ViewSettings } from "../../framework/view";
import { AppEvent, Route } from "../../framework/framwork";

@ViewSettings("./source/app/templates/market.hbs", "#outlet")
class ChartView extends View {

    constructor(mediator: IMediator) {
        super(mediator);
    }

    public initialize(): void {
        this.subscribeToEvents([
            new AppEvent("app.view.market.render", null, (e, args: any) => {
                this.renderAsync(args)
                    .then((model) => {
                        //设置 DOM 事件
                        this.bindDomEvents(model);
                    })
                    .catch((ex) => {
                        this.triggerEvent(
                            new AppEvent("app.error", ex, null)
                        );
                    });
            })
        ]);
    }

    public dispose(): void {
        this.unbindDomEvents();
        this.unsubscribeToEvents();
    }

    //初始化 dom 事件
    protected bindDomEvents(model: any) {
        var scope = $(this._container);
        //处理 quote 上的单击事件
        $(".getQuote").on("click", scope, (e) => {
            var symbol = $(e.currentTarget).data("sysmbol");
            this.getStockQuote(symbol);
        });
        //让表格变得可排序和可搜索
        $(scope).find("table").DataTable();
    }

    protected unbindDomEvents() {
        var scope = $(this._container);
        $(".getQuote").off("click", scope);
        var table = $(scope).find("table").DataTable();
        table.destroy();
    }

    //观察 quote 按钮的点击事件
    private getStockQuote(symbol: string) {
        //使用 route 事件跳转路由
        this.triggerEvent(
            new AppEvent("app.route", new Route("symbol", "quote", [symbol]), null)
        );
    }
}

export { ChartView };