# 缓存模块

支持redis和本地ConcurrentHashMap缓存

## 配置

`application.yml`

```yaml
kuugatool:
  cache:
    save-redis:
      cluster-enable: false
      url: 127.0.0.1
      port: 6379
      db-index: 0
      password:
    redis-enable: false
    query-use-save-properties: true
```

## 启动类注入

```java

@Import({KuugaCacheConfig.class})
public class CacheApplication {

}
```

## 使用DEMO

```java
import lock.io.github.kuugasky.kuugatool.cache.QuickLock;

import javax.annotation.Resource;

public class KuugaCacheTest {
    @Resource
    private KuugaCache kuugaCache;

    @Test
    void test() {
        KuugaCache.put(KuugaRedisCacheKey.REDIS_TEST, "key", "value", 10);
        Object value = kuugaCache.get(KuugaRedisCacheKey.REDIS_TEST, "key");
        long ttl = kuugaCache.ttl(KuugaRedisCacheKey.REDIS_TEST, "key");
    }

    @Test
    void quickLock() {
        String lockKey = "key";
        String status = QuickLock.fastLock(lockKey, kuugaCache, () -> {
            System.out.println("doSomethings...");
            return "done.";
        }, SysConstants.THREE);
        System.out.println(status);
    }

    @Test
    void doAction() {
        String wrapKey = agentCache.wrapKey(CacheKey.TASK_STATUS_BITMAP, form.getKey());
        agentCache.<RedisAction>doCustomAction(
                CacheOpeType.SAVE,
                redisTemplate -> redisTemplate.opsForValue().setBit(wrapKey, sourceModel.getId() - 1, true));
    }

    @Test
    void doCustomAction() {
        String lockKey = agentCache.wrapKey(CacheKey.REPEATED_REQUEST, String.format("%s:%s-%s", operateType.name(), entity.getId(), entity.getMessageId()));
        boolean flag = (boolean) agentCache.<RedisAction>doCustomAction(
                CacheOpeType.SAVE,
                redisTemplate -> redisTemplate.opsForValue().setIfAbsent(lockKey, 1, 30, TimeUnit.SECONDS));
    }

}
```

## 字符串类型和哈希类型在Redis中的区别如下

- 1.存储方式不同：字符串类型的值是单个字符串，而哈希类型的值是一个键值对集合。
- 2.存储结构不同：字符串类型的值是一个简单的字符串，而哈希类型的值是一个包含多个键值对的散列表。
- 3.访问方式不同：字符串类型的值只能通过键名访问，而哈希类型的值可以通过键名和字段名访问。
- 4.功能不同：字符串类型的值支持的操作有：设置、获取、追加、删除、统计等，而哈希类型的值支持的操作有：设置、获取、删除、统计等，还支持获取所有字段名和所有值的操作。
- 5.适用场景不同：字符串类型适合存储单个字符串值，如数字、文本等，而哈希类型适合存储多个键值对，如用户对象、商品对象等。

总的来说，字符串类型和哈希类型都是Redis中常用的数据类型，但是在不同的场景中需要根据具体情况选择使用哪种类型。
如果需要存储一个简单的字符串值，可以使用字符串类型，如果需要存储一个对象，可以使用哈希类型。

hash相当于MAP
赋值 key, hashKey, value
取值 key, hashKey

## list格式注意

伪代码：
leftPush(1)
leftPush(2)
leftPush(3)

for (int i = 0; i < 3 + 1; i++) {
leftPop(i)

依次输出：3，2，1

如果是：
rightPush(1)
rightPush(2)
rightPush(3)

for (int i = 0; i < 3 + 1; i++) {
rightPop(i)

也是输出：3，2，1

怎么理解呢？

有点像压栈的意思，比如leftPushAll(1,2,3),相当于就是从最左边依次插入0下标位，1插入到index0后，再插入2到index0，这时原先index0的值1就挪到index1。

然后leftPop，则是从最左边index0开始取值并移除，然后index的值1左移到index0.

right的方式也是一样，只是换了一个方向。

如果是右入左出，那么就跟平时理解比较贴合了，就是右入123，index0为1，index1为2，index2为3，就是常规理解的数组列表。

ListOperations里面有一大堆方法，用到再看吧，常规用的也就那么几个。

## 位图bitmap

Redis Bitmap 是 Redis 提供的一种位图数据结构，它可以用于存储二进制数据。

在 Redis 中，位图是一个`字符串`类型，字符串中的每个字符都是一个二进制位。

Redis Bitmap 支持一些位运算操作，例如与、或、非、异或等。

以下是 Redis Bitmap 的一些特点：

- 1.节省空间：Redis Bitmap 可以节省空间，因为它是基于位的，而不是字节的。例如，如果您需要存储 10 个布尔值，使用 Redis Bitmap
  只需要占用 1 个字节，而不是使用 10 个字节。
- 2.高效的位运算：Redis Bitmap 支持高效的位运算操作，例如与、或、非、异或等。这些操作可以在 O(1) 的时间复杂度内完成。
- 3.支持计数：Redis Bitmap 支持计算位图中设置为 1 的位数。这个操作可以在 O(N) 的时间复杂度内完成，其中 N 是位图的长度。
- 4.适用于布隆过滤器：Redis Bitmap
  可以用于实现布隆过滤器。布隆过滤器是一种数据结构，它可以快速判断一个元素是否存在于一个集合中。由于布隆过滤器存在一定的误判率，因此它通常用于场景中，对于误判率有一定容忍度的应用。

下面是 Redis Bitmap 的一些常用命令：

- 1.`SETBIT key offset value` ：将 key 对应的位图中，偏移量为 offset 的二进制位设置为 value。
- 2.`GETBIT key offset` ：获取 key 对应的位图中，偏移量为 offset 的二进制位的值。
- 3.`BITCOUNT key [start end]` ：计算 key 对应的位图中，值为 1 的二进制位的数量。如果指定了 start 和 end
  参数，则只计算偏移量在这个范围内的二进制位。
- 4.`BITOP operation destkey key [key ...]` ：对多个位图进行位运算，并将结果保存到 destkey 对应的位图中。operation 参数可以是
  AND、OR、XOR 或 NOT。
- 5.`BITPOS key bit [start] [end]` ：查找 key 对应的位图中，从 start 到 end 范围内，第一个值为 bit 的二进制位的偏移量。如果没有找到，返回
  -1。

希望这些信息可以帮助您了解 Redis Bitmap 的使用和特点。