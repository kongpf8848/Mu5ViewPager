# Mu5ViewPager
根据图片高度动态变化的ViewPager,kotlin+androidx实现，基于[JmStefanAndroid/Mu5ViewPager](https://github.com/JmStefanAndroid/Mu5ViewPager)


# UI

![Mu5ViewPager](/gif/Mu5ViewPager.gif)

# Demo 
[Download](https://fir.im/vkx6)

# Gradle

	dependencies {
		compile 'com.github.JmStefanAndroid:Mu5ViewPager:1.2'
	}
 
 # 使用 
 
 ### 1.xml中
            <me.stefan.library.mu5viewpager.Mu5ViewPager
                android:id="@+id/viewpager"
                android:layout_width="match_parent"
                android:layout_height="wrap_content" />
                
 ### 2.实现接口 Mu5Interface
 ```
class MainActivity : AppCompatActivity(), Mu5Interface {

    private val handler = Handler(Looper.getMainLooper())
    private val datas = arrayOf(
            "http://imgsrc.baidu.com/imgad/pic/item/241f95cad1c8a7860ea6962d6d09c93d70cf5001.jpg",
            "http://t9.baidu.com/it/u=830624624,301814668&fm=79&app=86&f=JPEG?w=1280&h=854",
            "http://t8.baidu.com/it/u=1484500186,1503043093&fm=79&app=86&f=JPEG?w=1280&h=853",
            "http://t8.baidu.com/it/u=705696793,503581032&fm=79&app=86&f=JPEG?w=1000&h=1474",
            "http://t9.baidu.com/it/u=2266751744,4253267866&fm=79&app=86&f=JPEG?w=1280&h=854",
            "http://t7.baidu.com/it/u=4050467064,3961079979&fm=79&app=86&f=JPEG?w=1280&h=1920",
            "http://t9.baidu.com/it/u=2268908537,2815455140&fm=79&app=86&f=JPEG?w=1280&h=719",
            "http://t7.baidu.com/it/u=3225540498,2642373837&fm=79&app=86&f=JPEG?w=1162&h=1800",
            "http://t8.baidu.com/it/u=198337120,441348595&fm=79&app=86&f=JPEG?w=1280&h=732"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //设置图片最大高度
        viewpager.setMaxHeight(Utils.getDisplayHeight(this))
        //设置图片数据
        viewpager.setData(datas, this)
        onPageSelected(0)
    }

    //滑动切换图片
    override fun onPageSelected(position: Int) {
        tv_index.text = getString(R.string.index_str, position + 1, datas.size)
    }

    //点击图片事件
    override fun onClick(url: String, position: Int) {
        Toast.makeText(this, "position:$position,url:$url", Toast.LENGTH_SHORT).show()
    }

    //加载图片资源
    override fun onLoadImage(imageView: ImageView, url: String, position: Int) {
        //此处使用Glide加载图片，可以替换成任意图片加载类库
        Glide.with(imageView.context)
                .asBitmap()
                .load(url)
                .listener(object : RequestListener<Bitmap> {
                    override fun onLoadFailed(e: GlideException?, model: Any, target: Target<Bitmap>, isFirstResource: Boolean): Boolean {
                        return false
                    }

                    override fun onResourceReady(resource: Bitmap, model: Any, target: Target<Bitmap>, dataSource: DataSource, isFirstResource: Boolean): Boolean {
                        handler.post {
                            viewpager.bindSource(resource, position, imageView)
                        }
                        return false
                    }
                }).submit()
    }
}
```  

