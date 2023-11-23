# Queue

- Queue表示队列，是一种特殊的线性表，特殊之处在于它只支持在队头取出元素，在队尾插入元素。
- Deque表示双端队列（Double Ended Queue），它和Queue的区别是其队头、队尾都能添加和获取元素。

使用数组实现的ArrayBlockingQueue总是有界的，而LinkBlockingQueue可以是无界的。

- 有界队列：有固定大小的队列。
- 无界队列：没有设置固定大小的队列。

LinkedBlockingQueue在不设置大小的时候，默认值为Integer.MAX_VALUE，可认为是无界的。

# 线程问题

为了保证并发安全，ArrayBlockingQueue在插入和删除数据时使用的是同一把锁。而LinkBlockingQueue则是在插入和删除数据时分别采用了putLock和takeLock，显然LinkedBlockingQueue的并发度更高一些。

# 阻塞队列和非阻塞队列

在Java并发包中，队列（QUEUE）的实现主要分两种，一种是以ConcurrentLinkedQueue为代表的非阻塞队列，另一种是以BlockingQueue接口为代表的阻塞队列。

阻塞队列：当读取或者插入元素时，如果队列不满足条件（如为空或已满）时，将等待条件满足时才能继续进行。
阻塞队列能防止队列容器溢出，避免数据丢失。而非阻塞队列虽然安全性不如阻塞队列，但性能要好一些，因为它不需要阻塞。

