学习笔记

Before：这道算法题通宵也要做出来。

After：思考2分钟，看题解，理解加背写。同一个题多刷几遍。

从结果上来看，曾经的方法的确不好，浪费时间，写出的算法也比较搓，代码很长，而且就写一遍，下次还是写不出来。用了超哥的方法可以花较少的时间做下来不少题，然后在反复的默写背诵中提升对于代码的理解。

数组问题注意事项：

指针(双指针反转数组、三指针、去重等等)、排序、边界条件。

链表问题注意事项：

递归（返回值，结束条件，操作）、辅助节点、运气。

# Queue：

https://blog.csdn.net/devnn/article/details/82591349  && https://www.cnblogs.com/Free-Thinker/p/3581490.html

![image-20200913122203710](C:\Users\姜天才\AppData\Roaming\Typora\typora-user-images\image-20200913122203710.png)

![image-20200913115341777](C:\Users\姜天才\AppData\Roaming\Typora\typora-user-images\image-20200913115341777.png)

**Queue queue = new LinkedList();**  

LinkedList 是一个继承于AbstractSequentialList的双向链表。它也可以被当作堆栈、队列或双端队列进行操作。
LinkedList 实现 List 接口，能对它进行队列操作。
LinkedList 实现 Deque 接口，即能将LinkedList当作双端队列使用。
LinkedList 实现了Cloneable接口，即覆盖了函数clone()，能克隆。
LinkedList 实现java.io.Serializable接口，这意味着LinkedList支持序列化，能通过序列化去传输。
LinkedList 是非同步的。

LinkedList类的部分源码

![image-20200913115056861](C:\Users\姜天才\AppData\Roaming\Typora\typora-user-images\image-20200913115056861.png)

![image-20200913115135245](C:\Users\姜天才\AppData\Roaming\Typora\typora-user-images\image-20200913115135245.png)

![image-20200913115221279](C:\Users\姜天才\AppData\Roaming\Typora\typora-user-images\image-20200913115221279.png)

![image-20200913115424362](C:\Users\姜天才\AppData\Roaming\Typora\typora-user-images\image-20200913115424362.png)

![image-20200913115620948](C:\Users\姜天才\AppData\Roaming\Typora\typora-user-images\image-20200913115620948.png)

![image-20200913120531016](C:\Users\姜天才\AppData\Roaming\Typora\typora-user-images\image-20200913120531016.png)

addFirst(E e)方法 ---->  linkFirst(E e)。。。。

https://www.cnblogs.com/xujian2014/p/4630785.html源码分析

```详见LinkedList源码分析.md

modCount标识这个这个链表结构被修改的次数

使用方法：

Queue是java中实现队列的接口，它总共只有6个方法，我们一般只用其中3个就可以了。Queue的实现类有LinkedList和PriorityQueue。最常用的实现类是LinkedList。

Queue的6个方法分类：

### 压入元素(添加)：add()、offer()

相同：未超出容量，从队尾压入元素，返回压入的那个元素。
区别：在超出容量时，add()方法会对抛出异常，offer()返回false

### 弹出元素(删除)：remove()、poll()

相同：容量大于0的时候，删除并返回队头被删除的那个元素。
区别：在容量为0的时候，remove()会抛出异常，poll()返回false

### 获取队头元素(不删除)：element()、peek()

相同：容量大于0的时候，都返回队头元素。但是不删除。
区别：容量为0的时候，element()会抛出异常，peek()返回null。

队列除了基本的 Collection 操作外，还提供特有的插入、提取和检查操作(如上)。每个方法都存在两种形式：一种抛出异常（操作失败时），另一种返回一个特殊值（null 或 false，具体取决于操作）。插入操作的后一种形式是用于专门为有容量限制的 Queue 实现设计的；在大多数实现中，插入操作不会失败。

深入底层与扩展：

![image-20200913114153869](C:\Users\姜天才\AppData\Roaming\Typora\typora-user-images\image-20200913114153869.png)

**LinkedBlockingQueue**的容量是没有上限的（说的不准确，在不指定时容量为Integer.MAX_VALUE，不要然的话在put时怎么会受阻呢），但是也可以选择指定其最大容量，它是基于链表的队列，此队列按 FIFO（先进先出）排序元素。





# Priority Queue：

如何实现：

https://www.cnblogs.com/ganchuanpu/p/9115239.html



具体使用：

https://www.cnblogs.com/wei-jing/p/10806236.html

优先队列PriorityQueue是Queue接口的实现，可以对其中元素进行排序，

可以放基本数据类型的包装类（如：Integer，Long等）或自定义的类

对于基本数据类型的包装器类，优先队列中元素默认排列顺序是升序排列

但对于自己定义的类来说，需要自己定义比较器

**PriorityBlockingQueue**是一个带优先级的 队列，而不是先进先出队列。元素按优先级顺序被移除，该队列也没有上限（看了一下源码，PriorityBlockingQueue是对 PriorityQueue的再次包装，是基于堆数据结构的，而PriorityQueue是没有容量限制的，与ArrayList一样，所以在优先阻塞 队列上put时是不会受阻的。虽然此队列逻辑上是无界的，但是由于资源被耗尽，所以试图执行添加操作可能会导致 OutOfMemoryError），但是如果队列为空，那么取元素的操作take就会阻塞，所以它的检索操作take是受阻的。另外，往入该队列中的元 素要具有比较能力。

![image-20200913123137547](C:\Users\姜天才\AppData\Roaming\Typora\typora-user-images\image-20200913123137547.png)
```