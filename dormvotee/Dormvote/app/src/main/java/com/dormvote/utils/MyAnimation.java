package com.dormvote.utils;

import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.RotateAnimation;

public class MyAnimation {
	// �붯��
	public static void StartAnimationIN(ViewGroup viewGroup, int duration) {
		for (int i = 0; i < viewGroup.getChildCount(); i++) {
			viewGroup.getChildAt(i).setVisibility(View.VISIBLE);
			viewGroup.getChildAt(i).setClickable(true);// ���Ե��
			viewGroup.getChildAt(i).setFocusable(true);// ���Ի�ý���
		}

		Animation animation;
		/*
		 * fromDegrees ��ʼ�Ƕ� toDegrees ����Ƕ� pivotXType x��Ĳ����� pivotXValue
		 * ����x��Ĳ����� ���ĸ�λ�ý�����ת pivotYType Y��Ĳ����� pivotYValue
		 * ����Y��Ĳ����� ���ĸ�λ�ý�����ת
		 */
		animation = new RotateAnimation(-180, 0, Animation.RELATIVE_TO_SELF,
				0.5f, Animation.RELATIVE_TO_SELF, 1.0f);
		animation.setFillAfter(true);// ͣ���� �������н����λ��
		animation.setDuration(duration);// ��������ʱ��

		viewGroup.startAnimation(animation);
	}

	// ������
	public static void StartAnimationOUT(final ViewGroup viewGroup,
			int duration, int startOffSet) {
		Animation animation = new RotateAnimation(0, -180,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				1.0f);
		animation.setFillAfter(true);// ͣ���� �������н����λ��
		animation.setDuration(duration);// ��������ʱ��
		animation.setStartOffset(startOffSet);// �����ӳ�����ʱ��
		animation.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {

			}

			@Override
			public void onAnimationRepeat(Animation animation) {

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				for (int i = 0; i < viewGroup.getChildCount(); i++) {
					viewGroup.getChildAt(i).setVisibility(View.GONE);
					viewGroup.getChildAt(i).setClickable(false);// �����Ե��
					viewGroup.getChildAt(i).setFocusable(false);// �����Ի�ý���
				}
				viewGroup.setVisibility(View.GONE);
			}
		});

		viewGroup.startAnimation(animation);
	}
}
