学习笔记

三数之和的心得：

Arrays的asList方法：

（1）该方法不适用于基本数据类型（byte,short,int,long,float,double,boolean）

（2）该方法将数组与列表链接起来，当更新其中之一时，另一个自动更新

（3）不支持add和remove方法

一定要注意Arrays的asList()这个方法不适用于基本数据类型，并且这个方法返回的是Arrays类内置的arrayList类，这个内置类没有add()和remove()方法。坑

解决方法：使用包装类。