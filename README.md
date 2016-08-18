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
而另一方面，这个demo中使用到的网络框架是NoHttp，我没有上传相关的后端文件，通过网络请求返回的JSON数据如下：<br/>

                {
    "comment": [
        {
            "id": "15",
            "comment": "就是我",
            "user_id": "1",
            "for_id": "24",
            "date": "2016-08-17 14:12:28",
            "from_name": "qqprp",
            "tx": "20160817_145224.jpg",
            "for_content": "bhhggg,陌路up95级图片推荐即使具体啦咯啦咯啦咯啦咯啦咯啦咯啦咯啦啦咯啦咯啦咯啦咯啦咯啦咯啦咯陌陌摸摸摸摸哦哦弄哦哦哦默默无语我摸"
        },
        {
            "id": "14",
            "comment": "哦哦哦",
            "user_id": "1",
            "for_id": "24",
            "date": "2016-08-17 14:08:03",
            "from_name": "qqprp",
            "tx": "20160817_145224.jpg",
            "for_content": "bhhggg,陌路up95级图片推荐即使具体啦咯啦咯啦咯啦咯啦咯啦咯啦咯啦啦咯啦咯啦咯啦咯啦咯啦咯啦咯陌陌摸摸摸摸哦哦弄哦哦哦默默无语我摸"
        },
        ...
    ],
    "success": 0
        }

你们可以根据相应的格式添加数据。
## 异步加载--AsyncTask
关于AsyncTask就不在过多描述，很常见的异步加载。
## 加载的图片放入缓存，或从缓存中读取图片--LruCache

                private LruCache<String, Bitmap> mcache;

	public ImageLoaderUtil() {
		int maxMemory = (int) Runtime.getRuntime().maxMemory();
		int cacheSize = maxMemory / 8;
		mcache = new LruCache<String, Bitmap>(cacheSize) {
			@SuppressLint("NewApi")
			@Override
			protected int sizeOf(String key, Bitmap value) {
				return value.getByteCount();
			}
		};
	}

	public void addBitmapToCache(String url, Bitmap bitmap) {
		if (getBitmapFromCache(url) == null) {
			mcache.put(url, bitmap);
		}
	}

	public Bitmap getBitmapFromCache(String url) {
		return mcache.get(url);

	}
## 判断手指滑动状态

这个很简单，直接使用ListView或RecyclerView的滑动监听即可。

## 综上，我们异步加载的步骤是：

首先判断在LruCache中通过url判断是否有欲加载的图片，没有的话从网络上加载，并使用url和图片的键值对放入LruCache中。同时监听手机滑动，当收停止滑动的时候在进行加载，滑动过程中不进行任何加载。

        
