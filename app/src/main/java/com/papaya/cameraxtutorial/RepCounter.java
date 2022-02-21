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

    public String checkPose(List<PoseLandmark> landmarks) {
        if (landmarks != null) {
            switch (type) {
                case "squats":
                    PoseLandmark lh = null;
                    PoseLandmark rh = null;
                    PoseLandmark lk = null;
                    PoseLandmark rk = null;
                    try {
                        lh = landmarks.get(PoseLandmark.LEFT_HIP);
                        rh = landmarks.get(PoseLandmark.RIGHT_HIP);
                        lk = landmarks.get(PoseLandmark.LEFT_KNEE);
                        rk = landmarks.get(PoseLandmark.RIGHT_KNEE);
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
