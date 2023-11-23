package io.github.kuugasky.design.behavior.chain.demo3;

/**
 * Main
 *
 * @author kuuga
 * @since 2023/6/8-06-08 09:46
 */
public class Main {

    public static void main(String[] args) {
        // 總統套房
        Handler handler1 = new PresidentialSuiteHandler("第一級別");
        // 豪華套房
        Handler handler2 = new LuxurySuiteHandler("第二級別");
        // 旅館
        Handler handler3 = new CertainHotelHandler("第三級別");
        // 招待所
        Handler handler4 = new GuestHouseHandler("第四級別");

        handler4.handelRequest("第一級別", "總統拜登來訪問");
        handler4.handelRequest("第二級別", "國務卿布林肯來訪問");

        System.out.println("直接调用不匹配，无输出");

        // 责任链模式实现接待
        Handler link = Handler.link(handler4, handler3, handler2, handler1);
        link.handelRequest("第一級別", "總統拜登來訪問");
        link.checkNext("第二級別", "國務卿布林肯來訪問");
    }

}
