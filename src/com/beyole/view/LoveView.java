package com.beyole.view;

import com.beyole.loveimageview.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class LoveView extends View {

	private Paint mPaint;
	private float default_min_rate = 5.0f;
	private float default_max_rate = 8.0f;
	private float default_step_rate = 0.05f;

	private static final int IS_ANIMATE_ON = 1;
	private static final int IS_ANIMATE_OFF = 0;
	private int default_color = Color.RED;
	// 动画线程是否开启
	private boolean isAnimateOn = false;
	// 半径变化率
	private float rate = 5.0f;
	private AnimateThread animateThread;
	// 路径
	private Path path;
	private boolean orderByDesc = false;

	public LoveView(Context context) {
		this(context, null);
	}

	public LoveView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);

	}

	public LoveView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		TypedArray array = context.getTheme().obtainStyledAttributes(attrs, R.styleable.LoveView, defStyle, 0);
		int n = array.getIndexCount();
		for (int i = 0; i < n; i++) {
			int attr = array.getIndex(i);
			switch (attr) {
			case R.styleable.LoveView_isAnimateOn:
				isAnimateOn = array.getBoolean(attr, false);
				break;
			case R.styleable.LoveView_loveColor:
				default_color = array.getColor(attr, default_color);
				break;
			}
		}
		array.recycle();
		init();
	}

	private void init() {
		// 初始化画笔
		mPaint = new Paint();
		// 消除锯齿
		mPaint.setAntiAlias(true);
		// 画笔规模为填充
		mPaint.setStyle(Style.FILL);
		// 画笔宽度为2
		mPaint.setStrokeWidth(2);
		// 初始化路径
		path = new Path();
		if (isAnimateOn) {
			animateThread = new AnimateThread();
			animateThread.start();
		}
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		// 重置画板
		path.reset();
		mPaint.setColor(default_color);
		// 得到中点坐标
		int px = getMeasuredWidth() / 2;
		int py = getMeasuredHeight() / 2;
		path.moveTo(px, py);
		// 根据心形函数画图
		for (double i = 0; i < 2 * Math.PI; i += 0.001) {
			float x = (float) (16 * Math.sin(i) * Math.sin(i) * Math.sin(i));
			float y = (float) (13 * Math.cos(i) - 5 * Math.cos(2 * i) - 2 * Math.cos(3 * i) - Math.cos(4 * i));
			x *= rate;
			y *= rate;
			x = px - x;
			y = py - y;
			path.lineTo(x, y);
		}
		canvas.drawPath(path, mPaint);

	}

	private class AnimateThread extends Thread {
		@Override
		public void run() {
			while (true) {
				if (!orderByDesc) {
					rate += default_step_rate;
					if (rate > default_max_rate) {
						orderByDesc = true;
					}
				} else {
					rate -= default_step_rate;
					if (rate < default_min_rate) {
						orderByDesc = false;
					}
				}
				// mPaint.setAlpha((int) (((rate - 1.5) * 255) / 8));
				try {
					Thread.sleep(50);
				} catch (Exception e) {
				}
				postInvalidate();
			}
		}
	}

	/**
	 * 设置最小变化率
	 * 
	 * @param minRate
	 */
	public void setMinRate(float minRate) {
		this.default_min_rate = minRate;
	}

	/**
	 * 设置最大变化率
	 * 
	 * @param maxRate
	 */
	public void setMaxRate(float maxRate) {
		this.default_max_rate = maxRate;
	}

	/**
	 * 设置步进变化率
	 * 
	 * @param stepRate
	 */
	public void setStepRate(float stepRate) {
		this.default_step_rate = stepRate;
	}

	public void setColor(int color) {
		this.default_color = color;
	}

	private OnClickListener clickListener;

	public interface OnClickListener {
		void onClick(View view);
	}

	/**
	 * 设置点击监听器
	 */
	public void setOnClickListener(OnClickListener listener) {
		this.clickListener = listener;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (clickListener != null) {
			clickListener.onClick(this);
		}
		return super.onTouchEvent(event);
	}

}
