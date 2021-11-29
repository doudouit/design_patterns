这是一个仿造netty的reactor模型的程序 程序架构图如img.png所示

这里不做关于IO 和 业务的事情


v1：<br>
完成架构图的左半部分
只有一个线程负责accept，每个都会被分配client，进行R/W

v2: <br>
完成架构图的左半部分
只有一个线程负责appept, 其他线程负责接收client，进行R/W

v3:<br>
分组 
boss负责接收连接
worker负责R/W
