# Networking

## Mục tiêu
- Sử dụng thư viện `Ktor` để kết nối internet và lấy dữ liệu về

#### Bước 1: Thêm thư viện `Ktor` vào `build.gradle`
```groovy
implementation("io.ktor:ktor-client-android:1.6.7")
implementation("io.ktor:ktor-client-serialization:1.6.7")
implementation("io.ktor:ktor-client-logging:1.6.7")
```

#### Bước 2: Tạo file Client.kt
- Chúng ta sẽ sử dụng api của page `Gái xinh chọn lọc` trên `Tumblr` để có được nhiều hình ảnh của các bạn nữ
- Tạo object Client tring file Client.kt
```kotlin
object Client {

}
```
#### Bước 3: Khai báo biến POSTS_URL bên trong object Client đó
```kotlin
object Client {
    private const val POSTS_URL = "https://api.tumblr.com/v2/blog/gaixinhchonloc/posts?api_key=rpyJ4MNH5MnXpEuz40kuVkeG3DPGQZE03aISFSwGx7pNv4zLoi"
}
```
#### Bước 4: Khai báo biến `httpClient`, đây là biến sẽ giúp ứng dụng có thể kết nối được internet
```kotlin
object Client {
    private const val POSTS_URL = "https://api.tumblr.com/v2/blog/gaixinhchonloc/posts?api_key=rpyJ4MNH5MnXpEuz40kuVkeG3DPGQZE03aISFSwGx7pNv4zLoi"
    
    private val httpClient = HttpClient(Android) {
        engine {
            connectTimeout = 100_000
            socketTimeout = 100_000
        }

        install(JsonFeature) {
            serializer = KotlinxSerializer(
                kotlinx.serialization.json.Json {
                    prettyPrint = true
                    ignoreUnknownKeys = true
                }
            )
        }

        install(Logging) {
            logger = Logger.ANDROID
            level = LogLevel.ALL
        }
    }
}
```
#### Bước 5: Tiếp theo ta khai báo một phương thức để các thành phần ứng dụng khác (Activity, ViewModel...) có thể gọi từ bên ngoài file `Client.kt` và lấy dữ liệu để hiển thị
```kotlin
object Client {
    private const val POSTS_URL = "https://api.tumblr.com/v2/blog/gaixinhchonloc/posts?api_key=rpyJ4MNH5MnXpEuz40kuVkeG3DPGQZE03aISFSwGx7pNv4zLoi"
    private val httpClient = HttpClient(Android) {
        engine {
            connectTimeout = 100_000
            socketTimeout = 100_000
        }

        install(JsonFeature) {
            serializer = kotlinx.serialization.json.KotlinxSerializer(
                kotlinx.serialization.json.Json {
                    prettyPrint = true
                    ignoreUnknownKeys = true
                }
            )
        }

        install(Logging) {
            logger = Logger.ANDROID
            level = LogLevel.ALL
        }
    }

    suspend fun getPosts(limit: Int?, offset: Int?): PostsResponse {
        return httpClient.get(POSTS_URL) {
            parameter("limit", limit)
            parameter("offset", offset)
        }
    }
}
```
## Chú ý:
- Để ý thì chúng ta có thể thấy là đằng trước phương thức `getPosts` có từ khoá `suspend`, đây là từ khoá để giúp các câu lệnh trong phương thức có thể chạy mà không ảnh hưởng tới giao diện. Ngoài ra, nếu trong 1 phương thức nào đó có gọi tới một phương thức có từ khoá `suspend` thì phương thức đó cũng phải có từ khoá `suspend` nếu không Android Studio sẽ báo lỗi.
- Để có thể chạy được 1 phương thức `suspend` mà không cần đánh đấu các phương thức ở nơi gọi ta cần khởi tạo 1 coroutine tương tự phần SplashScreen.
Ví dụ:
```kotlin
viewModelScope.launch {
    val posts = Client.getPosts() // getPosts là suspend function
}
```

### Như vậy là chúng ta đã có thể dùng Ktor để kết nối internet để lấy được link ảnh các bạn nữ xinh xinh về rồi đó. Phần tiếp theo ch ta sẽ cho dữ liệu hiển thị lên màn hình điện thoại để ngắm cho khoẻ thay vì phải đi dán link vào trình duyệt để xem nhé
