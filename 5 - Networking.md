# Networking

## Mục tiêu
- Sử dụng thư viện `Ktor` để kết nối internet và lấy dữ liệu về

#### Bước 0: Thêm quyền INTERNET cho ứng dụng
- Mở file `AndroidManifest.xml`
- Thêm dòng bên dưới
```xml 
<uses-permission android:name="android.permission.INTERNET" />
```
Đây là file `AndroidManifest.xml` sau khi thêm<br/>
```xml
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    package="dev.tsnanh.android.girlscollection">

    <uses-permission android:name="android.permission.INTERNET" />

    <application
        android:allowBackup="true"
        android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name"
        android:roundIcon="@mipmap/ic_launcher_round"
        android:supportsRtl="true"
        android:theme="@style/Theme.GirlsCollection">
        <activity
            android:name=".ui.splash.SplashActivity"
            android:exported="true">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />

                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>
        <activity
            android:name=".ui.main.MainActivity"
            android:exported="true" />
    </application>

</manifest>
```

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

    suspend fun getPostResponse(): PostsResponse {
        return httpClient.get(POSTS_URL)
    }
}
```
## Chú ý:
- Để ý thì chúng ta có thể thấy là đằng trước phương thức `getPostResponse` có từ khoá `suspend`, đây là từ khoá để giúp các câu lệnh trong phương thức có thể chạy mà không ảnh hưởng tới giao diện. Ngoài ra, nếu trong 1 phương thức nào đó có gọi tới một phương thức có từ khoá `suspend` thì phương thức đó cũng phải có từ khoá `suspend` nếu không Android Studio sẽ báo lỗi.
- Để có thể chạy được 1 phương thức `suspend` mà không cần đánh đấu các phương thức ở nơi gọi ta cần khởi tạo 1 coroutine tương tự phần SplashScreen.
## Coroutine là cái méo gì?
Hiểu đơn giản là coroutine sẽ giúp thực thi các phương thức bất đồng bộ (phương thức chạy ko biết trước lúc nào xong) trong 1 phạm vi code và không làm cho luồng hiện tại hay UI thread bị block bởi các phương thức bất đồng bộ đó
### Ví dụ:
```kotlin
// Khởi tạo coroutine
viewModelScope.launch {
    val posts = Client.getPostResponse() // getPostResponse là suspend function
    // Code bên dưới sẽ chạy khi nào getPostResponse chạy xong
}
```

### Như vậy là chúng ta đã có thể dùng Ktor để kết nối internet để lấy được link ảnh các bạn nữ xinh xinh về rồi đó. Phần tiếp theo ch ta sẽ cho dữ liệu hiển thị lên màn hình điện thoại để ngắm cho khoẻ thay vì phải đi dán link vào trình duyệt để xem nhé
