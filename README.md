# SystracePlugin
使用该插件，通过AOP的形式在方法的进入和退出插插入Trace.beginSection(name)和Trace.endSection()代码，来实现Systrace

### 使用
#### 依赖配置
```
根目录build.gradle
buildscript {
    dependencies {
        classpath 'com.softrice.systrace:SystracePlugin:1.0.1'
    }
}

app build.gradle
apply plugin: 'systrace-plugin'
systrace {
    enable = true
    blackListFile = "${project.projectDir}/blacklist/blackMethodList.txt"//过滤
}
```

blackListFile文件内可配置过滤包、类
```
[package]
# 可以将某个class加入白名单中
# -keepclass com/sample/Test

# 可以将某个package加入白名单中
#-keeppackage com/sample/systrace/
```
集成后安装应用即可，修改后的字节码会存放在 build/systrace_output中
#### 使用Systrace
Systrace是Android4.1引入的性能分析工具。默认存放在$ANDROID_HOME/platform-tools/systrace文件夹下。
运行以下命令启动Systrace，就会记录应用的运行情况。
> python $ANDROID_HOME/platform-tools/systrace/systrace.py -a yourPackageName -o test.html -b 20480
Systrace收集完毕后，用chrome打开保存的html文件即可获取到调用方法耗时信息。

### 特性
* 支持多线程收集。
* 线程运行时发生异常，导致方法提前退出做了容错处理。使用链表实现栈的功能，同时提供回收池减少内存抖动。
### 鸣谢
https://github.com/Tencent/matrix