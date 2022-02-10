package com.papaya.cameraxtutorial;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.Log;
import android.view.View;

import com.google.mlkit.vision.pose.Pose;
import com.google.mlkit.vision.pose.PoseLandmark;

import java.util.ArrayList;
import java.util.List;

public class DrawView extends View {
    Paint paint = new Paint();
    Paint whitePaint = new Paint();
    PointF scale = new PointF();
    float Scr_w;
    float Scr_h;
    List<PoseLandmark> landmarks;

    public DrawView(Context context) {
        super(context);
        paint.setColor(Color.RED);
        paint.setStrokeWidth(10f);
        whitePaint.setColor(Color.WHITE);
        whitePaint.setStrokeWidth(15f);



    }

    void DrawPose(List<PoseLandmark> landmarks, float width, float height, float viewX, float viewY) {
        if (landmarks != null) {
            this.landmarks = landmarks;
            Scr_w = width;
            Scr_h = height;
            scale.x = viewX;
            scale.y = viewY;
            invalidate();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (landmarks != null && !landmarks.isEmpty()) {
            PointF ls = null;
            PointF rs = null;
            PointF le = null;
            PointF re = null;
            PointF lw = null;
            PointF rw = null;
            PointF lh = null;
            PointF rh = null;
            PointF lk = null;
            PointF rk = null;
            PointF la = null;
            PointF ra = null;
            if (landmarks.get(PoseLandmark.LEFT_SHOULDER).getInFrameLikelihood() > 0.5) {
                ls = landmarks.get(PoseLandmark.LEFT_SHOULDER).getPosition();
                ls.offset(scale.x, scale.y);
            }
            if (landmarks.get(PoseLandmark.RIGHT_SHOULDER).getInFrameLikelihood() > 0.5) {
                rs = landmarks.get(PoseLandmark.RIGHT_SHOULDER).getPosition();
                rs.offset(scale.x, scale.y);
            }
            if (landmarks.get(PoseLandmark.LEFT_ELBOW).getInFrameLikelihood() > 0.5) {
                le = landmarks.get(PoseLandmark.LEFT_ELBOW).getPosition();
                le.offset(scale.x, scale.y);
            }
            if (landmarks.get(PoseLandmark.RIGHT_ELBOW).getInFrameLikelihood() > 0.5) {
                re = landmarks.get(PoseLandmark.RIGHT_ELBOW).getPosition();
                re.offset(scale.x, scale.y);
            }
            if (landmarks.get(PoseLandmark.LEFT_WRIST).getInFrameLikelihood() > 0.5) {
                lw = landmarks.get(PoseLandmark.LEFT_WRIST).getPosition();
                lw.offset(scale.x, scale.y);
            }
            if (landmarks.get(PoseLandmark.RIGHT_WRIST).getInFrameLikelihood() > 0.5) {
                rw = landmarks.get(PoseLandmark.RIGHT_WRIST).getPosition();
                rw.offset(scale.x, scale.y);
            }
            if (landmarks.get(PoseLandmark.LEFT_HIP).getInFrameLikelihood() > 0.5) {
                lh = landmarks.get(PoseLandmark.LEFT_HIP).getPosition();
                lh.offset(scale.x, scale.y);
            }
            if (landmarks.get(PoseLandmark.RIGHT_HIP).getInFrameLikelihood() > 0.5) {
                rh = landmarks.get(PoseLandmark.RIGHT_HIP).getPosition();
                rh.offset(scale.x, scale.y);
            }
            if (landmarks.get(PoseLandmark.LEFT_KNEE).getInFrameLikelihood() > 0.5) {
                lk = landmarks.get(PoseLandmark.LEFT_KNEE).getPosition();
                lk.offset(scale.x, scale.y);
            }
            if (landmarks.get(PoseLandmark.RIGHT_KNEE).getInFrameLikelihood() > 0.5) {
                rk = landmarks.get(PoseLandmark.RIGHT_KNEE).getPosition();
                rk.offset(scale.x, scale.y);
            }
            if (landmarks.get(PoseLandmark.LEFT_ANKLE).getInFrameLikelihood() > 0.5) {
                la = landmarks.get(PoseLandmark.LEFT_ANKLE).getPosition();
                la.offset(scale.x, scale.y);
            }
            if (landmarks.get(PoseLandmark.RIGHT_ANKLE).getInFrameLikelihood() > 0.5) {
                ra = landmarks.get(PoseLandmark.RIGHT_ANKLE).getPosition();
                ra.offset(scale.x, scale.y);
            }


















            /*canvas.drawLine(leftShoulder.x * scale.x, leftShoulder.y * scale.y, leftElbow.x * scale.x, leftElbow.y * scale.y, paint);
            canvas.drawLine(rightShoulder.x * scale.x, rightShoulder.y * scale.y, rightElbow.x * scale.x, rightElbow.y * scale.y, paint);
            canvas.drawLine(leftElbow.x * scale.x, leftElbow.y * scale.y, leftWrist.x * scale.x, leftWrist.y * scale.y, paint);
            canvas.drawLine(rightElbow.x * scale.x, rightElbow.y * scale.y, rightWrist.x * scale.x, rightWrist.y * scale.y, paint);
            canvas.drawLine(leftShoulder.x * scale.x, leftShoulder.y * scale.y, rightShoulder.x * scale.x, rightShoulder.y * scale.y, paint);
            canvas.drawLine(leftShoulder.x * scale.x, leftShoulder.y * scale.y, leftHip.x * scale.x, leftHip.y * scale.y, paint);
            canvas.drawLine(rightShoulder.x * scale.x, rightShoulder.y * scale.y, rightHip.x * scale.x, rightHip.y * scale.y, paint);
            canvas.drawLine(leftHip.x * scale.x, leftHip.y * scale.y, rightHip.x * scale.x, rightHip.y * scale.y, paint);
            canvas.drawLine(leftHip.x * scale.x, leftHip.y * scale.y, leftKnee.x * scale.x, leftKnee.y * scale.y, paint);
            canvas.drawLine(rightHip.x * scale.x, rightHip.y * scale.y, rightKnee.x * scale.x, rightKnee.y * scale.y, paint);
            canvas.drawLine(leftKnee.x * scale.x, leftKnee.y * scale.y, leftAnkle.x * scale.x, leftAnkle.y * scale.y, paint);
            canvas.drawLine(rightKnee.x * scale.x, rightKnee.y * scale.y, rightAnkle.x * scale.x, rightAnkle.y * scale.y, paint);*/
            try {
                canvas.drawLine(ls.x, ls.y, le.x, le.y, paint);
                canvas.drawLine(rs.x, rs.y, re.x, re.y, paint);
                canvas.drawLine(le.x, le.y, lw.x, lw.y, paint);
                canvas.drawLine(re.x, re.y, rw.x, rw.y, paint);
                canvas.drawLine(ls.x, ls.y, rs.x, rs.y, paint);
                canvas.drawLine(ls.x, ls.y, lh.x, lh.y, paint);
                canvas.drawLine(rs.x, rs.y, rh.x, rh.y, paint);
                canvas.drawLine(lh.x, lh.y, rh.x, rh.y, paint);
                canvas.drawLine(lh.x, lh.y, lk.x, lk.y, paint);
                canvas.drawLine(rh.x, rh.y, rk.x, rk.y, paint);
                canvas.drawLine(lk.x, lk.y, la.x, la.y, paint);
                canvas.drawLine(rk.x, rk.y, ra.x, ra.y, paint);
            } catch (Exception e) {
                Log.d("smth", String.valueOf(e));
            }
            try {
                canvas.drawPoint(ls.x, ls.y, whitePaint);
                canvas.drawPoint(rs.x, rs.y, whitePaint);
                canvas.drawPoint(le.x, le.y, whitePaint);
                canvas.drawPoint(re.x, re.y, whitePaint);
                canvas.drawPoint(lw.x, lw.y, whitePaint);
                canvas.drawPoint(rw.x, rw.y, whitePaint);
                canvas.drawPoint(lh.x, lh.y, whitePaint);
                canvas.drawPoint(rh.x, rh.y, whitePaint);
                canvas.drawPoint(lk.x, lk.y, whitePaint);
                canvas.drawPoint(rk.x, rk.y, whitePaint);
                canvas.drawPoint(la.x, la.y, whitePaint);
                canvas.drawPoint(ra.x, ra.y, whitePaint);
            } catch (Exception e) {
                Log.d("smth", String.valueOf(e));
            }



        }
    }
}
