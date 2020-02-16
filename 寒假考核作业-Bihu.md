[toc]

# 寒假考核作业-Bihu

## 一、App简要介绍

### 一）界面静图

#### 登录界面

![image]( https://github.com/Seven749/RainbowBihu/blob/master/photos/pRes/1login.jpg )

[![1zC7md.md.jpg](https://s2.ax1x.com/2020/02/15/1zC7md.md.jpg)](https://imgchr.com/i/1zC7md)

#### 注册界面

[![1zCbTI.md.jpg](https://s2.ax1x.com/2020/02/15/1zCbTI.md.jpg)](https://imgchr.com/i/1zCbTI)

#### 首页界面

[![1zCx1S.md.jpg](https://s2.ax1x.com/2020/02/15/1zCx1S.md.jpg)](https://imgchr.com/i/1zCx1S)

#### 收藏界面

[![1zCvp8.md.jpg](https://s2.ax1x.com/2020/02/15/1zCvp8.md.jpg)](https://imgchr.com/i/1zCvp8)

#### 疫情防控

[![1zCOtP.md.jpg](https://s2.ax1x.com/2020/02/15/1zCOtP.md.jpg)](https://imgchr.com/i/1zCOtP)

#### 发布问题

[![1zCz6g.md.jpg](https://s2.ax1x.com/2020/02/15/1zCz6g.md.jpg)](https://imgchr.com/i/1zCz6g)

#### 未收藏的问题（无回答）

[![1zPSXQ.md.jpg](https://s2.ax1x.com/2020/02/15/1zPSXQ.md.jpg)](https://imgchr.com/i/1zPSXQ)

#### 收藏的问题（有回答）

[![1zP9mj.md.jpg](https://s2.ax1x.com/2020/02/15/1zP9mj.md.jpg)](https://imgchr.com/i/1zP9mj)

#### 我的界面（可以上下滑动）

[![1zPC0s.md.jpg](https://s2.ax1x.com/2020/02/15/1zPC0s.md.jpg)](https://imgchr.com/i/1zPC0s)

[![1zPP7n.md.jpg](https://s2.ax1x.com/2020/02/15/1zPP7n.md.jpg)](https://imgchr.com/i/1zPP7n)

#### 修改密码

[![1zPkt0.md.jpg](https://s2.ax1x.com/2020/02/15/1zPkt0.md.jpg)](https://imgchr.com/i/1zPkt0)



---

### 二）动图演示

#### 打开App

点击app，直接进入登录界面，双击返回键直接退出app

![image](https://github.com/Seven749/RainbowBihu/blob/master/photos/gif/1openApp.gif)

#### 打开注册界面

点击登录界面下方的蓝字进行注册

![image](https://github.com/Seven749/RainbowBihu/blob/master/photos/gif/2register.gif)

#### 从推荐页到收藏页

这里的显示bug一直没有改好

![image](https://github.com/Seven749/RainbowBihu/blob/master/photos/gif/3hToF.gif)

#### 发布问题

![image]( https://github.com/Seven749/RainbowBihu/blob/master/photos/gif/4postQ.gif )

#### 打开收藏列表的回答

![image]( https://github.com/Seven749/RainbowBihu/blob/master/photos/gif/5openF.gif )

#### 打开推荐列表的回答

![image]( https://github.com/Seven749/RainbowBihu/blob/master/photos/gif/6openR.gif )

#### 查看疫情

![image]( https://github.com/Seven749/RainbowBihu/blob/master/photos/gif/7openYq.gif )

#### 修改密码

![image]( https://github.com/Seven749/RainbowBihu/blob/master/photos/gif/8mineTochange.gif )

#### 退出登录

![image]( https://github.com/Seven749/RainbowBihu/blob/master/photos/gif/9exitLogin.gif )



---

### 三）使用的技术总概

#### 布局

使用了ConstraintLayout，LinearLayout(大量)

使用RecyclerView实现列表

使用SrollView实现非列表滚动

使用WebView实现链接网页

自定义了title的控件

#### Java代码

##### 1）其中有一个MyUtil工具类，其中内含：

###### 工具一：获取网络图片--==getURLImage==

###### 工具二：封装的网络请求--==sendOkHttpUtil==

###### 工具三：重启App的程序--==restartApp==

###### 工具四：活动管理器--==activityManger==

##### 2）另外封装了一个实现实现双击back键退出程序的工具类：==DoubleClickExitHelper==

在主界面和登录界面都是使用了==DoubleClickExitHelper==和==MyUtil.finishAll()==实现“再按一次退出应用”



### 四）心得体会

为了这次作业，我前前后后花了20天的时间来完成，从重新认识Android，反反复复的看《第一行代码》，到编写一个个只实现一部分小功能的测试程序，我收获了许多，为了实现一个功能，为了解决一个bug，CSDN的收藏夹也渐渐装得越来越满，解决一个问题可能需要收集十几篇文章，上几个博客。

当然解决问题的过程是辛苦的，但最后解决了那过程也就过去了，同时也有一些问题没有成功解决，让我苦恼了许久，大量的删改代码，最后却没有效果，不过部分代码也因此变得更加规范，也不算完败。

然后再说说设计的思路:，设计思路很关键！非常关键！

它将决定你的代码写道后面是否轻松，设计思路最初应看得大，看的广。

我认为，应从一个粗糙骨感的框架，到一个精致丰满的程序。这样思路是向外发散，而不会越写越窄，最后把自己禁锢起来。