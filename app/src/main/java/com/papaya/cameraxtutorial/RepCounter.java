package com.papaya.cameraxtutorial;

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

    public RepCounter(String type) {
        this.type = type;
    }

    public String checkPose(List<PoseLandmark> landmarks, Float ankleY) {
        if (landmarks != null) {
            switch (type) {
                case "squats":
                    PoseLandmark lh = null;
                    PoseLandmark rh = null;
                    PoseLandmark lk = null;
                    PoseLandmark rk = null;
                    PoseLandmark la = null;
                    PoseLandmark ra = null;
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
                    } catch (Exception e) {

                    }
                    if (lh != null && rh != null && lk != null && rk != null) {
                        if (lh.getPosition().y > lk.getPosition().y && rh.getPosition().y > rk.getPosition().y) {
                            return "down";
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
