package com.papaya.cameraxtutorial;

import static java.lang.Math.atan2;

import com.google.mlkit.vision.pose.Pose;
import com.google.mlkit.vision.pose.PoseLandmark;

import java.util.List;

public class RepCounter {

    int reps;
    String type;

    public int getReps() {
        return reps;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }

    public RepCounter(String type) { this.type = type; }

    public String checkPose(List<PoseLandmark> landmarks, Float height) {
        if (landmarks != null) {
            PoseLandmark lh = null;
            PoseLandmark rh = null;
            PoseLandmark lk = null;
            PoseLandmark rk = null;
            PoseLandmark la = null;
            PoseLandmark ra = null;
            PoseLandmark ls = null;
            PoseLandmark rs = null;
            PoseLandmark lw = null;
            PoseLandmark rw = null;
            PoseLandmark le = null;
            PoseLandmark re = null;
            try {
                if (landmarks.get(PoseLandmark.LEFT_HIP).getInFrameLikelihood() > 0.5) {
                    lh = landmarks.get(PoseLandmark.LEFT_HIP);
                }
                if (landmarks.get(PoseLandmark.RIGHT_HIP).getInFrameLikelihood() > 0.5) {
                    rh = landmarks.get(PoseLandmark.RIGHT_HIP);
                }
                if (landmarks.get(PoseLandmark.LEFT_KNEE).getInFrameLikelihood() > 0.5) {
                    lk = landmarks.get(PoseLandmark.LEFT_KNEE);
                }
                if (landmarks.get(PoseLandmark.RIGHT_KNEE).getInFrameLikelihood() > 0.5) {
                    rk = landmarks.get(PoseLandmark.RIGHT_KNEE);
                }
                if (landmarks.get(PoseLandmark.LEFT_ANKLE).getInFrameLikelihood() > 0.5) {
                    la = landmarks.get(PoseLandmark.LEFT_ANKLE);
                }
                if (landmarks.get(PoseLandmark.RIGHT_ANKLE).getInFrameLikelihood() > 0.5) {
                    ra = landmarks.get(PoseLandmark.RIGHT_ANKLE);
                }
                if (landmarks.get(PoseLandmark.LEFT_SHOULDER).getInFrameLikelihood() > 0.5) {
                    ls = landmarks.get(PoseLandmark.LEFT_SHOULDER);
                }
                if (landmarks.get(PoseLandmark.RIGHT_SHOULDER).getInFrameLikelihood() > 0.5) {
                    rs = landmarks.get(PoseLandmark.RIGHT_SHOULDER);
                }
                if (landmarks.get(PoseLandmark.LEFT_WRIST).getInFrameLikelihood() > 0.5) {
                    lw = landmarks.get(PoseLandmark.LEFT_WRIST);
                }
                if (landmarks.get(PoseLandmark.RIGHT_WRIST).getInFrameLikelihood() > 0.5) {
                    rw = landmarks.get(PoseLandmark.RIGHT_WRIST);
                }
                if (landmarks.get(PoseLandmark.LEFT_ELBOW).getInFrameLikelihood() > 0.5) {
                    le = landmarks.get(PoseLandmark.LEFT_ELBOW);
                }
                if (landmarks.get(PoseLandmark.RIGHT_ELBOW).getInFrameLikelihood() > 0.5) {
                    re = landmarks.get(PoseLandmark.RIGHT_ELBOW);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            switch (type) {
                case "squats":
                    if (lh != null && rh != null && lk != null && rk != null) {
                        if (lh.getPosition().y <= (lk.getPosition().y + 10)) {
                            if (lh.getPosition().y >= lk.getPosition().y - 10) {
                                return "down";
                            } else {
                                return "hips too low";
                            }
                        } else {
                            return "up";
                        }
                    }
                    break;
                default:
                    return "error";

            }

        }
        return "error";

    }

}
