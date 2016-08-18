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
        {
            "id": "13",
            "comment": "舅舅",
            "user_id": "1",
            "for_id": "24",
            "date": "2016-08-17 14:00:36",
            "from_name": "qqprp",
            "tx": "20160817_145224.jpg",
            "for_content": "bhhggg,陌路up95级图片推荐即使具体啦咯啦咯啦咯啦咯啦咯啦咯啦咯啦啦咯啦咯啦咯啦咯啦咯啦咯啦咯陌陌摸摸摸摸哦哦弄哦哦哦默默无语我摸"
        },
        {
            "id": "12",
            "comment": "哈哈",
            "user_id": "1",
            "for_id": "24",
            "date": "2016-08-16 15:35:14",
            "from_name": "qqprp",
            "tx": "20160817_145224.jpg",
            "for_content": "bhhggg,陌路up95级图片推荐即使具体啦咯啦咯啦咯啦咯啦咯啦咯啦咯啦啦咯啦咯啦咯啦咯啦咯啦咯啦咯陌陌摸摸摸摸哦哦弄哦哦哦默默无语我摸"
        },
        {
            "id": "11",
            "comment": "墨迹就",
            "user_id": "56",
            "for_id": "24",
            "date": "2016-08-08 16:52:40",
            "from_name": "好的吧",
            "tx": "20160808_110209.jpg",
            "for_content": "bhhggg,陌路up95级图片推荐即使具体啦咯啦咯啦咯啦咯啦咯啦咯啦咯啦啦咯啦咯啦咯啦咯啦咯啦咯啦咯陌陌摸摸摸摸哦哦弄哦哦哦默默无语我摸"
        },
        {
            "id": "10",
            "comment": "dfggg",
            "user_id": "1",
            "for_id": "22",
            "date": "2016-08-02 09:38:36",
            "from_name": "qqprp",
            "tx": "20160817_145224.jpg",
            "for_content": "今天"
        },
        {
            "id": "9",
            "comment": "你就会",
            "user_id": "1",
            "for_id": "22",
            "date": "2016-08-01 09:04:38",
            "from_name": "qqprp",
            "tx": "20160817_145224.jpg",
            "for_content": "今天"
        },
        {
            "id": "8",
            "comment": "OK了里好",
            "user_id": "1",
            "for_id": "21",
            "date": "2016-07-29 16:23:33",
            "from_name": "qqprp",
            "tx": "20160817_145224.jpg",
            "for_content": "Ggfdd"
        },
        {
            "id": "7",
            "comment": "哦KTV",
            "user_id": "1",
            "for_id": "21",
            "date": "2016-07-28 10:32:49",
            "from_name": "qqprp",
            "tx": "20160817_145224.jpg",
            "for_content": "Ggfdd"
        },
        {
            "id": "6",
            "comment": "就好",
            "user_id": "1",
            "for_id": "22",
            "date": "2016-07-28 10:12:59",
            "from_name": "qqprp",
            "tx": "20160817_145224.jpg",
            "for_content": "今天"
        },
        {
            "id": "5",
            "comment": "哦",
            "user_id": "1",
            "for_id": "22",
            "date": "2016-07-28 10:06:26",
            "from_name": "qqprp",
            "tx": "20160817_145224.jpg",
            "for_content": "今天"
        },
        {
            "id": "4",
            "comment": "OK了哭泣",
            "user_id": "1",
            "for_id": "22",
            "date": "2016-07-28 09:28:41",
            "from_name": "qqprp",
            "tx": "20160817_145224.jpg",
            "for_content": "今天"
        },
        {
            "id": "3",
            "comment": "哦哦就是个",
            "user_id": "1",
            "for_id": "22",
            "date": "2016-07-27 15:56:52",
            "from_name": "qqprp",
            "tx": "20160817_145224.jpg",
            "for_content": "今天"
        },
        {
            "id": "2",
            "comment": "分开",
            "user_id": "1",
            "for_id": "22",
            "date": "2016-07-27 15:29:33",
            "from_name": "qqprp",
            "tx": "20160817_145224.jpg",
            "for_content": "今天"
        },
        {
            "id": "1",
            "comment": "阿里啦咯啦",
            "user_id": "1",
            "for_id": "22",
            "date": "2016-07-27 15:24:38",
            "from_name": "qqprp",
            "tx": "20160817_145224.jpg",
            "for_content": "今天"
        }
    ],
    "success": 0
}
