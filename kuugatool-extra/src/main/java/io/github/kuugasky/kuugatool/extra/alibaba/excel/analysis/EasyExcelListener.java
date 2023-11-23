package io.github.kuugasky.kuugatool.extra.alibaba.excel.analysis;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.metadata.CellData;
import io.github.kuugasky.kuugatool.core.collection.ListUtil;
import io.github.kuugasky.kuugatool.core.instance.ReflectionUtil;
import io.github.kuugasky.kuugatool.core.object.ObjectUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

/**
 * CustomizationListener
 *
 * @author kuuga
 * @since 2023/4/3-04-03 19:28
 */
@Slf4j
public class EasyExcelListener<T extends EasyExcelAnalysisDto> extends AnalysisEventListener<T> {

    /**
     * 创建EasyExcelListener
     *
     * @param numberOfEachExecuted 每次从解析内容中读取出的行数
     */
    public EasyExcelListener(long numberOfEachExecuted) {
        this.numberOfEachExecuted = numberOfEachExecuted;
        this.maximumImportQuantity = Integer.MAX_VALUE;
        this.end = ObjectUtil.cast(ReflectionUtil.newInstance(EasyExcelAnalysisDto.class));
    }

    /**
     * 创建EasyExcelListener
     *
     * @param maximumImportQuantity 文件支持导入最大行数
     * @param numberOfEachExecuted  每次从解析内容中读取出的行数
     */
    public EasyExcelListener(long maximumImportQuantity, long numberOfEachExecuted) {
        this.maximumImportQuantity = maximumImportQuantity;
        this.numberOfEachExecuted = numberOfEachExecuted;
        this.end = ObjectUtil.cast(ReflectionUtil.newInstance(EasyExcelAnalysisDto.class));
    }

    /**
     * 文件支持导入最大行数
     */
    private final long maximumImportQuantity;
    /**
     * 每次从解析内容中读取出的行数
     */
    private final long numberOfEachExecuted;
    /**
     * 链式阻塞队列，队列容量{@link Integer#MAX_VALUE}
     */
    private final BlockingQueue<T> queue = new LinkedBlockingQueue<>();
    /**
     * 行数累计
     */
    private final AtomicInteger count = new AtomicInteger(0);
    /**
     * 结束节点
     */
    private final T end;

    /**
     * 获取文件总行数，不包含列头行
     *
     * @return 文件总行数
     */
    public int getTotalCount() {
        return count.get();
    }

    /**
     * 解析head头
     *
     * @param headMap 列头map
     * @param context analysis context
     */
    @Override
    public void invokeHead(Map<Integer, CellData> headMap, AnalysisContext context) {
        super.invokeHead(headMap, context);
    }

    /**
     * 解析数据行
     * <p>
     * 每一行解析都会触发invoke 在这里进行数据处理
     *
     * @param data    one row value. It is same as {@link AnalysisContext#readRowHolder()}
     * @param context analysis context
     */
    @Override
    public void invoke(T data, AnalysisContext context) {
        int incrementAndGet = count.incrementAndGet();
        // 下标从1开始
        if (incrementAndGet > maximumImportQuantity) {
            throw new RuntimeException("导入数量超过最大限制");
        }

        // 读出来之后直接放入队列
        boolean offer = queue.offer(data);

        // 解析到最后一行 加入end
        if (!super.hasNext(context)) {
            offer = queue.offer(end);
        }
        log.debug("invoke queue.offer flag:{}", offer);
    }

    /**
     * 遍历每一批次结果集进行自定义处理
     *
     * @param consumer 自定义消费函数
     */
    public void foreach(Consumer<List<T>> consumer) {
        List<T> readResult;
        while ((readResult = getReadResult()).size() > 0) {
            consumer.accept(readResult);
        }
    }

    /**
     * 每次从queue队列中取出{@code numberOfEachExecuted}条元素，放入list
     *
     * @return list
     */
    public List<T> getReadResult() {
        List<T> list = ListUtil.newArrayList();
        T dto = null;
        try {
            /*
             阻塞从队列中取出 如果队列大于{numberOfEachExecuted}条 返回list 或者读到end
             queue.take()检索并删除此队列的头部，必要时等待直到元素可用。
             */
            while (list.size() < numberOfEachExecuted && (dto = queue.take()) != end) {
                list.add(dto);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // 二次入锁 解决死循环问题
        if (dto == end) {
            // 如果可以立即插入指定元素而不违反容量限制，则将其插入此队列，并在成功时返回true，如果当前没有可用空间则返回false。
            // 当使用容量受限的队列时，通常应优先使用此方法，而不是add方法，后者只能通过抛出异常来表示无法插入元素。
            boolean offer = queue.offer(end);
            log.debug("queue.offer(end) flag:{}", offer);
        }
        return list;
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        boolean offer = queue.offer(end);
        log.debug("queue.offer(end) flag:{}", offer);
    }

}
