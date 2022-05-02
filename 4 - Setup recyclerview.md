#Main Screen

## Mục tiêu
  - `Tạo 1 recyclerview để hiện thị dữ liệu trả về từ api`
  - `Biết cách cài đặt, sử dụng recyclerview cơ bản, TextView, thư viện Glide`
  - `Tổng quan về mô hình MVVM, ViewModel, LiveData`
  - `Sử dụng viewBinding cơ bản`
#### Bước 1: Thêm các thư viện cần thiết vào dự án 
###### Thực hiện theo các bước như ở bài hướng dẫn trước để thêm thư viện vào dự án
```pro
plugins {
    ...
    id 'org.jetbrains.kotlin.plugin.serialization' version '1.6.21'
}
```
```groovy
dependencies {
    ...
    implementation "androidx.recyclerview:recyclerview:1.2.1"

    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.2")

    implementation 'com.github.bumptech.glide:glide:4.13.0'
}
```
##### Enable viewBinding
```groovy
buildFeatures {
        viewBinding true
    }
```

#### Bước 2: Thêm recyclerview vào file layout
###### Mở file activity_main.xml trong thư mục layout/ và chỉnh sửa giống như sau:
```xml 
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".ui.main.MainActivity">

    <androidx.recyclerview.widget.RecyclerView
        android:id="@+id/main_recyclerview"
        android:layout_width="0dp"
        android:layout_height="0dp"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent"
        tools:listitem="@layout/item_recyclerview" />
</androidx.constraintlayout.widget.ConstraintLayout>
```
###### Note: Phần cần ghi nhớ ở file này bao gồm
```xml 
android:id="@+id/main_recyclerview" 
``` 
là id của recyclerview
  ```xml
        android:layout_width="0dp"
        android:layout_height="0dp"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent"
  ``` 
  Vì đang sử dụng constraint layout nên sẽ cần 4 dòng cuối để xác định vị trí của recyclerview </br>
  Recyclerview sẽ full màn hình nên height và width sẽ là match_parent nhưng ở constraint layout nên sẽ là 0dp
  
  #### Bước 3: Tạo Adapter, ViewHolder cho recyclerview
  ###### Để hiển thị một list các item trên một recyclerview, chúng ta sẽ viết adapter kế thừa từ ListAdapter và nó trông thế này:
  ```kotlin
  class PostAdapter : ListAdapter<Post, PostViewHolder>(PostDiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        return PostViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}
  ```
  ###### Một cách đơn giản adapter chính là 1 thứ mà có thể giúp cho recyclerview biết nó sẽ hiển thị gì và hiển thị như nào.
  
  Có 2 phương thức cần thiết trong 1 ListAdapter là onCreateViewHolder và onBindViewHolder
  - onBindViewHolder sẽ là nơi đưa dữ liệu của từng item cho viewholder thông qua ``` getItem(position) ```
 - onCreateViewHolder sẽ giúp adapter hiểu nó sẽ cần 1 viewholder như nào. Nhưng khoan viewholder là cái gì???
 - ViewHolder là 1 thứ luôn đi kèm cùng adapter, đơn giản nó giúp adapter giữ các view trong quá trình hoạt động O_o 
  
  ###### Tạo file ViewHolder cho Adapter
  ViewHolder được recyclerview cho sẵn chúng ta chỉ cần kế thừa nó là xong.
  ```kotlin
  class PostViewHolder private constructor(private val binding: ItemRecyclerviewBinding) :
    RecyclerView.ViewHolder(binding.root) {
    companion object {
        fun from(parent: ViewGroup): PostViewHolder {
            val binding =
                ItemRecyclerviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return PostViewHolder(binding)
        }
    }

    fun bind(post: Post) {}

}
  ```
 - function ` from ` để giấu các logic ko cần thiết khi khởi tạo viewholder mà không cần dùng constructor
 - function ` bind ` để truyền data vào view để hiện thị ở từng item
 ###### Vì List Adapter cần 1 DiffUtil để khi dữ liệu thay đổi, item nào đã có thì sẽ giữ nguyên, chưa có thì sẽ thêm vào, tránh trường hợp giật lag khi sử dụng.
 
 ```kotlin
 class PostDiffUtil : DiffUtil.ItemCallback<Post>() {
    override fun areItemsTheSame(oldItem: Post, newItem: Post) = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Post, newItem: Post) = oldItem == newItem
}
 ```
 
