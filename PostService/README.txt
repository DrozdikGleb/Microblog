Получить один пост по ID поста
    Запрос: /post/get/{postId}
    Ответ: Post.class (Status.OK) or Status.NOT_FOUND
    Метод: GET

Получить все посты по ID user`a
    Запрос: /post/getall/{userId}
    Ответ: List<Post>.class (Status.OK) or Status.NOT_FOUND
    Метод: GET

Добавить пост
    Запрос: /post/create/{userId} HTTP_BODY String.class
    Ответ: Status.CREATE
    Метод: PUT

Обновить пост
    Запрос: /post/update/{postId} HTTP_BODY String.class
    Ответ: Post.class (Status.OK) or Status.NOT_FOUND
    Метод: POST

Удалить пост по ID поста
    Запрос: /post/delete/{postId}
    Ответ: Status.OK or Status.NOT_FOUND
    Метод: DELETE

Удалить все посты пользователям
    Запрос: /post/delete/all/{userId}
    Ответ: Status.OK or Status.NOT_FOUND
    Метод: DELETE