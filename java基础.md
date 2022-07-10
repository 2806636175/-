### java基础



##### 1.1 人机交互方式

图形化界面GUI

命令行方式CLI

##### 1.2 常用命令行指令

dir:列出当前文件夹下文件目录

md:创建文件夹

cd:切换路径

cd.. :返回上一级目录

cd/ :返回根目录

del:删除文件夹

##### 1.3 java语言的特点

###### 1.3.1 面向对象性：三大特性（封装、继承、多态）

###### 1.3.2 健壮性：舍弃了C语言中容易引起错误的指针，增加垃圾回收器功能（使得内存管理更加安全）

###### 1.3.3 跨平台性：在不同的平台上都能运行，而不像C#局限于微软平台

##### 1.4 Jvm和垃圾回收机制

Jvm：jvm让java实现了一次编译，到处运行，使得Java程序在各个平台的jvm里运行

![image-20201227170213611](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\image-20201227170213611.png)

Jvm核心机制-垃圾回收

Java程序会出现内存泄漏或内存溢出吗？

答：会

##### 1.5 JDK、JRE、JVM

JDK:Java开发工具包，包含Jre和Java的开发工具

JRE:Java运行环境，包含Java程序所需的核心类库和JVM

##### 1.6数据类型

基本数据类型：数值型：整数类型:int、short、long、byte

​											浮点型：double、float

​							字符型：char

​							布尔型：bool

引用数据类型：类：class

​							接口：interface

​							数组：array([])

基本数据类型之间的运算规则：

1.自动类型提升：当容量小的数据类型的变量和容量大的 数据类型的变量做运算时，结果自动提升为容量大的数据类型

​	byte、short、int、long、float、double依次递增

​	special:  char、byte、short三者运算结果类型都为int

2.强制类型转换：自动类型提升运算的逆运算

string类型变量的使用：

a:string是引用数据类型

b:声明string类型时用""

c:string可以和8中种基本数据类型进行运算，且运算只能为连接运算：+

##### 1.7面向对象编程

1.7.1java类及类的成员：属性、方法、构造器、代码块、内部类

1.7.2面向对象三大特征：封装、继承、多态

1.7.3其他关键字：this、super、static、final、abstract、interface、package、import

面向过程：强调功能行为，以函数为最小单位，考虑怎么做

a.打开冰箱

b.抬起大象塞进冰箱

c.关闭冰箱门

面向对象：强调具备了功能的对象，以类/对象为最小单位，考虑谁来做

方法的重载：

定义：在同一个类中，允许一个以上的同名方法，只要他们的参数个数或参数类型不同就行

方法的重写：

定义：在子类继承父类的时候，子类可以对父类的方法进行重写，重写的方法的方法名和形参要一样，这样子类的方法会覆盖父类的此方法。（参数不一样时是重载，不是重写）

###### 封装性：

高内聚，低耦合：

![image-20210131221129786](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\image-20210131221129786.png)

高内聚：类的内部数据操作细节自己完成，不允许外部干涉。

低耦合：仅对外暴露少量方法用于使用

##### ==和equals的区别

1.==既可以比较基本类型，也可以比较引用类型，引用类型比较的的是内存地址，基本类型就是比较值。

2.equals属于Object类里的方法，如果该方法没有被重写过默认也是==，String类里的equals方法是被重写过的，在String类中比较常用

a.是一个方法，而非运算符，

b.只能适用于引用类型

c.像String、Date、File、包装类等都重写了Object类中的equals方法

##### 继承性

继承性的好处：

1.减少代码冗余，提高代码复用性

2.便于功能拓展

3.为多态性提供了前提

继承性的格式 class A extends B{}

A:子类、派生类、subclass

B:父类、基类、超类、superclass

体现 ：一旦A类继承B类以后，子类A中就获取了父类B中声明的所有属性和方法

##### 四种访问权限修饰符

![image-20210310142741684](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\image-20210310142741684.png)

##### Super关键字的使用

1.Super理解为父类的

2.Super可以调用：属性、方法、构造器

3.Super的使用

   3.1我们可以在子类的方法或构造器中，通过"Super.属性"或"Super.方法"的方式，显示的调用父类中声明的属性和方法，通常情况下我们习惯省略Super。

​	3.2特殊情况：当子父类中定义了同名的属性时，我们想要在子类调用父类的中声明的属性必须加上Super，表明调用的父类中的声明的属性，方法和属性同样。

![image-20210310145103322](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\image-20210310145103322.png)

##### 多态性

理解多态性：可以理解为一个事物的多种形态，

何为多态性：父类的引用指向子类的对象

![image-20210310161349305](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\image-20210310161349305.png)

多态的使用：虚拟方法调用，p2只能调用person父类的方法，如果person父类的方法被子类man重写，结果为子类里的结果

多态的使用前提：1.类的继承关系2.方法的重写

多态性不适用于属性（int 那些定义的字段）

##### 设计模式

单例模式：就是采取一定方法保证在整个软件系统中，对某个类只能存在一个对象实例

//饿汉式

![image-20210311140848432](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\image-20210311140848432.png)

//懒汉式

![image-20210311141129734](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\image-20210311141129734.png)

区分懒汉和饿汉

饿汉：坏处：对象加载时间过长  好处：线程是安全的

懒汉：好处：延迟对象创建  目前的写法：线程不安全

应用场景：网站的计数器、数据库连接池

##### 对属性赋值先后顺序：

![image-20210311142303688](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\image-20210311142303688.png)



##### final关键字

1.final可以修饰的结构：类、方法、变量

2.final用来修饰一个类：此类不能被其他类继承；比如：String类、System类、String Buffer类

3.final用来修饰一个方法：表名此方法不可被重写

4.final用来修饰变量：此时的变量就是一个常量

​	4.1final修饰的变量可以用显示化初始化、代码块初始化、和构造器初始化

​	4.2final可以修饰局部变量：使用final修饰形参时，表名此形参是常量

static final 用来修饰全局常量

##### 接口

![image-20210311150316953](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\image-20210311150316953.png)

##### 多线程

程序：一段静态代码，静态对象。

进程：是正在运行的程序，生命周期：自身的产生、存在、消亡

​			进程是动态的，进程是资源分配单位，，系统在运行时为每个进程分配不同的内存区域

线程：进程可进一步细分为线程，是一个程序内部的一条执行路径

![image-20210312191611631](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\image-20210312191611631.png)

##### 多线程的优点

1.提高应用程序的响应。增强用户体验。

2.提高系统计算机CPU的利用率。

3.改善程序结构。将既长又复杂的进程分为多个线程，独立运行，方便理解有利于修改

##### 线程的常用方法

![image-20210317144209973](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\image-20210317144209973.png)

##### 线程的优先级

MAX_PRIORITY:10

MIN_PRIORITY:1

NORM_PRIORITY:5

如何获取和设置当前优先级

getPriority():获取线程优先级

setPriority(int p):设置线程优先级

##### 线程的生命周期

![image-20210322160756724](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\image-20210322160756724.png)











​	

