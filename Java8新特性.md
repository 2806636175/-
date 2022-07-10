# Java8新特性

#### 1.Lambda表达式

##### 1.1为什么使用Lambda表达式

Lambda是一个匿名函数，可以让我们写出更简洁、更方便的代码。

##### 1.2Lambda表达式基础语法

"->"操作符是Lambda操作符，箭头操作符将Lambda表达式拆分为两部分：

左侧：Lambda表达式的参数列表

右侧：Lambda表达式所执行的功能

语法格式一、无参数无返回值

```java
()->System.out.println("hello world");
Runnable a=()->System.out.println("hello Lambda");
a.run();
```

语法格式二、一个参数无返回值

```java
(x)->System.out.println(x);
```

语法格式三、若只有一个参数（）可以不写

语法格式四、有两个以上参数，有返回值，Lambda体中有多条语句

```java
Compartor<Interger> con=(x,y)->{
	System.out.println("函数式接口");
	return Interger.compare(x,y);
}
```

Lambda表达式需要"函数式接口"的支持

#### 2.函数式接口

##### 2.1什么是函数式接口

接口中只有一个抽象方法的接口，称为函数式接口

可以使用注解@FunctionInterface修饰，可以检查是否是函数式接口

##### 2.2Java8内置四大核心函数式接口

Consumer<T> :消费型接口

​		void accept(T t);

Supplier<T> :供给型接口

 		T  get();

Function<T,R> :函数型接口

​		R apply(T t);

Predicate<T>  : 断言型接口

​		boolean test(T t);

#### 3.方法引用和构造器引用

##### 3.1方法引用

若Lambda方法体中的内容已经有方法实现了，我们可以使用"方法引用"

（可以理解为方法引用时Lambda表达式的另一种表现形式）

##### 3.2三种语法格式

对象：：实例方法名

类：：静态方法名

类：实例方法名

1、Lambda体中调用方法的参数列表与返回值类型，要与函数式接口列表的的返回值类型一致；

2、若 Lambda 参数列表中第一参数是实例方法的调用者，第二参数是是实例方法的参数，可以使用ClassName::Method

##### 3.3构造器引用

ClassName::new 

默认调用的是无参构造函数

需要调用的构造器参数列表与函数式接口中抽象方法参数保持一致。

##### 3.4数组引用

Type::new

#### 4.Stream API

##### 4.1Stream 的三步操作

###### 1.创建Stream

```Java
//创建Stream
    @Test
   public void test(){
        //1.可以通过Collection 系列集合提供的Stream（）（获取的串行流）或parallelStream（）（获取的并行流）获取流
        List<String>  list=new ArrayList<String>();
        Stream<String> stream = list.stream();

        //2.通过Arrays中的静态方法stream()获取数组流
        Integer a[] = new Integer[10];
        Stream<Integer> stream1 = Arrays.stream(a);

        //3.通过Stream类中的静态方法of（）方法
        Stream<String> aa = Stream.of("aa", "cc");

        //4.创建无限流
        //迭代器
        Stream<Integer> iterate = Stream.iterate(0, (x) -> x += 2);
        iterate.limit(10).forEach(System.out::println);

        //生成
        Stream.generate(()->Math.random())
                .limit(5)
                .forEach(System.out::println);
    }
```

###### 2.中间操作

多个中间操作可以连成一个流水线，除非流水线出发终止操作，否则中间操作不会做出任何处理，而在终止操作一次性执行，称为“惰性处理”。

```Java
//中间操作
    //筛选和切片
    /*
    * filter-接收Lambda，从流中筛选元素
    * limit-截断流，使其元素不超过给定数量
    * skip-跳过元素，返回一个扔掉前n个元素的流，若流中元素小于n个，则返回一个空流，与limit互补
    * distinct-筛选，通过流生成的hashcode()和equals()去除重复元素*/
    @Test
    public void test1(){
        Stream<Employee> stream = employees.stream().filter(e -> e.getAge() > 20);
        stream.forEach(System.out::println);
        employees.stream()
                .filter(e->{
                   return e.getAge()>20;
                })
                .limit(1)
                .forEach(System.out::println);
    }
```

```java
//    映射
//    map-接收Lambda，将元素转换为其他形式或提取信息。接收一个函数作为参数，该函数会被应用到每个元素上，并将其映射成一个新的元素
//    flatMap-接收一个函数作为参数，将流中的每个值换成另一个流，然后把所有流传承一个流
    @Test
    public void test3(){
        List<String> list= Arrays.asList("aaa","bbb","ccc");
        list.stream()
                .map(e->e.toUpperCase())
                .forEach(System.out::println);

        System.out.println("-------");

       //        Stream<Stream<Character>> streamStream = list.stream()
//                .map(StramApiTest::filterCharacter);
//        streamStream.forEach(e->{
//            e.forEach(System.out::println);
//        });
        Stream<Character> characterStream = list.stream()
                .flatMap(StramApiTest::filterCharacter);
        characterStream.forEach(System.out::println);

    }

    public static Stream<Character> filterCharacter(String str){
        List<Character> list=new ArrayList<>();
        for(Character ch:str.toCharArray()){
            list.add(ch);
        }
        return list.stream();
    }
```

