# Jetpack

![img](https://i.loli.net/2021/07/20/OM42k9NuhpxfUTG.png)



## 1.什么是Jetpack

***Jetpack就是Google官方推出的一套方便开发者的库。***



![image-20210719103100675](https://i.loli.net/2021/07/20/wnpGKRFxuNUavQb.png)

![image-20210719103833419](https://i.loli.net/2021/07/20/rIj2SoRZPzUg185.png)

其致力于

- 遵循最佳做法

  ![image-20210719103958239](https://i.loli.net/2021/07/20/9ivqUOHfAy8nZY1.png)

  https://developer.android.google.cn/stories/apps/iheartradio

- 减少样板代码

  ![image-20210719104046266](https://i.loli.net/2021/07/20/C2k1R8xUsDmq5LN.png)

  https://developer.android.google.cn/stories/apps/monzo-camerax

- 减少不一致

  ![image-20210719104130206](https://i.loli.net/2021/07/20/XmBs82ZTqAdejbI.png)

  https://developer.android.google.cn/jetpack/testimonials

  > 总之不管男女老少用过都说好



## 2.在项目中引入Jetpack

没想到吧，当我们创建App的时候build.gradle已经为我们添加了Jetpack的支持。

![](https://i.loli.net/2021/07/20/KAIGsouOpckCERy.png)



在引入google()之后便可以在dependences添加对应的Jetpack组件了。比如LiveData，Lifecycle，ViewModel，Navigation......

![image-20210719104850659](https://i.loli.net/2021/07/20/umPeiCO8lTVf4W6.png)



.....然后就是一番感人的调包环节了。



## 3.细化Jetpack组件的使用

- LiveData
- ViewModel
- Lifecycle
- Room
- Navigation
- DataBinding/ViewBindingg
- Dagger2/Hilt

### ViewModel

#### 1.什么是ViewModel

![image-20210719110127880](https://i.loli.net/2021/07/20/XkwNftWE4epUzJL.png)

> 借用google的一句话就是缓存数据的，当我的Activity发生配置变化时候会重新调用onCreate方法创建新的一个Activity的实例。这会导致屏幕内的
>
> 数据丢失。这很不符合用户预想中的使用，所以通常情况下我们会通过 `onSaveInstanceState()`来保存并拯救丢失的数据。但是`onSaveInstanceState()`只可以序列化再反序列化的少量数据，而不适合数量可能较大的数据，所以它不太适合存储整个页面的数据。所以就有了ViewModel，**但ViewModel并不是`onSaveInstanceState()`的替代品**。



#### 2.ViewModel的初步使用

> Code
>
> Tips：代码在
>
> com/example/viewmodeldemo/MainActivity.kt，
>
> com/example/viewmodeldemo/ui/activity/vm/MainViewModel.kt
>
> 文件中

step1 创建ViewMode

```kotlin
package com.example.viewmodeldemo

import androidx.lifecycle.ViewModel

/**
 *@author ZhiQiang Tu
 *@time 2021/7/19  11:18
 *@signature 我将追寻并获取我想要的答案
 */

class MainViewModel : ViewModel(){
    var number:Long = 0
        private set
    fun plusOne(){
        number++
    }
    fun plusTwo(){
        number+=2
    }
}
```

step2 在视图组件(Activity,Fragment,...)中获取ViewModel的实例

```kotlin
package com.example.viewmodeldemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    //懒加载ViewModel的实例
    private val viewModel:MainViewModel by lazy { ViewModelProvider(this,ViewModelProvider.NewInstanceFactory()).get(MainViewModel::class.java)}
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()
        //这种写法可能会出现空指针，建议使用ViewBinding/DataBinding
        plus_one.setOnClickListener {
            viewModel.plusOne()
            updateTextView()
        }
        plus_two.setOnClickListener {
            viewModel.plusTwo()
            updateTextView()
        }
    }
	//更新视图
    private fun updateTextView() {
        show_number.text = viewModel.number.toString()
    }
	//初始化视图
    private fun initView() {
        updateTextView()
    }

}
```



#### 3.ViewModel的进一步探究



##### 1.ViewModel的实现原理

这个还没想好。。

##### 2.ViewModel的生命周期

![image-20210719172024686](https://i.loli.net/2021/07/20/FSKdxCNgID9f2sO.png)

![image-20210719173906083](https://i.loli.net/2021/07/20/Mynz27C9Ku8BfRs.png)

![image-20210719174151805](https://i.loli.net/2021/07/20/nkCA96upUvZgoR2.png)

也就是说ViewModel的生命周期依托于ViewModelProvider传入的lifecycle参数，而lifecycle接口又由Activity，Fragment等实现，故ViewModel会和视图组件间接性的形成联系。并且在lifecycle组件第一次调用onCreate后初始化VIewModel（在之后的配置变化如屏幕旋转等都不会再次创建ViewModel），等到lifecycle组件调用了onDestroy方法，并且彻底凉透了，ViewModel才会调用onCleared释放内存。

总体来说

- ***ViewModel依赖与Lifecycle组件。在Lifecycle组件利用ViewModelProvider创建ViewModel实例的时候建立联系，并在Lifecycle组件第一次调用onCreate时候创建ViewModel，在Lifecycle组件彻底凉透了再释放ViewModel内存。***
- ***ViewModel的生命周期长于Activity。我们不能让ViewModel持有Lifecycle组件。否者会发生内存泄漏。***

##### 3.ViewModel的种类

- 普通ViewModel

  略

- AndroidViewModel

  > ***这是一个具有application的ViewModel除此之外与其他的ViewModel并没有什么不同***（这个AndriodViewModel并***不是***说生命周期绑定的application，它生命周期绑定的***还是***this（lifecycle），***只是构造函数中被传入了一个application参数。***）

  

  > Code
  >
  > Tipes：
  >
  > 代码在
  >
  > com/example/viewmodeldemo/ui/activity/vm/DemoAndroidViewModel.kt，
  >
  > com/example/viewmodeldemo/ui/activity/presentation/AndroidViewModelActivity.kt
  >
  > 文件中

  *step1 创建ViewModel*

  ```kotlin
  package com.example.viewmodeldemo.vm
  
  import android.app.Application
  import androidx.lifecycle.AndroidViewModel
  
  /**
   *@author ZhiQiang Tu
   *@time 2021/7/19  21:06
   *@signature 我们不明前路，却已在路上
   */
  class DemoAndroidViewModel(application: Application) : AndroidViewModel(application) {
      val mApplication = getApplication<Application>()
      
  }
  ```

  *step2 实例化AndroidViewModel*

  ```kotlin
  class AndroidViewModelActivity : AppCompatActivity() {
      private val viewModel:DemoAndroidViewModel by lazy { ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory(application)).get(DemoAndroidViewModel::class.java) }
  
      override fun onCreate(savedInstanceState: Bundle?) {
          super.onCreate(savedInstanceState)
          setContentView(R.layout.activity_android_view_model)
      }
  }
  ```

  

- SharedViewModel

> SharedViewModel实现了**同Activity上的Fragment之间**的数据共享。
>
> 这就是一个以Activity为Lifecycle的Fragment的ViewModel。
>
> 有点绕，也就是说这个ViewModel是给Fragment用的但是创建ViewModelProvider用的ViewModelStoreOwner却是Fragment所Attach的activity。
>
> 因为只有将ViewModelStoreOwner变成activity才能实现fragment间数据的共享。
>
> ```java
> ViewModelProvider(@NonNull ViewModelStoreOwner owner, @NonNull Factory factory)
> ```



> Code 
>
> Tips：代码在
>
> com/example/viewmodeldemo/ui/activity/presentation/SharedViewModelActivity.kt，
>
> com/example/viewmodeldemo/ui/fragment/presentation
>
> 文件中

step1 创建ViewModel

```kotlin
package com.example.viewmodeldemo.ui.fragment.vm

import androidx.lifecycle.ViewModel
import com.example.viewmodeldemo.ui.fragment.model.DemoData

/**
 *@author ZhiQiang Tu
 *@time 2021/7/19  21:46
 *@signature 我们不明前路，却已在路上
 */
class SharedViewModel: ViewModel() {
    //测试
    var data:DemoData = DemoData(0,"data")
}
```

step2 创建Fragment并实例化ViewModel

```kotlin
package com.example.viewmodeldemo.ui.fragment.presentation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.viewmodeldemo.R
import com.example.viewmodeldemo.ui.fragment.vm.SharedViewModel
import kotlinx.android.synthetic.main.fragment_demo01.*

private const val TAG = "DemoFragment01"

class DemoFragment01 : Fragment() {
    private val viewModel by lazy { ViewModelProvider(requireActivity()).get(SharedViewModel::class.java) }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_demo01, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        textView.text = "当前viewModel为$viewModel"
        textView2.text = "当前data为${viewModel.data}"
        Log.e(TAG, "当前viewModel为$viewModel" )
        Log.e(TAG,  "当前data为${viewModel.data}")
    }

}
```

```kotlin
package com.example.viewmodeldemo.ui.fragment.presentation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.viewmodeldemo.R
import com.example.viewmodeldemo.ui.fragment.vm.SharedViewModel
import kotlinx.android.synthetic.main.fragment_demo02.*
import kotlin.math.log

private const val TAG = "DemoFragment02"

class DemoFragment02 : Fragment() {
    private val viewModel by lazy { ViewModelProvider(requireActivity()).get(SharedViewModel::class.java) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_demo02, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        textView4.text = "当前viewModel为$viewModel"
        textView5.text = "当前data为${viewModel.data}"
        Log.e(TAG, "当前viewModel为$viewModel" )
        Log.e(TAG,  "当前data为${viewModel.data}")
    }
    
}
```

- 自定义构造器的ViewModel

  > 从之前的几种ViewModel中我们可以分成两类：
  >
  > - 一类是默认构造含函数的ViewModel比如`SharedViewModel`，最基础的ViewModel。
  > - 另外一就是非默认构造函数的ViewModel比如AndroidViewModel。

  

  ***那如何创建一个自定义的构造函数的ViewModel呢？***

  那不简单，这样嘛

  ```kotlin
  class MyViewModel(val myData:Data):ViewModel()	
  ```

  我竟无法反驳。

  值得注意的是当我们创建一个ViewModel的时候是利用的ViewModelProvider创建的，不是直接`MyViewModel(myData)`这样new出来，所以上述的方法貌似没什么用。

  回归ViewModelProvider上看看

  ```java
  public ViewModelProvider(@NonNull ViewModelStoreOwner owner, @NonNull Factory factory) {
      this(owner.getViewModelStore(), factory);
  }
  ```

你发现了什么，他有一个构造方法需要传入两个参数，一个owner一个`Factory`，哦所以自定义的构造函数的参数的传递需要靠这玩意了呗。

> Code
>
> Tips：代码在
>
> com/example/viewmodeldemo/ui/activity/vm/CustomViewModel.kt，
>
> com/example/viewmodeldemo/ui/activity/presentation/CustomFactoryViewModelActivity.kt
>
> 文件中

step1 创建ViewModel

```kotlin
package com.example.viewmodeldemo.ui.activity.vm

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.viewmodeldemo.ui.fragment.model.DemoData

/**
 *@author ZhiQiang Tu
 *@time 2021/7/20  7:34
 *@signature 我们不明前路，却已在路上
 */
private const val TAG = "CustomViewModel"
class CustomViewModel(var data: DemoData) : ViewModel(){
    fun logData(){
        //检测data是否真的被传入了
        Log.e(TAG, "$data")
    }
}
```

step2 自定义Factory

```kotlin
class CustomFactory:ViewModelProvider.Factory{
    //这个方法是ViewModel内部调用创建ViewModel实例的，所以它的任务就只是返回一个ViewModel,你怎么返回它并不关心。
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        val data = DemoData(0,"data")
        val customCustomViewModel = CustomViewModel(data)
        return customCustomViewModel as T
    }

}
```

step3 在owner组件中初始化ViewModel实例

```kotlin
package com.example.viewmodeldemo.ui.activity.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.viewmodeldemo.R
import com.example.viewmodeldemo.ui.activity.vm.CustomFactory
import com.example.viewmodeldemo.ui.activity.vm.CustomViewModel

class CustomFactoryViewModelActivity : AppCompatActivity() {
    private val viewModel by lazy { ViewModelProvider(this,CustomFactory()).get(CustomViewModel::class.java) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_custom_factory_view_model)
        viewModel.logData()
    }
}
```

注意这个factory必须要传入的哦，不传入就会这样。

![image-20210720082231895](https://gitee.com/False_Mask/jetpack-demos-pics/blob/master/PicsAndGifs/image-20210720082231895.png)



> Caused by: java.lang.InstantiationException: java.lang.Class<com.example.viewmodeldemo.ui.activity.vm.CustomViewModel> ***has no zero argument constructor***

不传入默认就是无参构造函数，

![image-20210720082428345](https://i.loli.net/2021/07/20/XT9WwpM581BDlRJ.png)

内部通过使用get函数里面的class参数进行反射创建ViewModel。

```kotlin
private val viewModel by lazy { ViewModelProvider(this).get(CustomViewModel::class.java) }
```

而ViewModel并没有无参构造，这就直接crash了。



##### 4.ViewModel+Ktx扩展

看看下面几个ViewModel的初始化方法。

```kotlin
//1
private val viewModel: DemoAndroidViewModel by lazy { ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory(application)).get(DemoAndroidViewModel::class.java) }

//2
private val viewModel by lazy { ViewModelProvider(this).get(CustomViewModel::class.java) }

//3
private val viewModel by lazy { ViewModelProvider(requireActivity()).get(SharedViewModel::class.java) }
```

太长了对吧。而且这个初始化很模板化。就是

***ViewModelProvider(viewModel所联接的组件，factory).get(你所需要创建的ViewModel的class参数)***

为了简化viewModel的初始化，ktx有更为简单的扩展。

```groovy
def lifecycle_version = "2.4.0-alpha02"
// ViewModel
implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycle_version")

//ktx
implementation"androidx.activity:activity-ktx:1.2.2"
implementation"androidx.fragment:fragment-ktx:1.3.4"
```

***然后之前冗长的代码就变成了这样,简直爽翻天。***

```kotlin
//1
private val viewModel:DemoAndroidViewModel by viewModels()

//2
private val viewModel:CustomViewModel by viewModels { CustomFactory() }

//3
private val viewModel:SharedViewModel by activityViewModels()
```



##### 5.ViewModel失效了！

> 每当我们使用ViewModel的时候我们总是认为：ViewModel一定能帮我们在任何情况下保存好界面的数据，然而真实情况是这样的吗？

进行以下设置

- 打开设置面板

  <img src="https://i.loli.net/2021/07/20/CEunFmt3QZoj1lL.jpg" alt="Screenshot_20210720_091652_com.android.settings" style="zoom:25%;" />

- 选择开发者选项

  <img src="https://i.loli.net/2021/07/20/2O8EPevHzmnkIsl.jpg" alt="Screenshot_20210720_091727_com.android.settings" style="zoom:25%;" />

- 选择软件设置，勾选切入后台不保留activity

<img src="https://i.loli.net/2021/07/20/emA9dvPOUBlgs1W.jpg" alt="Screenshot_20210720_091557_com.android.settings" style="zoom:25%;" />

然后发生了很恐怖的事情，切入后台再回来，数据没了。

效果图（GIF）

<img src="https://i.loli.net/2021/07/20/v6OLbZWG2aNemUI.gif" alt="SVID_20210720_092410_1 00_00_00-00_00_30" style="zoom:25%;" />

这是为什么，螈来是当打开开发者设置***不保留后台进程***之后，切入后台之后，Activity会直接被系统鲨了，并且不调用任何生命周期方法。连带着ViewModel都挂了，所以数据没法保存。还记得最早的时候说的：***ViewModel不是onSaveInstanceState的替代品吗***？<img src="C:\Users\Fool\AppData\Roaming\Typora\typora-user-images\image-20210720095422311.png" alt="image-20210720095422311" style="zoom: 50%;" />

由于系统杀死Activity是不会调用任何什么周期方法的，但是这样会给用户造成“***这个软件是个垃圾***”的错觉。

所以在系统杀死Activity之前它留了一线生机。会调用onSaveInstanceState这回是你 恢复数据的希望。我们可以这样写。

> Code
>
> Tip：该代码在com/example/viewmodeldemo/MainActivity.kt中

重写onSaveInstanceState把需要存放的数据保存下来

```kotlin
override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("number", show_number.text.toString())
    }
```

然后再onCreate方法中判断一下savedInstanceState是不是空的。若不是空的就把数据取出来。

```kotlin
override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    savedInstanceState?.let {
        viewModel.number = (it.get("number") as String).toLong()
    }
    initView()
    setListener()
}
```

效果图（GIF）

![](https://gitee.com/False_Mask/jetpack-demos-pics/raw/master/PicsAndGifs/SVID_20210720_101730_1 00_00_00-00_00_30.gif)



除此之外我们还能使用ViewModel来实现。

不过还得加入一个依赖

```groovy
// Saved state module for ViewModel
implementation("androidx.lifecycle:lifecycle-viewmodel-savedstate:$lifecycle_version")
```

> Code
>
> Tips：代码位置
>
> com/example/viewmodeldemo/MainActivity.kt，
>
> com/example/viewmodeldemo/ui/activity/vm/MainViewModel.kt
>
> 还有一个需要提醒大家的是SavedStateHandle不适合把整个页面的数据都保存下来，它的定位是保存最为重要的***一小部分数据***与onSaveInstanceState的定位是一样的。
>
> 如下。
>
> ![](https://gitee.com/False_Mask/jetpack-demos-pics/raw/master/PicsAndGifs/image-20210720105135906.png)

对之前的MianViewModel***稍作修改***

```kotlin
package com.example.viewmodeldemo.ui.activity.vm

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

/**
 *@author ZhiQiang Tu
 *@time 2021/7/19  11:18
 *@signature 我将追寻并获取我想要的答案
 */
private val TAG = "MainViewModel"
class MainViewModel(private val savedStateHandle: SavedStateHandle) : ViewModel() {
    var number:Long = if (savedStateHandle.contains("number")) {
        savedStateHandle.get<Long>("number") !!
    }
    else {
        savedStateHandle.set("number",0)
        0
    }

    fun plusOne(){
        number++
        updateSavedStateHandle(number)
    }

    private fun updateSavedStateHandle(number: Long) {
        savedStateHandle.set("number",number)
    }

    fun plusTwo(){
        number+=2
        updateSavedStateHandle(number)
    }
}
```

实例化ViewModel

```kotlin
private val viewModel:MainViewModel by viewModels{ SavedStateViewModelFactory(application,this) }
```



##### 6.ViewModel的使用建议

> *❌* Don’t let ViewModels (and Presenters) know about Android framework classes
>
> 不要让ViewModel知晓Android的FrameWork，ViewModel只是用来写点逻辑代码和存数据的。
>
> ![](https://gitee.com/False_Mask/jetpack-demos-pics/raw/master/PicsAndGifs/image-20210720111534040.png)
>
> 我所读的文章告诉我要将ViewModel和FrameWork隔离，最好的办法就是在ViewModel中不要导入android.的包，（android.arch.除外）（也算是个好办法。雾......）





> *✅ Keep the logic in Activities and Fragments to a minimum*
>
> 尽量不要在Activity，Fragment中写逻辑代码。
>
> 这个在刚学Android的时候估计是非常常见的问题，一个Activity 六七百行人的写傻了。
>
> 也算是设计模式的一个运用了，在MVVM中推荐将业务逻辑代码写在ViewModel中（这个其实也会存在问题的，后面有讲）。





> *❌ Avoid references to Views in ViewModels.*
>
> 不要将页面组件（Activity，Fragment）放在ViewModel中，这个估计懂得都懂。ViewModel生命周期比Activity等owner要长一点，如果ViewModel持有Activity/Fragment等就会造成内存泄漏嘛。



> *✅ Instead of pushing data to the UI, let the UI observe changes to it.*
>
> 在页面View数据的更新上，不是让ViewModel持有View通过set把数据装载到UI上。而是让UI 观察ViewModel中的数据，当数据发生改变后自己更新。（观察者模式也算的上MVVM中很重要的一部分了。
>
> 这我得跟你谈谈LiveData的含金量了。
>
> ![](https://gitee.com/False_Mask/jetpack-demos-pics/raw/master/PicsAndGifs/image-20210720112250468.png)
>
> 也就是说要让ViewModel持有UI数据包裹的LiveData（不是MutableLiveData哦，LiveData更加符合面向对象的封装性，而且只要不是双向绑定UI根本不会有更改数据的行为存在的所以没必要暴露Mutable)最后在View去观察对应数据的变化。



> *✅ Distribute responsibilities, add a domain layer if needed.*
>
> 分配职责，在有需要的时候新加一个domain层。之前有提过在 ViewModel里面写逻辑代码是存在一定问题的。那就是----ViewModel膨胀。如果app的业务逻辑比较复杂，那么就会导致ViewModel内代码很多。所以很有必要对业务逻辑进行合理的分离。
>
> ![](https://gitee.com/False_Mask/jetpack-demos-pics/raw/master/PicsAndGifs/image-20210720144309262.png)
>
> 它给出了两种方法：
>
> - 将一些业务逻辑分配到presenter中去，该presenter和ViewModel有相同的作用域。并且和app的其他板块进行交互并且更新ViewModel中的LiveData。
> - 像Clean Architecture一样采取添加一个domain层。使得架构更加具有可测试性和可维护性。（Clean Architecture我不懂，真的，需要的自取 [Clean Architecture](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html)





> *✅ Add a data repository as the single-point entry to your data*
>
> 添加一个data repository，并且repository对数据的使用是单向的。
>
> 就和google推荐的架构差不多，repository对Model和Romote的联接是单向箭头。
>
> ![](https://gitee.com/False_Mask/jetpack-demos-pics/raw/master/PicsAndGifs/image-20210720145437775.png)
>
> 数据的获取路径它给出了3种
>
> ![](https://gitee.com/False_Mask/jetpack-demos-pics/raw/master/PicsAndGifs/image-20210720150037509.png)
>
> Remote和Local就不必说了，增添了一种In-memory cache（内存缓存）。说实话不是很了解，看看就好了。
>
> ***最后就是如果你有很多的并且差异很明显的数据，可以选择开辟更多的Repository.***



> *✅ Expose information about the state of your data using a wrapper or another LiveData.*
>
> ***通过包装的方法，或者额外添加一个LiveData来提供数据的状态、***
>
> 其中数据状态需要包含什么？
>
>  `MyDataState` could contain information about whether the data is currently loading, has loaded successfully or failed
>
> 状态包含数据是否正在加载，是否加载完成，是否失败等。
>
> - 通过添加额外的LiveData暴露数据状态
>
>   ![](https://gitee.com/False_Mask/jetpack-demos-pics/raw/master/PicsAndGifs/image-20210720150822729.png)
>
>   其中MyData是数据本身，MyDataState是数据加载的情况。
>
> - 通过装饰模式暴露数据的状态。
>
> ![](https://gitee.com/False_Mask/jetpack-demos-pics/raw/master/PicsAndGifs/image-20210720151201652.png)
>
> data就是数据本身，message是数据的状态信息（这写法不来由的想到了MVI架构中的ViewState）





> *✅ Design events as part of your state. For more details read* [LiveData with SnackBar, Navigation and other events (the SingleLiveEvent case)](https://medium.com/google-developers/livedata-with-snackbar-navigation-and-other-events-the-singleliveevent-case-ac2622673150)*.*
>
> 把事件当成状态的一部分。好像这样翻译起来太过绕口。
>
> ***我认为它想表达的意思是将Event进行封装。***
>
> 这个Event就是一些消费性事件，比如Snackbar弹窗，Toast，点击事件等这些不具有状态的消费性事件。
>
> ***为什么要把它封装起来封装以后又放在哪？***
>
> 1.我们知道presentation层（也就是Activity和Fragment）需要和ViewModel交互。
>
> 2.presentation层需要发送一个事件给ViewModel然后ViewModel处理这个事件。
>
> 3.比如一个登陆场景，用户点击了LoginActivity的Login Button，然后LoginViewModel接受到一个登陆事件，这个登陆事件里面包含了用户名，密码等配置信息。LoginViewModel根据从这个事件里面获取的用户名密码发送网络请求比对是否与服务器上的一致，最后更改对应的LiveData。最后Presentation观察到LiveData变化做出响应（如登陆成功，登陆失败。
>
> 4.当我们回首去看这个登陆流程的时候后我们会发现一个问题，这个登陆事件怎么处理？
>
> - 有些人会直接通过对button设置监听，当点击触发直接调用viewModel里面的方法把需要的参数直接传入进去。不是很推荐，主要是写法太过简洁，不能装*（虽然我找不出什么问题但总是感觉怪怪的。
>
> - 还有些人会在xml里面的onClick里面通过dataBinding的单项绑定直接调用viewModel对象的方法，不过好像和上面的方法并没有本质区别。也不是很推荐。
>
> - 推荐的方法式通过将这个LoginEvent封装，因为这样Testable（虽然我根本不懂啥是Test，并且逻辑更为清晰。
>
>   ```kotlin
>   data class LoginEvent(val username:String,val password:String)
>   ```
>
> 然后在需要的时候发送Event到ViewModel里，不过为了保证这个事件是1次性消费事件还得做一些处理，比如。
>
> 具体的我就不做多的演示了，这个内容得有两三篇博客那么长，况且我也没看太懂。有兴趣的可以自己看看。
>
> 
>
> SingleLiveEvent和Event确保事件为单次消费事件
>
> [LiveData with SnackBar, Navigation and other events (the SingleLiveEvent case)](https://medium.com/androiddevelopers/livedata-with-snackbar-navigation-and-other-events-the-singleliveevent-case-ac2622673150)
>
> 
>
> 利用协程Flow库确保事件为单次消费事件（2021最新解决方案
>
> [Android SingleLiveEvent Redux with Kotlin Flow](https://proandroiddev.com/android-singleliveevent-redux-with-kotlin-flow-b755c70bb055)
>
> 
>
> 思来想去还是把代码贴一下。

> **way1** 
>
> 通过继承MutableLiveData创建一个特殊的MutableLiveData。
>
> 这个SingleLiveEvent确保了事件为单次的消费性事件。
>
> ***但是存在线程不安全的问题。***
>
> ```kotlin
> /*
>  *  Copyright 2017 Google Inc.
>  *
>  *  Licensed under the Apache License, Version 2.0 (the "License");
>  *  you may not use this file except in compliance with the License.
>  *  You may obtain a copy of the License at
>  *
>  *      http://www.apache.org/licenses/LICENSE-2.0
>  *
>  *  Unless required by applicable law or agreed to in writing, software
>  *  distributed under the License is distributed on an "AS IS" BASIS,
>  *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
>  *  See the License for the specific language governing permissions and
>  *  limitations under the License.
>  */
> 
> package com.example.android.architecture.blueprints.todoapp;
> 
> import android.arch.lifecycle.LifecycleOwner;
> import android.arch.lifecycle.MutableLiveData;
> import android.arch.lifecycle.Observer;
> import android.support.annotation.MainThread;
> import android.support.annotation.Nullable;
> import android.util.Log;
> 
> import java.util.concurrent.atomic.AtomicBoolean;
> 
> /**
>  * A lifecycle-aware observable that sends only new updates after subscription, used for events like
>  * navigation and Snackbar messages.
>  * <p>
>  * This avoids a common problem with events: on configuration change (like rotation) an update
>  * can be emitted if the observer is active. This LiveData only calls the observable if there's an
>  * explicit call to setValue() or call().
>  * <p>
>  * Note that only one observer is going to be notified of changes.
>  */
> public class SingleLiveEvent<T> extends MutableLiveData<T> {
> 
>     private static final String TAG = "SingleLiveEvent";
> 
>     private final AtomicBoolean mPending = new AtomicBoolean(false);
> 
>     @MainThread
>     public void observe(LifecycleOwner owner, final Observer<T> observer) {
> 
>         if (hasActiveObservers()) {
>             Log.w(TAG, "Multiple observers registered but only one will be notified of changes.");
>         }
> 
>         // Observe the internal MutableLiveData
>         super.observe(owner, new Observer<T>() {
>             @Override
>             public void onChanged(@Nullable T t) {
>                 if (mPending.compareAndSet(true, false)) {
>                     observer.onChanged(t);
>                 }
>             }
>         });
>     }
> 
>     @MainThread
>     public void setValue(@Nullable T t) {
>         mPending.set(true);
>         super.setValue(t);
>     }
> 
>     /**
>      * Used for cases where T is Void, to make calls cleaner.
>      */
>     @MainThread
>     public void call() {
>         setValue(null);
>     }
> }
> ```
>
> way2 使用装饰器包裹一层。

> ```kotlin
> /**
>  * Used as a wrapper for data that is exposed via a LiveData that represents an event.
>  */
> open class Event<out T>(private val content: T) {
> 
>     var hasBeenHandled = false
>         private set // Allow external read but not write
> 
>     /**
>      * Returns the content and prevents its use again.
>      */
>     fun getContentIfNotHandled(): T? {
>         return if (hasBeenHandled) {
>             null
>         } else {
>             hasBeenHandled = true
>             content
>         }
>     }
> 
>     /**
>      * Returns the content, even if it's already been handled.
>      */
>     fun peekContent(): T = content
> }
> 
> ```
>
> way3 利用协程Flow
>
> 这个我没看。



#### 4.内容来源

> 什么是ViewModel，ViewModel的初步使用 ---- [Google官方文档](https://developer.android.google.cn/topic/libraries/architecture/viewmodel)，[Google官方文档提供的博客](https://medium.com/androiddevelopers/viewmodels-a-simple-example-ed5ac416317e)
>
> ![](https://gitee.com/False_Mask/jetpack-demos-pics/raw/master/PicsAndGifs/image-20210720165008322.png)
>
> <img src="https://gitee.com/False_Mask/jetpack-demos-pics/raw/master/PicsAndGifs/image-20210720164713562.png" style="zoom:25%;" />
>
> 
>
> 





> ViewModel + Ktx 扩展 ---- [Bilibili](https://www.bilibili.com/video/BV1PE411g7o7?from=search&seid=16004297047746133157)
>
> <img src="https://gitee.com/False_Mask/jetpack-demos-pics/raw/master/PicsAndGifs/image-20210720164531107.png" style="zoom:25%;" />



> ViewModel的生命周期 ---- [Google官方文档](https://developer.android.google.cn/topic/libraries/architecture/viewmodel#lifecycle)



> ViewModel失效了 ---- [Bilibili](https://www.bilibili.com/video/BV1H4411K7Be?from=search&seid=16004297047746133157)，[Google官方文档提供的博客](https://medium.com/androiddevelopers/viewmodels-persistence-onsaveinstancestate-restoring-ui-state-and-loaders-fc7cc4a6c090)
>
> <img src="https://gitee.com/False_Mask/jetpack-demos-pics/raw/master/PicsAndGifs/image-20210720164610227.png" style="zoom:25%;" />
>
> ![image-20210720164258899](https://gitee.com/False_Mask/jetpack-demos-pics/raw/master/PicsAndGifs/image-20210720165008322.png)
>
> <img src="https://gitee.com/False_Mask/jetpack-demos-pics/raw/master/PicsAndGifs/image-20210720164642765.png" style="zoom:25%;" />





> ***ViewModel的使用建议*** ---- [Google官方文档提供的博客](https://medium.com/androiddevelopers/viewmodels-and-livedata-patterns-antipatterns-21efaef74a54)
>
> ![](https://gitee.com/False_Mask/jetpack-demos-pics/raw/master/PicsAndGifs/image-20210720165008322.png)
>
> <img src="https://gitee.com/False_Mask/jetpack-demos-pics/raw/master/PicsAndGifs/image-20210720164940557.png" alt="image-20210720164940557" style="zoom:25%;" />
>
> 



### Navigation

> Navigation是用来处理
