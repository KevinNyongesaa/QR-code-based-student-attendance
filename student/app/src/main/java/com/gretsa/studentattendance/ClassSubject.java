package com.gretsa.studentattendance;

public class ClassSubject {

    private String classText;
    private String subjectText;

    public ClassSubject() {
        // Empty constructor required for Firestore
    }

    public ClassSubject(String classText, String subjectText) {
        this.classText = classText;
        this.subjectText = subjectText;
    }

    public String getClassText() {
        return classText;
    }

    public String getSubjectText() {
        return subjectText;
    }
}
