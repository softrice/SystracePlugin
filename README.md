# SystracePlugin
通过AOP的形式在方法的进入和退出插入Trace.beginSection(name)和Trace.endSection()。配合使用Systrace即可在应用运行期获取详细的方法耗时信息。

### 使用方法
#### 依赖配置
```
//根目录下 build.gradle
buildscript {
    dependencies {
        classpath 'com.softrice.systrace:SystracePlugin:1.0.1'
    }
}

//app目录下 build.gradle
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
集成完毕后编译安装应用，修改后的字节码会输出存放在 build/systrace_output中。可以看到方法的前后插入和Trace代码。
```
//示例
public class App extends Application {

    public void onCreate() {
        TraceTag.i("com.softrice.sample.App.onCreate.()V");
        super.onCreate();
        TraceTag.o("com.softrice.sample.App.onCreate.()V");
    }

}
```
#### 使用Systrace
Systrace是Android4.1引入的性能分析工具。默认存放在$ANDROID_HOME/platform-tools/systrace文件夹下。
<br>运行以下命令启动Systrace，就会记录应用的运行情况。
<br>再输入回车即可结束记录，并输出test.html。
> python $ANDROID_HOME/platform-tools/systrace/systrace.py -a yourPackageName -o test.html -b 20480

*注意:替换-a后的包名为检测的包名 不然无法正确输出*
<br>Systrace收集完毕后，用Chrome打开保存的html文件即可获取到调用方法耗时信息。
<br>示例html：sample运行时收集的数据:[test.html](https://github.com/softrice/SystracePlugin/blob/master/test.html)

### 特性
* 支持多线程收集。
* 线程运行时发生异常，导致方法提前退出的异常情况做了容错处理。
* 使用链表实现栈的功能，同时提供回收池减少内存抖动。
* 过滤无效&短小方法的插入

### 鸣谢
[matrix](https://github.com/Tencent/matrix) 部分功能摘取