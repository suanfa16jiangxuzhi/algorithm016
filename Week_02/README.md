学习笔记

HashMap

一、HashMap的扩容机制

HashMap的初始容量大小默认为16，当HashMap的数组长度到达一个临界值的时候，会触发扩容，这个临界值也就是	当前大小	乘以	加载因子(0.75)时，就会触发扩容操作。eg：16 * 0.75 = 12

二、构造方法



```
//序列号
    private static final long serialVersionUID = 362498820763181265L;
 
    //默认初容量始
    static final int DEFAULT_INITIAL_CAPACITY = 1 << 4; // aka 16
 
    //最大容量
    static final int MAXIMUM_CAPACITY = 1 << 30;
 
    //扩容因子
    static final float DEFAULT_LOAD_FACTOR = 0.75f;
 
    //链表节点数大于8个链表转红黑树
    static final int TREEIFY_THRESHOLD = 8;
 
    //链表节点小于6个链表转红黑树
    static final int UNTREEIFY_THRESHOLD = 6;
 
    //当容量大于等于64的时候才触发链表转红黑树
    static final int MIN_TREEIFY_CAPACITY = 64;
 
    //以Node数组存储元素，长度为2的次幂
    transient Node<K,V>[] table;
 
    //具体元素存放集
    transient Set<Map.Entry<K,V>> entrySet;
 
    //存放的元素的个数，数组加链表一共
    transient int size;
 
    //每次扩容和更改map结构的计数器，源码中有特定的地方触发
    transient int modCount;
 
    //临界值 = 容量 * 扩容因子
    int threshold;
 
    //扩容因子，默认值是0.75，可以在构造器中自定义
    final float loadFactor;
```

```
// 默认构造函数。
public HashMap() {
    // 设置“加载因子”
    this.loadFactor = DEFAULT_LOAD_FACTOR; //0.75
    // 设置“HashMap阈值”，当HashMap中存储数据的数量达到threshold时，就需要将HashMap的容量加倍。
    threshold = (int)(DEFAULT_INITIAL_CAPACITY * DEFAULT_LOAD_FACTOR);
    // 创建Entry数组，用来保存数据
    table = new Entry[DEFAULT_INITIAL_CAPACITY];
    init();
}

// 指定“容量大小”和“加载因子”的构造函数
public HashMap(int initialCapacity, float loadFactor) {
    if (initialCapacity < 0)
        throw new IllegalArgumentException("Illegal initial capacity: " +
                                           initialCapacity);
    // HashMap的最大容量只能是MAXIMUM_CAPACITY
    if (initialCapacity > MAXIMUM_CAPACITY)
        initialCapacity = MAXIMUM_CAPACITY;
    if (loadFactor <= 0 || Float.isNaN(loadFactor))
        throw new IllegalArgumentException("Illegal load factor: " +
                                           loadFactor);

    // Find a power of 2 >= initialCapacity
    int capacity = 1;
    while (capacity < initialCapacity)
        capacity <<= 1;
    
    // 设置“加载因子”
    this.loadFactor = loadFactor;
    // 设置“HashMap阈值”，当HashMap中存储数据的数量达到threshold时，就需要将HashMap的容量加倍。
    threshold = (int)(capacity * loadFactor);
    // 创建Entry数组，用来保存数据
    table = new Entry[capacity];
    init();

}

// 指定“容量大小”的构造函数
public HashMap(int initialCapacity) {
    this(initialCapacity, DEFAULT_LOAD_FACTOR);
}

// 包含“子Map”的构造函数
public HashMap(Map<? extends K, ? extends V> m) {
    this(Math.max((int) (m.size() / DEFAULT_LOAD_FACTOR) + 1,
                  DEFAULT_INITIAL_CAPACITY), DEFAULT_LOAD_FACTOR);
    // 将m中的全部元素逐个添加到HashMap中
    putAllForCreate(m);
}
```



在了解put和get方法之前先看一下hashmap的hash（）这个方法：

# 定位哈希桶数组索引位置

不管增加、删除、查找键值对，定位到哈希桶数组的位置都是很关键的第一步。前面说过 HashMap 的数据结构是“数组+链表+红黑树”的结合，所以我们当然希望这个 HashMap 里面的元素位置尽量分布均匀些，尽量使得每个位置上的元素数量只有一个，那么当我们用 hash 算法求得这个位置的时候，马上就可以知道对应位置的元素就是我们要的，不用遍历链表/红黑树，大大优化了查询的效率。HashMap 定位数组索引位置，直接决定了 hash 方法的离散性能。下面是定位哈希桶数组的源码：

```
// 代码1
static final int hash(Object key) { // 计算key的hash值
​    int h;
​    // 1.先拿到key的hashCode值; 2.将hashCode的高16位参与运算
​    return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
}
// 代码2
int n = tab.length;
// 将(tab.length - 1) 与 hash值进行&运算
int index = (n - 1) & hash;
```

整个过程本质上就是三步：

1. 拿到 key 的 hashCode 值
2. 将 hashCode 的高位参与运算，重新计算 hash 值
3. 将计算出来的 hash 值与 (table.length - 1) 进行 & 运算

put方法：

