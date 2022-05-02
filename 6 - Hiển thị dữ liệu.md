# Hiển thị dữ liệu lên màn hình

## Mục tiêu:

- Biết cách khởi tạo 1 coroutine để có thể lấy dữ liệu từ đối tượng `Client` (cụ thể là function `getPosts`)
- Gán dữ liệu cho `LiveData` để nó thông báo cho `Activity` là sẽ có data được truyền sang `RecyclerView`

#### Bước 1: Mở file MainViewModel.kt và chỉnh sửa function getPosts

- Trước tiên chúng ta sẽ tạo 1 coroutine để xử lý việc này bên trong function `getPosts`

```kotlin
class MainViewModel : ViewModel() {
    private val _response = MutableLiveData<PostsResponse>()
    val response: LiveData<PostsResponse> get() = _response

    init {
        getPosts()
    }

    private fun getPosts() {
        // Khởi tạo coroutine
        viewModelScope.launch {
            // Code
        }
    }
}
```

- Ta tiến hành lấy dữ liệu từ function `getPostResponse()` của đối tượng `Client`

```kotlin
class MainViewModel : ViewModel() {
    private val _response = MutableLiveData<PostsResponse>()
    val response: LiveData<PostsResponse> get() = _response

    init {
        getPosts()
    }

    private fun getPosts() {
        // Khởi tạo coroutine
        viewModelScope.launch {
            // Lấy dữ liệu
            val postsResponse = withContext(Dispatchers.IO) {
                Client.getPostResponse()
            }
        }
    }
}
```

- Phương thức `Client.getPostResponse()` được bọc bởi phường thức `withContext(Dispatchers.IO) {}` để nó chuyển đoạn
  code gọi dữ liệu từ mạng sang một luồng khác chuyên về những câu lệnh IO như mạng, database... Đảm bảo câu lệnh không
  làm app bị đơ khi được thực thi.

- Sau đó chúng ta gán dữ liệu đã lấy được vào `LiveData _response` để Activity có thể nhận được và cập nhật giao diện

```kotlin
class MainViewModel : ViewModel() {
    private val _response = MutableLiveData<PostsResponse>()
    val response: LiveData<PostsResponse> get() = _response

    init {
        getPosts()
    }

    private fun getPosts() {
        // Khởi tạo coroutine
        viewModelScope.launch {
            // Lấy dữ liệu
            val postsResponse = withContext(Dispatchers.IO) {
                Client.getPostResponse()
            }
            // Cập nhật dữ liệu cho LiveData
            _response.value = postsResponse
        }
    }
}
```

- Việc cập nhật dữ liệu phải được gọi bên ngoài function `withContext(Dispatchers.IO)` nếu không khi chạy nó sẽ báo lỗi
  crash app
- Đừng quên rằng, mọi đoạn code liên quan tới network đều có thể xảy ra lỗi. Cho nên chúng ta nên bao bọc nó bằng 1
  khối `try/catch` bên trong coroutine để đảm bảo không có lỗi gì nghiêm trọng gây crash app
- Nếu `try/catch` được đặt bên ngoài coroutine thì nếu xảy ra lỗi, app vẫn crash như thường

### DO THIS:

```kotlin
class MainViewModel : ViewModel() {
    private val _response = MutableLiveData<PostsResponse>()
    val response: LiveData<PostsResponse> get() = _response

    init {
        getPosts()
    }

    private fun getPosts() {
        // Khởi tạo coroutine
        viewModelScope.launch {
            try {
                // Lấy dữ liệu
                val postsResponse = withContext(Dispatchers.IO) {
                    Client.getPostResponse()
                }
                // Cập nhật dữ liệu cho LiveData
                _response.value = postsResponse
            } catch (e: Exception) {
                println(e)
            }
        }
    }
}
```

### NOT THIS:

```kotlin
class MainViewModel : ViewModel() {
    private val _response = MutableLiveData<PostsResponse>()
    val response: LiveData<PostsResponse> get() = _response

    init {
        getPosts()
    }

    private fun getPosts() {
        // Khởi tạo coroutine
        try {
            viewModelScope.launch {
                // Lấy dữ liệu
                val postsResponse = withContext(Dispatchers.IO) {
                    Client.getPostResponse()
                }
                // Cập nhật dữ liệu cho LiveData
                _response.value = postsResponse
            }
        } catch (e: Exception) {
            println(e)
        }
    }
}
```

#### Chạy ứng dụng lên và chúng ta đã có ứng dụng xem ảnh girl xinh rồi đóo
https://github.com/1plus1equal1/android_traning_todo_app/blob/main/images_videos/Screen%20Recording%202022-05-02%20at%2015.01.17.mov
