一个client对应一个io线程    
可配置参数
```angular2html
# 服务端线程数
NioEventLoopGroup boss = new NioEventLoopGroup(10);

# 客户端连接数
int poolSize = 1;
```

* io线程与执行线程相同   
```angular2html
ctx.executor().execute(() -> {

});


client over msg: io thread: nioEventLoopGroup-2-7exec Thread: nioEventLoopGroup-2-7 from args: hello18, src arg: hello18
client over msg: io thread: nioEventLoopGroup-2-4exec Thread: nioEventLoopGroup-2-4 from args: hello17, src arg: hello17
client over msg: io thread: nioEventLoopGroup-2-6exec Thread: nioEventLoopGroup-2-6 from args: hello2, src arg: hello2
```


* io线程与执行线程不同
```angular2html
ctx.executor().parent().next().execute(() -> {

});

client over msg: io thread: nioEventLoopGroup-2-2exec Thread: nioEventLoopGroup-2-2 from args: hello2, src arg: hello2
client over msg: io thread: nioEventLoopGroup-2-2exec Thread: nioEventLoopGroup-2-1 from args: hello3, src arg: hello3
client over msg: io thread: nioEventLoopGroup-2-2exec Thread: nioEventLoopGroup-2-2 from args: hello11, src arg: hello11
```
