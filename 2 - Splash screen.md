# Splash screen

## Mục tiêu
- `Tạo ra 1 màn hình splash hiển thị khoảng 5s sau khi mở app, sau 5s sẽ chuyển sang màn hình chính của app`
- `Thêm ảnh vào dự án và sử dụng`
- `Biết sử dụng ImageView cơ bản`

## Thêm thư viện Lifecycle vào dự án
#### Bước 1: Tìm thư viện trên https://developer.android.com/jetpack/androidx/explorer
![image](https://user-images.githubusercontent.com/65409611/166155478-91eaec13-e32a-44e2-9be3-2a9f03049e44.png)
#### Bước 2: Thêm thư viện vào dự án
- `Copy các url cần dùng ( ở đây chỉ dùng viewmodel và lifecycle runtime ) vào bỏ vào file build.gradle của app sau đó click vào Sync now.`
![image](https://user-images.githubusercontent.com/65409611/166156176-ca9c49c1-d79f-4865-ae82-a84fe8700798.png)

#### Bước 3: Thêm ảnh vào dự án
https://user-images.githubusercontent.com/45780510/166152760-c8f7bdc8-189e-483a-87aa-07b9b84c4651.mov
- Chọn ảnh cần thêm và kéo vào thư mục drawable của dự án

#### Bước 4: Tạo SplashActivity.kt và file layout activity_splash.xml
![Screen Shot 2022-05-01 at 22 28 16](https://user-images.githubusercontent.com/45780510/166152954-6d127ffa-9352-4d69-b187-65952ec28b6b.png)
![Screen Shot 2022-05-01 at 22 31 18](https://user-images.githubusercontent.com/45780510/166153349-42bf0928-839a-47b5-9b84-4a6a6dba7e23.png)

#### Bước 5: 
- Mở file `activity_splash.xml`<br/>
![Screen Shot 2022-05-01 at 22 45 15](https://user-images.githubusercontent.com/45780510/166153676-dc16b8e6-bc4e-41f4-82df-e13ea57cdd61.png)<br/>
- Nhập vào `ImageView` và nhấn `Apply`<br/>
![Screen Shot 2022-05-01 at 22 50 31](https://user-images.githubusercontent.com/45780510/166153777-53fccf5e-527b-401b-83dd-69a88226a4a7.png)<br/>
-Bây giờ `ConstraintLayout` cũ đã trở thành `ImageView`<br/>
![Screen Shot 2022-05-01 at 22 52 26](https://user-images.githubusercontent.com/45780510/166153834-c8005890-2e8f-4ed0-8bd2-357512f68b98.png)<br/>

#### Bước 6: Hiển thị hình ảnh lên `ImageView`
- Nhấn chọn `ImageView`, ở tab `Attributes` tìm `srcCompat` rồi nhấn vào icon ảnh để mở mục chọn ảnh<br/>
![Screen Shot 2022-05-01 at 22 57 50](https://user-images.githubusercontent.com/45780510/166153996-860d205f-bae4-4569-bfdf-d78f7cbed582.png)<br/>
![Screen Shot 2022-05-01 at 22 59 35](https://user-images.githubusercontent.com/45780510/166154140-d9876851-1777-4648-8bec-53c29a1111e5.png)<br/>
- Chúng ta có thể thấy được là bức ảnh không phủ kín màn hình, cho nên chúng ta cần thêm vài bước để `ImageView` có thể hiển thị chính xác những gì chúng ta mong muốn<br/>
![Screen Shot 2022-05-01 at 23 03 07](https://user-images.githubusercontent.com/45780510/166154175-596673f5-25a5-4421-ae7c-db3a5316c69e.png)<br/>
- Ở tab `Attributes` tìm `scaleType`, chọn `centerCrop`<br/>
![Screen Shot 2022-05-01 at 23 07 06](https://user-images.githubusercontent.com/45780510/166154351-c0c7a939-4b77-4f33-b8d5-e01181f10bbd.png)<br/>
- Bức ảnh bây giờ đã hiển thị đúng<br/>
![Screen Shot 2022-05-01 at 23 08 30](https://user-images.githubusercontent.com/45780510/166154413-0ded45c8-3db5-402a-b3ba-174ee92cb4ae.png)<br/>

#### Bước 7: Delay màn hình Splash 5s rồi chuyển sang màn hình khác
- Mở file SplashActivity.kt
- Chúng ta sẽ sử dụng coroutines để có thể delay 5s và chuyển sang màn hình `MainActivity` bằng đoạn code sau
```kotlin
lifecycleScope.launch {
    delay(5_000)
    val intent = Intent(this@SplashActivity, MainActivity::class.java)
    startActivity(intent)
}
```
- File SplashActivity.kt của chúng ta sau khi sửa sẽ như sau:<br/>
![Screen Shot 2022-05-01 at 23 35 06](https://user-images.githubusercontent.com/45780510/166155419-9ea9857b-afb7-42e2-a3a4-95eeeeccf6a1.png)<br/>
- Chúng ta vẫn còn thấy một thanh app bar như hình<br/>
![Screen Shot 2022-05-01 at 23 39 48](https://user-images.githubusercontent.com/45780510/166155547-8b752a9e-b4c5-4e8d-9c92-7056119a1375.png)<br/>
- Để loại bỏ nó ra khỏi tất cả màn hình của app ta mở file `res/values/themes.xml`<br/>
![Screen Shot 2022-05-01 at 23 42 00](https://user-images.githubusercontent.com/45780510/166155633-2aff2bc5-c442-4992-b378-19673f9254ce.png)<br/>
- Để ý dòng thứ 3 chúng ta thấy có `parent="Theme.MaterialComponents.DayNight.DarkActionBar"`, việc ta cần làm là thay đổi từ `DarkActionBar` thành `NoActionBar`<br/>
![Screen Shot 2022-05-01 at 23 43 52](https://user-images.githubusercontent.com/45780510/166155692-f722593f-2daf-4519-bbc7-2cfe4ed3215f.png)<br/>

### Và như vậy là chúng ta đã hoàn thành được màn hình splash
### Chạy app và xem kết quả
https://user-images.githubusercontent.com/45780510/166155909-b176e260-d63b-4dd4-a766-9f32d20919fb.mov
