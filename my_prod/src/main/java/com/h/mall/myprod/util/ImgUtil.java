package com.h.mall.myprod.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.h.mall.myprod.model.Char;
import com.h.mall.myprod.model.Point;

public class ImgUtil {
    // 离散点
    public static final int MIN_POINT_VALUE = 6;

    /**
     * 获取字符
     *
     * @param values   图片像素区块，int[y][x],y表示图片横向像素个数，x表示图片纵向像素个数，数值0表示黑点，1表示白点
     * @param minWidth 单个字符最小宽度
     * @param maxWidth 单个字符最大宽度
     * @return
     */
    public static List<Char> getChars(int[][] values, int size ,int minWidth, int maxWidth) {
        List<Char> chars = new ArrayList<Char>();
        // step1: 扫描字符区块
        List<Char> charAreas = getCharAreas(values);
        // step2: 检查每个字符区块，如果宽度小于最小宽度，丢弃；如果大于最大宽度，使用滴水分割法进行分割

        if(charAreas.size() == size){
            return charAreas;
        }
        for (Char c : charAreas) {
            if (c.getWidth() < minWidth) {
                // 小于最小宽度，丢弃
            } else if (c.getWidth() > maxWidth) {
                // 大于最大宽度，使用滴水分割法进行分割
                splitChars(chars, c, minWidth, maxWidth);
            } else {
                // 一个字符
                chars.add(c);
            }
        }
        // step3: 好像没有3了。。。
        return chars;
    }

    /**
     * 从图片像素阵列中获取字符区块
     *
     * @param values
     * @return
     */
    private static List<Char> getCharAreas(int[][] values) {
        List<Char> chars = new ArrayList<Char>();
        int totalX = values[0].length;
        int totalY = values.length;
        // 标志，记录是否已扫描
        boolean[][] flags = new boolean[totalY][totalX];
        // 从左往右依次扫描
        for (int y = 0; y < totalY; y++) {
            for (int x = 0; x < totalX; x++) {
                // 如果该点已经被扫描过，跳过
                if (flags[y][x]) continue;

                // 如果是黑点，
                if (values[y][x] == 0) {
                    Point p = new Point(x, y);
                    // 统计周围8个像素
                    int value = calcArount(values, p);
                    if (value > MIN_POINT_VALUE) {
                        // 这是个离散点
                        flags[y][x] = true;
                    } else {
                        // 这个点是一个新的字符区块的起点，从该点发散去获取粘连的字符区块
                        List<Point> blackPoints = new ArrayList<Point>();
                        scanChar(blackPoints, p, y, values, flags);
                        Char c = new Char(blackPoints);
                        chars.add(c);
                    }
                } else {
                    flags[y][x] = true;
                }
            }
        }
        return chars;
    }


//    private void print(List<Point> blackPoints) {
//        for (int i = 0; i < 1000; i++) {
//            for (int j = 0; j < 1000; j++) {
//                System.out.println();
//            }
//        }
//    }

    /**
     * 统计周围8个点的
     *
     * @param values
     * @param p
     * @return
     */
    private static int calcArount(int[][] values, Point p) {
        int value = 0;
        int x = p.getX();
        int y = p.getY();
        int totalX = values[0].length;
        int totalY = values.length;
        // 左上
        if (x > 0 && y > 0) {
            value += values[y - 1][x - 1];
        }
        // 上
        if (x > 0) {
            value += values[y][x - 1];
        }
        // 右上
        if (x > 0 && y < totalY - 1) {
            value += values[y + 1][x - 1];
        }
        // 左
        if (y > 0) {
            value += values[y - 1][x];
        }
        // 右
        if (y < totalY - 1) {
            value += values[y + 1][x];
        }
        // 左下
        if (x < totalX - 1 && y > 0) {
            value += values[y - 1][x + 1];
        }
        // 下
        if (x < totalX - 1) {
            value += values[y][x + 1];
        }
        // 右下
        if (x < totalX - 1 && y < totalY - 1) {
            value += values[y + 1][x + 1];
        }
        return value;
    }

