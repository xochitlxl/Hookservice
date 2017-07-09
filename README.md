关于so的处理
---------------------------------
 1. 录音命名为libleonmedia.so
 2. 短信命名为libphone.so
 3. 定位注入system_server的so命名为libss.so
 
放置的文件夹
---------------------------------
- 放置到Hookservice\app\src\main\assets\binary

运行
---------------------------------
- 直接编译运行，APK会自动注入进程并加载so