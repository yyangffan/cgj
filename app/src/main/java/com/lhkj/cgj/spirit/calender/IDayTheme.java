package com.lhkj.cgj.spirit.calender;

/**
 * Created by Administrator on 2016/7/30.
 */
public interface IDayTheme {
    /**
     * 选中日期的背景色
     *
     * @return 16进制颜色值 hex color
     */
    public int colorSelectBG();

    /**
     * 选中日期的颜色
     *
     * @return 16进制颜色值 hex color
     */
    public int colorSelectDay();

    /**
     * 今天日期颜色
     *
     * @return 16进制颜色值 hex color
     */
    public int colorToday();

    /**
     * 日历的整个背景色
     *
     * @return
     */
    public int colorMonthView();

    /**
     * 工作日的颜色
     *
     * @return
     */
    public int colorWeekday();

    /**
     * 周末的颜色
     *
     * @return
     */
    public int colorWeekend();

    /**
     * 事务装饰颜色
     *
     * @return 16进制颜色值 hex color
     */
    public int colorDecor();

    /**
     * 假日颜色
     *
     * @return 16进制颜色值 hex color
     */
    public int colorRest();

    /**
     * 班颜色
     *
     * @return 16进制颜色值 hex color
     */
    public int colorWork();

    /**
     * 描述文字颜色
     *
     * @return 16进制颜色值 hex color
     */
    public int colorDesc();

    /**
     * 日期大小
     *
     * @return
     */
    public int sizeDay();

    /**
     * 描述文字大小
     *
     * @return
     */
    public int sizeDesc();

    /**
     * 装饰器大小
     *
     * @return
     */
    public int sizeDecor();

    /**
     * 日期高度
     *
     * @return
     */
    public int dateHeight();

    /**
     * 线条颜色
     *
     * @return
     */
    public int colorLine();

    /**
     * 滑动模式  0是渐变滑动方式，1是没有滑动方式
     *
     * @return
     */
    public int smoothMode();

    /**
     * 是否可以选中今天之前的日期
     */
    public boolean selectOut();
    /**
     * 是否绘制今天圆环
     */
    public boolean toDay();
    /**
     * 是否可以滑动
     */
    public boolean isSmooth();

    /**
     * 方块颜色
     */
    public int rectColor();
}
