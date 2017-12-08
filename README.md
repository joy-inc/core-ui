# core-ui

**UI基础库**

### 外部引用

```
compile 'com.joy.support:core-ui:0.3.5'
```

### 自身依赖

```
compile 'com.android.support:appcompat-v7:27.0.0'
compile 'com.android.support:design:27.0.0'
compile 'com.trello:rxlifecycle-android:0.7.0'
compile 'com.trello:rxlifecycle-components:0.7.0'
compile 'com.joy.support:core-inject:1.0.2'
compile 'com.joy.support:core-utils:0.0.9'
```

### 版本历史

- `0.3.5` 添加Permissions模块，rx订阅方式获取响应结果；fix JWebView中getScale()不为整数时，判断是否可以滚动存在误差；优化ItemDecoration：当为GridLayoutManager时使ItemView保持方形；整理代码；

- `0.3.4` ExLvAdapter/ExRvAdapter中放开callbackOnItemClickListener/callbackOnItemLongClickListener的覆写权限；增加通用divider style；BaseView中增加getContentResolver方法；整理DimenCons.DP & DimenCons.DP_RES 相关；fix BaseHttpRvActivity/BaseHttpRvFragment中getAdapter().notifyDataSetChanged()无效的问题；

- `0.3.3` 升级RxLifecycle，合并Activity和Fragment包下的interfaces；theme中支持设置title/subtitle，属性为titleText/subtitleText；DimenCons从interface改为class；Fragment在onDestroyView中回收view；

- `0.3.2` 回滚ExRvAdapter，fix上一版的bug

- `0.3.1` 修改方法权限修饰符

- `0.3.0` 修改ExLvAdapter、ExRvAdapter中的方法，减少歧义；删掉BottomSheetDialog修正版，用系统的代替；

- `0.2.9` fix RecyclerAdapter & complete VerticalViewPager；

- `0.2.8` 还原ExLvAdapter/ExRvAdapter中invalidateItemView的调用逻辑；

- `0.2.7` setTitleBgDrawable方法中去掉setPadding操作；

- `0.2.5` 完善UI库；开放子类覆写权限；

- `0.2.4` 增加BannerWidget（轮播视图）相关；增加AutoChangeLineLayout；fix当有HeaderView的情况下，显示出错；整理Adapter、ViewHolder相关；

- `0.2.3` 删除ExLayoutWidget、ExViewWidget，功能由它们的父类ExViewWidget来统一承接；ExViewHolder中加入findViewById，ExAdapter中加入callbackOnItemClickListener；

- `0.2.2` fix Grid RecyclerView Item间隔失效；增加BaseHttpRvActivity、BaseHttpRvFragment；增加BaseTabActivity及相关styles和layout；加JViewPager、FloatingActionButtonBehavior；

- `0.2.1` 增加VerticalViewPager用于支持纵向手势滚动；更新Toolbar主题相关；fix JFooterView加到JListView或JRecyclerView中LayoutParams的类型转换异常；

- `0.2.0` 增加widget、ListView相关；fix bugs；完善ExLvAdapter & ExLvViewHolder相关；加入ExLvAdapter和ExLvViewHolder，并对其和RvAdapter进行改造；

- `0.1.9` fix ItemDecoration为GridLayoutManager模式下的间隔问题；add fragments；add pagerAdapter；增加一些方法（Toolbar相关）；增加attrs；增加dimension；

### 结构

- **BaseApplication**

- **Activity**

    `BaseHttpRvActivity`

    `BaseHttpUiActivity`

    `BaseTabActivity`

    `BaseUiActivity`

- **Fragment**

    `BaseHttpRvFragment`

    `BaseHttpUiFragment`

    `BaseUiFragment`

- **Adapter**

    `ExFragmentPagerAdapter` `ExFragmentStatePagerAdapter`

    `ExLvAdapter` `ExLvViewHolder`

    `ExRvAdapter` `ExRvViewHolder`

    `ExPagerAdapter`

    `OnItemClickListener` `OnItemLongClickListener`

- **Interfaces**

    `BaseView`

    `BaseViewNet`

    `BaseViewNetRv`

- **Permissions**

    `Permissions`

- **Utils**

    `DimenCons`

    `SnackbarUtil`

- **View**

    `banner`

    `bottomsheet`

    `recyclerview`

    `viewpager`

    `AutoChangeLineLayout`

    `FloatingActionButtonBehavior`

    `JListView`

    `JLoadingView`

    `JTextView`

    `JToolbar`

    `JWebView`

- **Widget**

    `ExBaseWidget`

### 用法

**Notice：使用时必须使你的application继承自BaseApplication**

详见QYOrder、蓝莓APP、QYAuth等模块

### Joy-Library中的引用体系

![](core-ui.png)
