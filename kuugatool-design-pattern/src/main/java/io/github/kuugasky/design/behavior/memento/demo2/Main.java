package io.github.kuugasky.design.behavior.memento.demo2;

/**
 * Main
 *
 * @author kuuga
 * @since 2023/6/27-06-27 15:29
 */
public class Main {

    private static int index = -1;

    /**
     * 备忘录管理者
     */
    private static final MementoCaretaker MEMENTO_CARETAKER = new MementoCaretaker();

    public static void main(String[] args) {
        Chessman chess = new Chessman("车", 1, 1);
        // play前将当前棋子坐标进行暂存
        play(chess);
        chess.setY(4);
        play(chess);
        chess.setX(5);
        play(chess);

        undo(chess, index);
        undo(chess, index);
        redo(chess, index);
        redo(chess, index);
    }

    /**
     * 下棋，同时保存备忘录
     *
     * @param chess 象棋棋子
     */
    public static void play(Chessman chess) {
        // 创建一个备忘录对象
        ChessmanMemento chessmanMemento = chess.save();
        // 将备忘录对象保存到备忘录管理者中
        MEMENTO_CARETAKER.addMemento(chessmanMemento);
        // 下标+1
        index++;
        // 打印当前棋子位置
        chess.show();
    }

    /**
     * 悔棋，撤销到上一个备忘录
     *
     * @param chess 象棋棋子
     * @param i     i
     */
    public static void undo(Chessman chess, int i) {
        System.out.println("******悔棋******");
        index--;
        chess.restore(MEMENTO_CARETAKER.getMemento(i - 1));
        chess.show();
    }

    /**
     * 撤销悔棋，恢复到下一个备忘录
     *
     * @param chess 象棋棋子
     * @param i     i
     */
    public static void redo(Chessman chess, int i) {
        System.out.println("******撤销悔棋******");
        index++;
        chess.restore(MEMENTO_CARETAKER.getMemento(i + 1));
        chess.show();
    }

}
