Thanks a lot for your efforts,
I faced with the following:
1. Project not buildable; Smth missed during git-committing?
2. Didn't find sharedPref or Room usages; I guess that related with point 1.
3. Kindly check my comments with todo-tag

+ I also checked apk file that was provided by you. Looks like that the program is working fine, I like it :)
But were found the following issues:

1. Was missed: .. Each new “shaking” reset timer counting ..
2. Bug/App crash -> java.lang.RuntimeException: Unable to destroy activity {com.example.photoshaker/com.example.photoshaker.MainActivity}: kotlin.UninitializedPropertyAccessException: lateinit property timer has not been initialized