    /**
     * @param blackPoints
     * @param startPoint
     * @param startY
     * @param values
     * @param flags
     */
    private static void scanChar(List<Point> blackPoints, Point startPoint, int startY,
                                 int[][] values, boolean[][] flags) {
        int x = startPoint.getX();
        int y = startPoint.getY();
        if (flags[y][x]) return;
        flags[y][x] = true;

        blackPoints.add(new Point(x, y - startY));
        // 从该点开始，向上、右上、右、右下、下进行扫描连续的黑点
        for (int ix = x - 1; ix <= x + 1; ix++) {
            for (int iy = y - 1; iy <= y + 1; iy++) {
                // 越界
                if (ix < 0 || ix >= values[0].length || iy < 0 || iy >= values.length) continue;
                // 原点
                if (ix == x && iy == y) continue;
                // 已扫描过
                if (flags[iy][ix]) continue;

                // 黑点
                if (values[iy][ix] == 0) {
                    Point p = new Point(ix, iy);
                    // 统计周围8个像素
                    int value = calcArount(values, p);
                    if (value > MIN_POINT_VALUE) {
                        // 这是个离散点
                        flags[iy][ix] = true;
                    } else {
                        scanChar(blackPoints, p, startY, values, flags);
                    }
                } else {
                    flags[iy][ix] = true;
                }
            }
        }
    }

    /**
     * 使用分割线分割字符
     *
     * @param chars
     * @param c
     * @param minWidth
     * @param maxWidth
     * @return
     */
    private static List<Char> splitChars(List<Char> chars, Char c, int minWidth, int maxWidth) {
        System.out.println("开始分割，计算分割线。");
        Map<Integer, Integer> line = calcLine(c, minWidth, maxWidth);
        System.out.println("计算分割线完成。");
        // 分割线的最小y值
        int minY = maxWidth;
        Collection<Integer> ys = line.values();
        for (int y : ys) {
            minY = Math.min(minY, y);
        }
        System.out.println("统计分割线的最小y值完成。");
        List<Point> ps1 = new ArrayList<Point>();
        List<Point> ps2 = new ArrayList<Point>();
        List<Point> blackPoints = c.getBlackPoints();
        for (Point p : blackPoints) {
            if (p.getY() <= line.get(p.getX())) {
                ps1.add(p);
            } else {
                // 重置y偏移
                ps2.add(new Point(p.getX(), p.getY() - minY - 1));
            }
        }
        Char c1 = new Char(ps1);
        chars.add(c1);
        Char c2 = new Char(ps2);
        System.out.println("分割完成。");

        if (c2.getWidth() == c.getWidth()) {
            // 没有分割出来，放弃了
            System.out.println("c2分割失败。");
            chars.add(c2);
        } else if (c2.getWidth() > maxWidth) {
            System.out.println("c2需要继续分割。");
            splitChars(chars, c2, minWidth, maxWidth);
        } else {
            System.out.println("c2成功被分割。");
            chars.add(c2);
        }
        return chars;
    }

    /**
     * 计算分割线
     *
     * @param c
     * @param minWidth
     * @param maxWidth
     * @return
     */
    private static Map<Integer, Integer> calcLine(Char c, int minWidth, int maxWidth) {
        // 确定分割线起始点
        System.out.println("确定分割线起始点");
        int y = getLineY(c, minWidth, maxWidth);
        if (y == 0) y++;
        Point nextPoint = new Point(0, y);
        // 计算分割线
        System.out.println("计算分割线, y = " + y);
        Map<Integer, Integer> line = new HashMap<Integer, Integer>();
        line.put(0, y);
        int x = 0;
        int times = 0;
        while (x < c.getHeight() - 1) {
            // 列出周围5个点
            System.out.println("列出周围5个点, c.getHeight() = " + c.getHeight() + ", x = " + x);
            int[] pVvalues = getUnderPointsValue(c, nextPoint);
            nextPoint = getNextPoint(nextPoint, pVvalues);
            if (times > x * 2 && line.get(nextPoint.getX()) != null
                    && line.get(nextPoint.getX()).intValue() == nextPoint.getY() - 1) {
                nextPoint = new Point(nextPoint.getX() + 1, nextPoint.getY() + 1);
            }
            line.put(nextPoint.getX(), nextPoint.getY());
            x = nextPoint.getX();
            times++;
        }
        return line;
    }