1. ```
   1. public V put(K key, V value) {
   2. ​    return putVal(hash(key), key, value, false, true);
   3. }
   4.  
   5. final V putVal(int hash, K key, V value, boolean onlyIfAbsent,
   6. ​               boolean evict) {
   7. ​    Node<K,V>[] tab; Node<K,V> p; int n, i;
   8. ​    // 1.校验table是否为空或者length等于0，如果是则调用resize方法进行初始化
   9. ​    if ((tab = table) == null || (n = tab.length) == 0)
   10. ​        n = (tab = resize()).length;
   11. ​    // 2.通过hash值计算索引位置，将该索引位置的头节点赋值给p，如果p为空则直接在该索引位置新增一个节点即可
   12. ​    if ((p = tab[i = (n - 1) & hash]) == null)
   13. ​        tab[i] = newNode(hash, key, value, null);
   14. ​    else {
   15. ​        // table表该索引位置不为空，则进行查找
   16. ​        Node<K,V> e; K k;
   17. ​        // 3.判断p节点的key和hash值是否跟传入的相等，如果相等, 则p节点即为要查找的目标节点，将p节点赋值给e节点
   18. ​        if (p.hash == hash &&
   19. ​            ((k = p.key) == key || (key != null && key.equals(k))))
   20. ​            e = p;
   21. ​        // 4.判断p节点是否为TreeNode, 如果是则调用红黑树的putTreeVal方法查找目标节点
   22. ​        else if (p instanceof TreeNode)
   23. ​            e = ((TreeNode<K,V>)p).putTreeVal(this, tab, hash, key, value);
   24. ​        else {
   25. ​            // 5.走到这代表p节点为普通链表节点，则调用普通的链表方法进行查找，使用binCount统计链表的节点数
   26. ​            for (int binCount = 0; ; ++binCount) {
   27. ​                // 6.如果p的next节点为空时，则代表找不到目标节点，则新增一个节点并插入链表尾部
   28. ​                if ((e = p.next) == null) {
   29. ​                    p.next = newNode(hash, key, value, null);
   30. ​                    // 7.校验节点数是否超过8个，如果超过则调用treeifyBin方法将链表节点转为红黑树节点，
   31. ​                    // 减一是因为循环是从p节点的下一个节点开始的
   32. ​                    if (binCount >= TREEIFY_THRESHOLD - 1)
   33. ​                        treeifyBin(tab, hash);
   34. ​                    break;
   35. ​                }
   36. ​                // 8.如果e节点存在hash值和key值都与传入的相同，则e节点即为目标节点，跳出循环
   37. ​                if (e.hash == hash &&
   38. ​                    ((k = e.key) == key || (key != null && key.equals(k))))
   39. ​                    break;
   40. ​                p = e;  // 将p指向下一个节点
   41. ​            }
   42. ​        }
   43. ​        // 9.如果e节点不为空，则代表目标节点存在，使用传入的value覆盖该节点的value，并返回oldValue
   44. ​        if (e != null) {
   45. ​            V oldValue = e.value;
   46. ​            if (!onlyIfAbsent || oldValue == null)
   47. ​                e.value = value;
   48. ​            afterNodeAccess(e); // 用于LinkedHashMap
   49. ​            return oldValue;
   50. ​        }
   51. ​    }
   52. ​    ++modCount;
   53. ​    // 10.如果插入节点后节点数超过阈值，则调用resize方法进行扩容
   54. ​    if (++size > threshold)
   55. ​        resize();
   56. ​    afterNodeInsertion(evict);  // 用于LinkedHashMap
   57. ​    return null;
   58. }
   ```

   

get方法：

1. ```
   1. public V get(Object key) {
   2. ​    Node<K,V> e;
   3. ​    return (e = getNode(hash(key), key)) == null ? null : e.value;
   4. }
   5.  
   6. final Node<K,V> getNode(int hash, Object key) {
   7. ​    Node<K,V>[] tab; Node<K,V> first, e; int n; K k;
   8. ​    // 1.对table进行校验：table不为空 && table长度大于0 && 
   9. ​    // table索引位置(使用table.length - 1和hash值进行位与运算)的节点不为空
   10. ​    if ((tab = table) != null && (n = tab.length) > 0 &&
   11. ​        (first = tab[(n - 1) & hash]) != null) {
   12. ​        // 2.检查first节点的hash值和key是否和入参的一样，如果一样则first即为目标节点，直接返回first节点
   13. ​        if (first.hash == hash && // always check first node
   14. ​            ((k = first.key) == key || (key != null && key.equals(k))))
   15. ​            return first;
   16. ​        // 3.如果first不是目标节点，并且first的next节点不为空则继续遍历
   17. ​        if ((e = first.next) != null) {
   18. ​            if (first instanceof TreeNode)
   19. ​                // 4.如果是红黑树节点，则调用红黑树的查找目标节点方法getTreeNode
   20. ​                return ((TreeNode<K,V>)first).getTreeNode(hash, key);
   21. ​            do {
   22. ​                // 5.执行链表节点的查找，向下遍历链表, 直至找到节点的key和入参的key相等时,返回该节点
   23. ​                if (e.hash == hash &&
   24. ​                    ((k = e.key) == key || (key != null && key.equals(k))))
   25. ​                    return e;
   26. ​            } while ((e = e.next) != null);
   27. ​        }
   28. ​    }
   29. ​    // 6.找不到符合的返回空
   30. ​    return null;
   31. }
   ```

   

大部分转自：https://blog.csdn.net/v123411739/article/details/78996181?biz_id=102&utm_term=hashmap%E8%AF%A6%E7%BB%86&utm_medium=distribute.pc_search_result.none-task-blog-2~all~sobaiduweb~default-0-78996181&spm=1018.2118.3001.4187#get%20%E6%96%B9%E6%B3%95