```java
 /*
    * 排序
    * sorted-自然排序
    * sorted(Comparator com)-定制排序
    * */
    @Test
    public void test4(){
        List<String> list= Arrays.asList("bbb","aaa","ccc");
        list.stream()
            .sorted()
            .forEach(System.out::println);

        employees.stream()
                .sorted((e1,e2)->{
                    return e1.getAge().compareTo(e2.getAge());
                })
                .forEach(System.out::println);
    }
```

3.终止操作

###### 查找与匹配

allMatch-检查是否匹配所有元素

anyMatch-检查至少匹配一个元素

noneMatch-检查是否没有匹配所有元素

findfirst-返回第一个元素

findAny-返回当前流中任意元素

count-计数

max-返回流中最大值

min-最小值

```Java
@Test
    public void test5(){
        boolean b = employees.stream()
                .allMatch(e -> e.getAge() == 20);
        System.out.println(b);

        System.out.println("--------------");

        boolean b1 = employees.stream()
                .anyMatch(e -> e.getAge() == 20);
        System.out.println(b1);

        System.out.println("--------------");

        boolean b2 = employees.stream()
                .noneMatch(e -> e.getAge() == 20);
        System.out.println(b2);

        System.out.println("--------------");
        
        Optional<Employee> first = employees.stream()
                .sorted((e1, e2) -> {
                    return e1.getAge().compareTo(e2.getAge());
                })
                .findFirst();
        System.out.println(first.get());
    }
```

###### 归约和收集

```java
//归约
    //reduce(T identity,BinaryOperator)--可以将流中元素反复结合起来，得到一个值
//    收集
//    collect-将流转换为其他形式，接收Collector接口的实现，用于Stream中元素做汇总的方法
    @Test
    public void test6(){
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        Integer reduce = list.stream()
                .reduce(0, (x, y) -> x + y);
        System.out.println(reduce);

        Optional<Integer> reduce1 = employees.stream()
                .map(Employee::getAge)
                .reduce(Integer::sum);
        //map和reduce结合的叫map-reduce模式
        System.out.println(reduce1.get());

        employees.stream()
                 .map(Employee::getName)
                 .collect(Collectors.toList())
                 .forEach(System.out::println);

        employees.stream()
                .map(Employee::getName)
                .collect(Collectors.toSet())
                .forEach(System.out::println);

        employees.stream()
                .map(Employee::getName)
                .collect(Collectors.toCollection(HashSet::new))
                .forEach(System.out::println);
    }
```

#### 5.并行流和顺序流

![image-20210528144840421](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\image-20210528144840421.png)

###### Java8并行流

```java
@Test
    public void test1(){
        /**
         * java8并行流
         */
        Instant start = Instant.now();
        LongStream.rangeClosed(0,100000000000L)
                  .parallel()
                  .reduce(0,Long::sum);
        Instant end = Instant.now();
        System.out.println(Duration.between(start,end).toMillis());
    }
```

#### 6.Optional容器类

![image-20210528153134439](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\image-20210528153134439.png)

```Java
@Test
    public void test(){
        //程序不报错，运行报空指针异常
        Optional<Employee> emp=Optional.of(null);
        Employee e=emp.get();
        System.out.println(e);
    }
```

```java
 @Test
    public void test(){
        //程序不报错，运行报空指针异常
//        Optional<Employee> emp=Optional.of(null);
//        Employee e=emp.get();
//        System.out.println(e);
//        Optional<Employee> op=Optional.empty();
//        System.out.println(op.get());
        Optional<Employee> op = Optional.ofNullable(null);
//        Employee emp=op.orElse(new Employee("李四",5,6));

        Employee emp = op.orElseGet(() -> new Employee());
        System.out.println(emp);
        Optional<Employee> op1=Optional.ofNullable(new Employee("李四",5,6));
        Optional<String> a=op1.map(e->e.getName());
        Optional s=op1.flatMap(e->Optional.of(e.getName()));
        System.out.println(a.get());
        System.out.println(s.get());
    }
```

#### 7.接口中默认方法和静态方法

Java8中的默认方法和静态方法可以实现

```java
default String getName(){
	return "哈哈哈";
}

public static void show(){
    System.out.println("gagaga");
}
```

![image-20210528162128108](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\image-20210528162128108.png)

#### 8.新时间日期API

![image-20210529203345271](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\image-20210529203345271.png)

```java
LocalDateTime.plusYears(2);当前日期加两年，月日换Years就行
    minus是减，plus是加
Instant:时间戳（以Uniox元年：1970年1月1日00：00：00到某个时间的毫秒值）
    Instant ins1=Instant.now();//获取的是UTC时间，相差8小时
	OffestDateTime odt=ins1.atOffest(ZoneOffest.ofHours(8));//获取的是当前时间
Duration:计算两个时间间隔
Period:计算两个日期之间的间隔
```

![image-20210529205131142](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\image-20210529205131142.png)

```java
TemporalAdjuster:时间校正器
LocalDateTime ldt=LocalDateTime.now().with(TemporalAdjusters.next(DayOfWeek.SUNDAY));

DateTimeFormatter:格式化日期时间
    DateTimeFormatter dtf=DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH:mm:ss");
	LocalDateTime ldt=LocalDateTime.now();
	String a=dtf.format(ldt);

时区处理：ZonedTime,ZonedDate,ZonedDateTime
    ZoneId.getAvailableZoneIds()
              .stream()
              .forEach(System.out::println);
```



#### 9.其他新特性

###### 9.1重复注解和类型注解

![image-20210529211304544](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\image-20210529211304544.png)

