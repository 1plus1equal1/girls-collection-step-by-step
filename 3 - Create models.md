# Create model

## Mục tiêu
- `Biết tạo 1 model để chuyển đổi dữ liệu lấy từ api để sử dụng trong ứng dụng`

####  1 file json cơ bản
```json
{
    "meta": {
        "status": 200,
        "msg": "OK"
    },
    "response": {
        "posts": [
            {
                "type": "photo",
                "id": 635827454330667008,
                "id_string": "635827454330667008",
                "date": "2020-11-26 06:31:50 GMT",
                "timestamp": 1606372310,
                "caption": "<p>#gaixinhchonloc #gxcl #beautygirl #vietnamesegirl #beauty #girl</p>\n\n<p><a href=\"https://www.instagram.com/p/CIC0QUmAJ4L/?igshid=vbi9xj4ezhx5\">https://www.instagram.com/p/CIC0QUmAJ4L/?igshid=vbi9xj4ezhx5</a></p>",
                "link_url": "https://www.instagram.com/p/CIC0QUmAJ4L/?igshid=vbi9xj4ezhx5",
                "image_permalink": "https://gaixinhchonloc.com/image/635827454330667008",
                "photos": [
                    {
                        "caption": "",
                        "original_size": {
                            "url": "https://64.media.tumblr.com/18bf4a0410fdf1b431a6c166cae5bac8/acccfbee3cda4daf-39/s1280x1920/779e3fca63705d2ecce30a27fa036c4495c63e05.jpg",
                            "width": 1080,
                            "height": 1350
                        }
                    }
                ]
            }
        ],
        "total_posts": 2,
        "_links": {
            "next": {
                "href": "/v2/blog/gaixinhchonloc/posts?tumblelog=gaixinhchonloc&page_number=VOa2sepy8xJeu-bNMErJg5ZJ1HBHlt3WagJvDl7sVDdTbDdySVRsbit1UXFSTll5eEdJL0VJU1dpUE5zY09rVGZDa1pDRXFnSEZCVGdOV1dzYS9TRzVRYnM5VGRFenVSSlZuakNkNndwU0VLZDJsNVAwNjl1VnN4UHVlQ1o4ZHBhMnRrODhkOGFSYk1NT1BBSWpSRzlVNVBOTTUrdjZjVlpLaldHa1FBVElVOXJPS1Z2NUhQSmJGSzM2MVZtcUh5ZVdycmpqMkVOKzR0WDVUbytvQnlSRXBtdmZzc2RxZnFtQkY5VkVkSDJSaThaWkFhQlJjeFZEZEp0RnBZUURUWFpDRHNUdzI2RGpwaW52VkU4YnYwMGJIWE1IS2lLQzFGSlhNTVUwR2JBVEtaMDQ5Q2NHZjJ5NmtKMG5haDJUVXg5WWpuVFZlS0J0ZTJGaE5WcTkwVkNVY1BLUnJVb0lxTGRaWVBTYnBTTXRnckRvKzFjb040a3FvYngzMjN5M3BxcXVhei9wNUEzWTJsTmd1dHlzNzQ3bkV3a3Iyb3lEeFRG",
                "method": "GET",
                "query_params": {
                    "tumblelog": "gaixinhchonloc",
                    "page_number": "VOa2sepy8xJeu-bNMErJg5ZJ1HBHlt3WagJvDl7sVDdTbDdySVRsbit1UXFSTll5eEdJL0VJU1dpUE5zY09rVGZDa1pDRXFnSEZCVGdOV1dzYS9TRzVRYnM5VGRFenVSSlZuakNkNndwU0VLZDJsNVAwNjl1VnN4UHVlQ1o4ZHBhMnRrODhkOGFSYk1NT1BBSWpSRzlVNVBOTTUrdjZjVlpLaldHa1FBVElVOXJPS1Z2NUhQSmJGSzM2MVZtcUh5ZVdycmpqMkVOKzR0WDVUbytvQnlSRXBtdmZzc2RxZnFtQkY5VkVkSDJSaThaWkFhQlJjeFZEZEp0RnBZUURUWFpDRHNUdzI2RGpwaW52VkU4YnYwMGJIWE1IS2lLQzFGSlhNTVUwR2JBVEtaMDQ5Q2NHZjJ5NmtKMG5haDJUVXg5WWpuVFZlS0J0ZTJGaE5WcTkwVkNVY1BLUnJVb0lxTGRaWVBTYnBTTXRnckRvKzFjb040a3FvYngzMjN5M3BxcXVhei9wNUEzWTJsTmd1dHlzNzQ3bkV3a3Iyb3lEeFRG"
                }
            }
        }
    }
}
```
#### Nhìn tổng quan qua 1 object json ta thấy có 2 key là `meta` và `response`
- Sau key meta có 2 trường nhỏ hơn là `status` và `msg` nên ta có 2 class như sau: 

```kotlin
@Serializable
data class PostsResponse(
    val meta: Meta,
    val response: Response
)

@Serializable
data class Meta(
    val status: Int,
    val msg: String,
)
```
###### Mình sẽ dùng annotation `@Serializable` để đánh dấu class này. `@Serializable` giúp class ày có thể chuyển đổi dữ liệu ở các module với nhau

- Nhỏ hơn `response` sẽ là `total_posts`, `_links`, `posts` lần lượt ta sẽ có các class khác như sau:

```kotlin
@Serializable
data class Response(
    @SerialName("total_posts")
    val totalPosts: Int,
    @SerialName("_links")
    val links: Links,
    val posts: List<Post>
)

@Serializable
data class Links(
    val next: Link
)

@Serializable
data class Link(
    val href: String,
    val method: String,
    @SerialName("query_params")
    val queryParams: QueryParams,
)

@Serializable
data class QueryParams(
    val tumblelog: String,
    @SerialName("page_number")
    val pageNumber: String
)

@Serializable
data class Post(
    val type: String,
    @SerialName("blog_name")
    val id: Long,
    @SerialName("id_string")
    val idString: String,
    val date: String,
    val timestamp: Long,
    val photos: List<Photo>
)

@Serializable
data class Photo(
    val caption: String,
    @SerialName("original_size")
    val originalSize: Image
)

@Serializable
data class Image(
    val url: String,
)
```
- Vì các key của từ file json như `total_posts`, `_links` vì cho code đẹp hơn thì ta sẽ dùng  `@SerialName("total_posts")` để cho nó hiểu là biến này sẽ tên như này.
#### Đấy tạo 1 model đơn giản như thế này thôi :v 
