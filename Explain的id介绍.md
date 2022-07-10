## Explain的id介绍

![image-20210328140833653](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\image-20210328140833653.png)

##### id

select查询的序列号，包含一组数字，表示查询中执行select字句或操作表的顺序

​		三种情况：1.id相同情况下，执行顺序由上至下

​							2.如果是子查询，id的序号会递增，id值越大，优先级越高，越先被执行

​							3.![image-20210328141851252](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\image-20210328141851252.png)

衍生=DERIVED

##### select-type

![image-20210328142221478](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\image-20210328142221478.png)



SIMPLE：简单的select查询，查询中不包含子查询和Union 

PRIMARY：查询中若包含任何的子查询，最外层的被标记为PRIMARY

SUBQUERY：在select或where中包含的子查询

DERIVED：在FROM列表中包含的子查询被标记为DERIVED（衍生），MySql会递归执行这些子查询，把结					  果放在临时表里

UNION：若第二个SELECT出现在UNION之后，则被标记为UNION，若UNION包含在FROM字句的子查询				  中，外层SELECT则被标记为DERIVED

UNION RESULT：从UNION表中获取结果的SELECT

##### TABLE：显示这行数据属于哪张表

##### TYPE：访问类型排列，显示查询用了何种类型；

​				最好到最差依次是：system>const>eq_ref>ref>range>index>all

system:表只有一行记录（等于系统表），这是const类型的特例，平时不会出现，可以忽略不计。

const:表示通过一次索引就找到了，const用于比较primary key和unique索引，因为只匹配一行数据，所以很快

eq_ref:唯一性索引扫描，对于每个索引键，表中只有一个记录与他匹配，常用于主键或唯一索引扫描

ref:非唯一索引扫描，返回匹配某个单独值的所有行

range:只检索给定范围的行

index:index类型之遍历索引树，通常比ALL快（all和index都是读取全表）

all:扫描全表找到所需要的行

##### POSSIBLE-KEYS

显示可能应用在这张表中的索引（一个或多个）

查询设计到的字段上若存在索引，则该索引被列出，但不一定被查询实际使用

##### KEY

实际使用的索引，如果为NUll，则没有使用索引

若查询中使用了覆盖索引，则该索引只出现在key列表中

##### KEY_LEN

![image-20210328223834183](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\image-20210328223834183.png)

##### REF

显示索引哪一列被使用了，如果可能的话，是一个常数。哪些列或常量被用于查找索引列上的值

##### ROWS

![image-20210328224832853](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\image-20210328224832853.png)

##### EXTRA

包含不适合在其他列显示但十分重要的额外信息







