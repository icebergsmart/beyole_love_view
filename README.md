## 描述(Description):
自定义爱心view，直接可以应用。此view采用自定义的方式，绘制了桃心型的爱心，具体参考公式:<br>
x=16*(sint)^3<br>
y=13*cost-5cos(2*t)-2*cos(3*t)-cos(4*t)<br>
其中0<=t<=2*Math.PI
## 用法(usage):
直接在布局文件中引入view所在的包，具体引用方式如下所示:<br>
```Java
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    xmlns:beyole="http://schemas.android.com/apk/res/com.beyole.loveimageview"
    android:layout_width="match_parent"
    android:layout_height="match_parent" >

    <com.beyole.view.LoveView
        android:id="@+id/id_mylove"
        android:layout_width="150dp"
        android:layout_height="150dp"
        android:layout_centerInParent="true"
        beyole:isAnimateOn="true"
        beyole:loveColor="#660000ff" />

</RelativeLayout>
```