    /**
     * 统计竖直投影直方图，判断切分点
     *
     * @param c
     * @param minWidth
     * @param maxWidth
     * @return
     */
    private static int getLineY(Char c, int minWidth, int maxWidth) {
        boolean[][] values = c.getValues();
        int end = Math.min(maxWidth + 2, c.getWidth());
        int max = 0;
        int rightY = minWidth;
        for (int y = minWidth; y < end; y++) {
            int total = 0;
            for (int x = 0; x < c.getHeight(); x++) {
                total += (values[y][x] ? 0 : 1);
            }
            if (total > max) {
                max = total;
                rightY = y;
            }
        }
        return rightY;
    }

    /**
     * 获取水滴周围5个点的像素值
     *
     * @param c
     * @param p
     * @return
     */
    private static int[] getUnderPointsValue(Char c, Point p) {
        boolean[][] values = c.getValues();
        int x = p.getX(), y = p.getY();
        int[] pVvalues = new int[5];
        // 1,左下
        if (y == 0) {
            pVvalues[0] = 0;
        } else {
            pVvalues[0] = values[(y - 1)][(x + 1)] ? 0 : 1;
        }
        // 2,下
        pVvalues[1] = values[y][(x + 1)] ? 0 : 1;
        // 3,右下
        if (y == c.getWidth() - 1) {
            pVvalues[2] = 0;
        } else {
            pVvalues[2] = values[(y + 1)][(x + 1)] ? 0 : 1;
        }
        // 4,右
        if (y == c.getWidth() - 1) {
            pVvalues[3] = 0;
        } else {
            pVvalues[3] = values[(y + 1)][x] ? 0 : 1;
        }
        // 5,左
        if (y == 0) {
            pVvalues[4] = 0;
        } else {
            pVvalues[4] = values[(y - 1)][x] ? 0 : 1;
        }

        return pVvalues;
    }

    /**
     * 获取滴水算法分割线的下一个点
     *
     * @param currPoint 分割线的当前点
     * @param values    当前点周围像素，长度=5，顺序为左下、下、右下、右、左
     * @return
     */
    public static Point getNextPoint(Point currPoint, int[] values) {
        // 计算重力势能的衡量
        int w = 0;
        int sum = 0;
        int max = 0;
        for (int index = 0; index < values.length; index++) {
            int temp = values[index] * (5 - index);
            sum += temp;
            max = Math.max(max, temp);
        }
        if (sum == 0 || sum == 15) {
            w = 4;
        } else {
            w = max;
        }
        System.out.println("w=" + w);
        // 计算下个点的坐标
        int x = 0;
        int y = 0;
        switch (w) {
            case 1:
                // 左
                x = currPoint.getX();
                y = currPoint.getY() - 1;
                break;
            case 2:
                // 右
                x = currPoint.getX();
                y = currPoint.getY() + 1;
                break;
            case 3:
                // 右下
                x = currPoint.getX() + 1;
                y = currPoint.getY() + 1;
                break;
            case 4:
                // 下
                x = currPoint.getX() + 1;
                y = currPoint.getY();
                break;
            case 5:
                // 左下
                x = currPoint.getX() + 1;
                y = currPoint.getY() - 1;
                break;
        }

        return new Point(x, y);
    }

}