#### Bước 4: Tạo ViewModel cho MainActivity
Trước hết chúng ta cần biết ViewModel là gì.

###### ViewModel là gì ???
 `ViewModel` là một class có trách nhiệm chuẩn bị và quản lý dữ liệu cho một UI component (có thể là Activity hoặc Fragment). </br>
 Nó cũng cung cấp cách để dễ dàng giao tiếp giữa activity và fragment hoặc giữa các fragment với nhau.
 `ViewModel` luôn được tạo trong cùng một phạm vi (một fragment hoặc một activity) và sẽ được giữ lại cho đến khi phạm vi đó còn "sống".  </br>
 Hay nói cách khác là ViewModel sẽ không bị destroyed khi activity hoặc fragment của nó bị destroyed bởi một configuration change (ví dụ như việc xoay màn hình).</br>
 Instance mới của các owner này sẽ chỉ kết nối lại với ViewModel hiện có của nó.

###### LiveData là gì ???
LiveData là một lớp giữ dữ liệu quan sát được.</br>
LiveData nó tôn trọng vòng đời của các thành phần ứng dụng khác, chẳng hạn như các activities, fragments hoặc services.
Nhận thức này đảm bảo LiveData chỉ cập nhật các thành phần ứng dụng quan sát nó khi những thành phần này đang ở trạng thái hoạt động.

Đã rõ, hãy cùng xem 1 ViewModel cơ bản sẽ trông như nào:

```kotlin
class MainViewModel : ViewModel() {
    private val _response = MutableLiveData<PostsResponse>()
    val response: LiveData<PostsResponse> get() = _response

    fun getPosts() {
     ...
    }
}
```

Khai báo 1 biến `_response` có kiểu dữ liệu là `MutableLiveData<PostsResponse>`  để chứa dữ liệu chỉ dùng trong viewmodel
```kotlin
  private val _response = MutableLiveData<PostsResponse>()
```
Khai báo biến `response` có kiểu là ` LiveData<PostsResponse>` được lấy từ `_response` để sử dụng được từ bên ngoài
```kotlin
  val response: LiveData<PostsResponse> get() = _response
```

function `fun getPosts() {
     ...
    }`
    dùng để lấy dữ liệu từ api, sẽ thay đổi ở cái bài sau

#### Bước 5: Setup recyclerview tại MainActivity
Cùng xem MainActivity có gì nào

```kotlin
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val postAdapter = PostAdapter()

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        viewModel.response.observe(this) { postResponse ->
            postAdapter.submitList(postResponse.response.posts)
        }

        with(binding.mainRecyclerview) {
            layoutManager = LinearLayoutManager(this@MainActivity)
            setHasFixedSize(true)
            adapter = postAdapter
        }
    }
}
```

Khai báo và khởi tạo biến binding(thay thế cho findViewById)
```kotlin
  private lateinit var binding: ActivityMainBinding
  
  binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
```

Khai báo và khởi tạo Adapter
```kotlin
 private val postAdapter = PostAdapter()
```

Khai báo và khởi tạo ViewModel
```kotlin
  private lateinit var viewModel: MainViewModel
  
  viewModel = ViewModelProvider(this)[MainViewModel::class.java]
```

Setup recyclerview như layoutManager, và gán adapter đã khởi tạo cho recycerview
```kotlin
  with(binding.mainRecyclerview) {
            layoutManager = LinearLayoutManager(this@MainActivity)
            setHasFixedSize(true)
            adapter = postAdapter
        }
```

Lắng nghe dữ tiệu từ viewModdel, khi dữ liệu thay đổi thì sẽ gọi adapter để đưa dữ liệu cho adapter
```kotlin
viewModel.response.observe(this) { postResponse ->
            postAdapter.submitList(postResponse.response.posts)
        }
```

#### Sử dụng Glide cơ bản
Ví dụ như load ảnh cho image trong item recyclerview

```kotlin
Glide.with(binding.image.context).load(photo.originalSize.url).into(binding.image)
```




  

