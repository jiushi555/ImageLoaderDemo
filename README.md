# ImageLoaderDemo
网络图片异步加载
其实有关图片加载存在这样一个问题，图片的下载始终是一个耗时的操作，这个时候如果把图片加载放在主线程中话的是不明智的，模拟一个这样的场景，
假如在一个listview或RecyclerView中，每一个listitem中都有一张网络图片，假如不使用网络异步处理的话，滑动工作会特别卡，因为必须加载网图片后
，才会加载下个item。除了这些，网络图片加载还存在很多优化，下面是ImageLoader对于网络图片加载的优化内容：
* 加载过程使用异步，这里使用AsyncTask
* 加载图片后，将图片放入缓存，不必每次都从网络上请求，节省流量，加快加载速度
* 使用ViewHolder，这点就不必多说了
* 监听滑动过程，其实我们知道的，在滑动的时候其实我们是没有必要加载图片的，等滑动结束的时候，加载当前页面的图片

其实网上有很多类似的开源框架，但是为了理解其中原理，我自己写了一个ImageLoader。同时在加载过程中我自己写了一个ProgressDialog，显示效果如下<br/>
![](https://github.com/jiushi555/ImageLoaderDemo/raw/master/ImageLoaderDemo/load.png)<br/>
而另一方面，这个demo中使用到的网络框架是NoHttp，我没有上传相关的后端文件，通过网络请求返回的JSON数据如下：
        
