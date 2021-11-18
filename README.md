# JD-GUI NG

给多年没有更新的 jd gui 加点功能。

（u1s1，jadx更人性化，但是两者功能有交叉

## TODO LIST
- [x] 直接从文件夹load工程，而不是非要打个 war
- [x] 增加 log 选项，现在这个模式 hang 住真的难受
- [ ] 直接抽取 res 到文件，现在抽取到内存对大工程会有问题
- [ ] 优化 mvn 包识别和下载机制，细化小版本
- [ ] 对不同 provider 来源的source做区分
- [ ] 直接导出 mvn 工程，方便 idea index src
- [ ] 对抗混淆，增加选项在导出时不省略包名
- [ ] 对抗混淆，提示类名大小写冲突

## BUG FIX
### save all sources 异常处理
save all sources 时如果在释放文件时出现异常导致 task 退出，则整个进程都会失败，而且没有提示。

这里修了一个文件名不合法导致的退出。

然后给整个task加了try catch。
