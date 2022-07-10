## Http协议

Http协议包括客户端和服务端两个实体，客户端发送请求给服务端，服务端返回响应给客户端，在http中，数据可以说是资源，可以是http文档、图片，也可以是txt文档，资源是通过url定位的；

![image-20210227221949877](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\image-20210227221949877.png) 

服务端收到Url会解析他们并返回相应的数据

Http的组成

![image-20210227222241536](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\image-20210227222241536.png)

![image-20210227222340032](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\image-20210227222340032.png)

Http协议是无状态的，每次客户端发送的请求都会被认为是从全新的客户端发送过来的，如果需要记录状态，需要同过cooki和session来保持会话，现在http2可以正式使用了，他和http1.1的不同有

1.数据是通过二进制传输，不是文本形式

2.多路复用，建立连接后可以一次发送多个http请求

3.压缩http headers,减少负载

4.支持server push